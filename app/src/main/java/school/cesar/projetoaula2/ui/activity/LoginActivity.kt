package school.cesar.projetoaula2.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import school.cesar.projetoaula2.R
import school.cesar.projetoaula2.dao.UsuarioDAO
import school.cesar.projetoaula2.extension.isEmailValido
import school.cesar.projetoaula2.model.Usuario
import school.cesar.projetoaula2.util.PoliticaSenhaUtil
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtSenha: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        configuraActionBar()
        carregarCamposFormulario()
        configuraBotaoCancelar()
        configuraBotaoEntrar()
        configurarValidacaoPoliticaSenha()
    }

    private fun configuraActionBar(){
        val actionbar = supportActionBar
        actionbar?.let {
            actionbar.title = getString(R.string.titulo_login)
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun carregarCamposFormulario(){
        edtEmail = findViewById<EditText>(R.id.activity_login_edt_email)
        edtSenha = findViewById<EditText>(R.id.activity_login_edt_senha)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun validarForm(): Boolean{
        var valido = true
        if(!edtEmail.text.toString().trim().isEmailValido()){
            edtEmail.error = getString(R.string.msg_email_invalido)
            valido = false
        }
        if(edtSenha.text.toString().trim().length < Usuario.TAMANHO_MINIMO_SENHA){
            edtSenha.error = getString(R.string.msg_minimo_de_caracteres, Usuario.TAMANHO_MINIMO_SENHA)
            valido = false
        }
        if(edtSenha.error != null){
            valido = false
        }
        return valido
    }

    private fun configuraBotaoCancelar(){
        val btnCancelar = findViewById<Button>(R.id.activity_login_btn_cancelar)
        btnCancelar.setOnClickListener{
            intent = Intent(this, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    private fun configuraBotaoEntrar(){
        val btnEntrar = findViewById<Button>(R.id.activity_login_btn_entrar)
        btnEntrar.setOnClickListener{
            if(validarForm()) {
                Toast.makeText(this, getString(R.string.msg_efetuando_login), Toast.LENGTH_SHORT).show()
                efetuarLogin(edtEmail.text.toString().trim(), edtSenha.text.toString().trim())
            }
        }
    }

    private fun efetuarLogin(email: String, senha: String){
        val usuarioDAO = UsuarioDAO(this)
        val usuario = usuarioDAO.getUsuarioEmail(email);
        if(usuario != null){
            if(usuario.senha.equals(senha)) {
                intent = Intent(this, TarefasActivity::class.java)
                intent.putExtra(Usuario.TAG, usuario)
                startActivity(intent)
            }else{
                edtSenha.error = getString(R.string.msg_senha_invalida)
            }
        }else{
            edtEmail.error = getString(R.string.msg_usuario_nao_cadastrado)
        }
    }

    private fun configurarValidacaoPoliticaSenha(){
        edtSenha.addTextChangedListener(PoliticaSenhaUtil(edtSenha, getString(R.string.msg_senha_primeira_letra_maiscula)))
    }
}
