package school.cesar.projetoaula2.dao

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import school.cesar.projetoaula2.model.Usuario
import java.lang.reflect.Type

class TarefaDAO (val context: Context){

    companion object{
        private var controleIndice = 0;

        fun getInstance(context: Context) : TarefaDAO {
            return TarefaDAO(context)
        }
    }

    private fun getSharedPreferences(): SharedPreferences{
        return context.getSharedPreferences(UsuarioDAO.PREFERENCE_USERS, Context.MODE_PRIVATE)
    }

    fun salvar(indice: Int, tarefas: MutableList<String>){
        var editor = getSharedPreferences().edit()
        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonTarefas: String = gson.toJson(tarefas)
        editor.putString("tarefas_$indice", jsonTarefas)
        editor.commit()
    }

    fun getTarefas(indice: Int): MutableList<String>{
        var tarefas = mutableListOf<String>();
        val sharedPreferences = getSharedPreferences()
        val gson = Gson()
        var jsonTarefas = sharedPreferences.getString("tarefas_$indice", null)
        if(jsonTarefas != null){
            val listType = object : TypeToken<MutableList<String>>() { }.type
            tarefas = gson.fromJson(jsonTarefas, listType)
        }
        return tarefas
    }
}