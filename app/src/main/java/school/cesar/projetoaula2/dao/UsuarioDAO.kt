package school.cesar.projetoaula2.dao

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import school.cesar.projetoaula2.model.Usuario

class UsuarioDAO (val context: Context){

    companion object{
        private var controleIndice = 0;
        val PREFERENCE_USERS = "PREFERENCE_USERS"

        fun getInstance(context: Context) : UsuarioDAO {
            return UsuarioDAO(context)
        }
    }

    private fun getSharedPreferences(): SharedPreferences{
        return context.getSharedPreferences(PREFERENCE_USERS, Context.MODE_PRIVATE)
    }

    private fun getIndice(){
        controleIndice = getSharedPreferences().getInt("controleIndice", 0)
    }

    fun salvar(usuario: Usuario){
        getIndice()
        var editor = getSharedPreferences().edit()
        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonUsuario: String = gson.toJson(usuario)
        editor.putString("usuario_$controleIndice", jsonUsuario)
        usuario.indice = controleIndice
        controleIndice++
        editor.putInt("controleIndice", controleIndice)
        editor.commit()
    }

    private fun getUsuarios(): List<Usuario>{
        getIndice()
        var usuarios = mutableListOf<Usuario>();
        val gson = Gson()
        val sharedPreferences = getSharedPreferences()
        for (i in 0..controleIndice) {
            var jsonUsuario = sharedPreferences.getString("usuario_$i", null)
            if(jsonUsuario != null){
                var usuario : Usuario = gson.fromJson(jsonUsuario, Usuario::class.java)
                usuario.indice = i
                usuarios.add(usuario)
            }
        }
        return usuarios;
    }

    fun buscarUsuario(usuario: Usuario): Usuario?{
        for (usuarioAtual in getUsuarios()) {
            if(usuario.cpf == usuarioAtual.cpf || usuario.email == usuarioAtual.email){
                return  usuarioAtual;
            }
        }
        return null;
    }

    fun getUsuarioEmailSenha(email :String, senha: String): Usuario?{
        for (usuarioAtual in getUsuarios()) {
            if(email == usuarioAtual.email && senha == usuarioAtual.senha){
                return  usuarioAtual;
            }
        }
        return null;
    }
}