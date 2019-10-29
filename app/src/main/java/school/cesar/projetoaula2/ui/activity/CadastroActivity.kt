package school.cesar.projetoaula2.ui.activity

import android.app.ActionBar
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import school.cesar.projetoaula2.R
import school.cesar.projetoaula2.extension.isEmailValido
import school.cesar.projetoaula2.model.Usuario
import school.cesar.projetoaula2.util.CPFUtil
import school.cesar.projetoaula2.util.Mask

class CadastroActivity : AppCompatActivity() {

    private lateinit var edtNome: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtSenha: EditText
    private lateinit var edtCpf: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        configuraActionBar()
        carregarCamposFormulario()
        defineMascaraCpf()
        configuraBotaoCadastrar()
    }

    private fun configuraActionBar(){
        val actionbar = supportActionBar
        actionbar?.let {
            actionbar.title = getString(R.string.titulo_nova_conta)
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun carregarCamposFormulario(){
        edtNome = findViewById<EditText>(R.id.activity_cadastro_edt_nome)
        edtEmail = findViewById<EditText>(R.id.activity_cadastro_edt_email)
        edtSenha = findViewById<EditText>(R.id.activity_cadastro_edt_senha)
        edtCpf = findViewById<EditText>(R.id.activity_cadastro_edt_cpf)
    }


    fun defineMascaraCpf(){
        edtCpf.addTextChangedListener(Mask.mask("###.###.###-##", edtCpf))
    }

    private fun configuraBotaoCadastrar(){
        val btnCadastrar = findViewById<Button>(R.id.activity_cadastro_btn_cadastrar)
        btnCadastrar.setOnClickListener{
            if(validarForm()) {
                Toast.makeText(this, getString(R.string.msg_salvando), Toast.LENGTH_SHORT).show()
                val usuario = preencheUsuario()
                criarConta(usuario)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun validarForm(): Boolean{
        var valido = true

        if(edtNome.text.toString().isEmpty()){
            edtNome.error = getString(R.string.msg_campo_obrigatorio)
            valido = false;
        }
        if(!edtEmail.text.toString().isEmailValido()){
            edtEmail.error = getString(R.string.msg_email_invalido)
            valido = false
        }
        if(edtSenha.text.toString().length < Usuario.TAMANHO_MINIMO_SENHA){
            edtSenha.error = getString(R.string.msg_minimo_de_6_caracteres)
            valido = false
        }
        if(!CPFUtil.validarCPF(edtCpf.text.toString())){
            edtCpf.error = getString(R.string.msg_cpf_invalido)
            valido = false
        }
        return valido
    }

    private fun preencheUsuario(): Usuario {
        return Usuario(edtNome.text.toString(), edtEmail.text.toString(),
                    edtSenha.text.toString(), edtCpf.text.toString())
    }

    private fun criarConta(usuario: Usuario){
        intent = Intent(this, ResumoCadastroActivity::class.java)
        intent.putExtra(ResumoCadastroActivity.EXTRA_USUARIO, usuario)
        startActivity(intent)
    }
}
