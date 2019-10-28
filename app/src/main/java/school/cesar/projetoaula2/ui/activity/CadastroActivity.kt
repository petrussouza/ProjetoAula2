package school.cesar.projetoaula2.ui.activity

import android.app.ActionBar
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import school.cesar.projetoaula2.R
import school.cesar.projetoaula2.extension.emBranco
import school.cesar.projetoaula2.extension.isEmailValido
import school.cesar.projetoaula2.extension.minCaracteres
import school.cesar.projetoaula2.model.Usuario
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
        edtCpf.addTextChangedListener(Mask.mask("###.###.###-##", edtCpf))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_activity_cadastro, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_cadastro_menu_salvar -> {
            if(validarForm()) {
                Toast.makeText(this, getString(R.string.msg_salvando), Toast.LENGTH_SHORT).show()
                val usuario = preencheUsuario()
                criarConta(usuario)
            }
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun validarForm(): Boolean{
        if(!edtNome.emBranco() and edtEmail.isEmailValido() and edtSenha.minCaracteres(6) and edtCpf.minCaracteres(11)){
            return true
        }
        return false
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
