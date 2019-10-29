package school.cesar.projetoaula2.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import school.cesar.projetoaula2.R
import school.cesar.projetoaula2.model.Usuario

class ResumoCadastroActivity : AppCompatActivity() {

    var usuario: Usuario? = null

    private lateinit var txtNome: TextView
    private lateinit var txtEmail: TextView
    private lateinit var txtCpf: TextView

    companion object{
        var EXTRA_USUARIO = "usuario"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumo_cadastro)
        configuraActionBar()
        carregarCamposTela()
        getExtraIntent()
        exibeDadosUsuario()
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
        if(intent.hasExtra(EXTRA_USUARIO)){
            usuario = intent.getSerializableExtra(EXTRA_USUARIO) as Usuario
        }
    }

    private fun carregarCamposTela(){
        txtNome = findViewById<TextView>(R.id.activity_resumo_cadastro_txt_nome)
        txtEmail = findViewById<TextView>(R.id.activity_resumo_cadastro_txt_email)
        txtCpf = findViewById<TextView>(R.id.activity_resumo_cadastro_txt_cpf)
    }

    private fun exibeDadosUsuario(){
        usuario?.let{
            txtNome.text = "Nome: ${it.nome}"
            txtEmail.text = "Email: ${it.email}"
            txtCpf.text = "CPF: ${it.cpf}"
        }
    }
}
