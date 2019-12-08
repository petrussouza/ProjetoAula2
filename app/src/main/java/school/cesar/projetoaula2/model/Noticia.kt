package school.cesar.projetoaula2.model

import com.google.gson.annotations.SerializedName

data class Noticia(@SerializedName("title") val titulo:String,
                   @SerializedName("description") val descricao: String){

}