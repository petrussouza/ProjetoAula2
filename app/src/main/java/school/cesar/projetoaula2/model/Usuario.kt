package school.cesar.projetoaula2.model

import java.io.Serializable

data class Usuario (var nome: String,
                    var email: String,
                    var senha: String,
                    val cpf: String): Serializable{
}