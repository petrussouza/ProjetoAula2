package school.cesar.projetoaula2.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import school.cesar.projetoaula2.R
import school.cesar.projetoaula2.model.Usuario

class ResumoLoginActivity : AppCompatActivity() {

    var email: String? = null
    var senha: String? = null

    private lateinit var txtEmail: TextView
    private lateinit var txtSenha: TextView

    companion object{
        var EXTRA_EMAIL = "email"
        var EXTRA_SENHA = "senha"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumo_login)
        configuraActionBar()
        carregarCamposTela()
        getExtraIntent()
        exibeDadosLogin()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun configuraActionBar(){
        val actionbar = supportActionBar
        actionbar?.let {
            actionbar.title = getString(R.string.titulo_resumo_cadastro)
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun getExtraIntent(){
        if(intent.hasExtra(EXTRA_EMAIL) && intent.hasExtra(EXTRA_SENHA)){
            email = intent.getStringExtra(EXTRA_EMAIL)
            senha = intent.getStringExtra(EXTRA_SENHA)
        }
    }

    private fun carregarCamposTela(){
        txtEmail = findViewById<TextView>(R.id.activity_resumo_login_txt_email)
        txtSenha = findViewById<TextView>(R.id.activity_resumo_login_txt_senha)
    }

    private fun exibeDadosLogin(){
        txtEmail.text = "Login: $email"
        txtSenha.text = "Senha: $senha"
    }
}
