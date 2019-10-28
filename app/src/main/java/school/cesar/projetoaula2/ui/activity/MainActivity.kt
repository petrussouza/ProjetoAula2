package school.cesar.projetoaula2.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import school.cesar.projetoaula2.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configuraBotaoCadastreSe()
        configuraBotaoEntrar()
    }

    fun configuraBotaoCadastreSe(){
        val btnCadastreSe = findViewById<Button>(R.id.activity_main_btn_cadastre_se)
        btnCadastreSe.setOnClickListener{
            startActivity(Intent(this, CadastroActivity::class.java))
        }
    }

    fun configuraBotaoEntrar(){
        val btnLogin = findViewById<Button>(R.id.activity_main_btn_entrar)
        btnLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

}
