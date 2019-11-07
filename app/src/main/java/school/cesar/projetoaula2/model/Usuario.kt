package school.cesar.projetoaula2.model

import com.google.gson.annotations.Expose
import java.io.Serializable

data class Usuario (var nome: String,
                    var email: String,
                    var senha: String,
                    val cpf: String): Serializable{

    companion object{
        val TAG = "usuario"
        val TAMANHO_MINIMO_SENHA = 6
    }

    var indice: Int = 0;

}