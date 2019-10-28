package school.cesar.projetoaula2.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import school.cesar.projetoaula2.R
import school.cesar.projetoaula2.extension.emBranco
import school.cesar.projetoaula2.extension.isEmailValido
import school.cesar.projetoaula2.extension.minCaracteres
import school.cesar.projetoaula2.model.Usuario

class LoginActivity : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtSenha: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        configuraActionBar()
        carregarCamposFormulario()
        configuraBotaoEntrar()
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
        if(edtEmail.isEmailValido() and edtSenha.minCaracteres(6)){
            return true
        }
        return false
    }

    private fun configuraBotaoEntrar(){
        val btnEntrar = findViewById<Button>(R.id.activity_login_btn_entrar)
        btnEntrar.setOnClickListener{
            if(validarForm()) {
                Toast.makeText(this, getString(R.string.msg_efetuando_login), Toast.LENGTH_SHORT).show()
                efetuarLogin(edtEmail.text.toString(), edtSenha.text.toString())
            }
        }
    }

    private fun efetuarLogin(email: String, senha: String){
        intent = Intent(this, ResumoLoginActivity::class.java)
        intent.putExtra(ResumoLoginActivity.EXTRA_EMAIL, email)
        intent.putExtra(ResumoLoginActivity.EXTRA_SENHA, senha)
        startActivity(intent)
    }
}
