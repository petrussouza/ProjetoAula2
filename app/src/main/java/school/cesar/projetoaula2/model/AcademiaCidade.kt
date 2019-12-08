package school.cesar.projetoaula2.model

import com.google.gson.annotations.SerializedName

data class AcademiaCidade(@SerializedName("polo") val nome:String,
                          @SerializedName("latitude") val latitude: Double,
                          @SerializedName("longitude") val longitude: Double){

}