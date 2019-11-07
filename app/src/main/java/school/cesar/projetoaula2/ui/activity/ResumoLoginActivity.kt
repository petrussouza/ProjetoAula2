package school.cesar.projetoaula2.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import school.cesar.projetoaula2.R
import school.cesar.projetoaula2.model.Usuario

class ResumoLoginActivity : AppCompatActivity() {

    var usuario: Usuario? = null

    private lateinit var txtEmail: TextView
    private lateinit var txtSenha: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumo_login)
        configuraActionBar()
        carregarCamposTela()
        getExtraIntent()
        exibeDadosLogin()
        configuraBotaoListaTarefas()
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
        if(intent.hasExtra(Usuario.TAG)){
            usuario = intent.getSerializableExtra(Usuario.TAG) as Usuario
        }
    }

    private fun carregarCamposTela(){
        txtEmail = findViewById<TextView>(R.id.activity_resumo_login_txt_email)
        txtSenha = findViewById<TextView>(R.id.activity_resumo_login_txt_senha)
    }

    private fun exibeDadosLogin(){
        usuario?.let {
            txtEmail.text = "Login: ${it.email}"
            txtSenha.text = "Senha: ${it.senha}"
        }
    }

    private fun configuraBotaoListaTarefas(){
        val btnListaTarefas: Button = findViewById<Button>(R.id.activity_resumo_login_btn_lista_tarefas)
        btnListaTarefas.setOnClickListener {
            intent = Intent(this, TarefasActivity::class.java)
            intent.putExtra(Usuario.TAG, usuario)
            startActivity(intent)
        }
    }
}
