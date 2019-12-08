package school.cesar.projetoaula2.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import school.cesar.projetoaula2.model.Usuario

class UsuarioDAO(context: Context): DataSource(context) {

    companion object {
        const val TABLE_NAME = "usuario"
        //Columns
        const val ID: String = "id"
        const val NOME: String = "nome"
        const val EMAIL: String = "email"
        const val SENHA: String = "senha"
        const val CPF: String = "cpf"
        const val TIMESTAMP: String = "timestamp"

        val SQL_SCRIPT_CREATE =
            "CREATE TABLE if not exists " + TABLE_NAME + " (" +
                    "${ID} integer PRIMARY KEY autoincrement," +
                    "${NOME} varchar(40)," +
                    "${EMAIL} varchar(30)," +
                    "${SENHA} varchar(20)," +
                    "${CPF} varchar(14)," +
                    "${TIMESTAMP} integer" +
                    ");"

        val SQL_SCRIPT_DROP = "DROP TABLE IF EXISTS $TABLE_NAME;"
    }

    fun insertUsuario(usuario: Usuario) {
        val values = ContentValues()
        values.put(NOME, usuario.nome)
        values.put(EMAIL, usuario.email)
        values.put(SENHA, usuario.senha)
        values.put(CPF, usuario.cpf)
        values.put(TIMESTAMP, System.currentTimeMillis())
        val idInserido = dbWrite.insert(TABLE_NAME, null, values);
        usuario.id = idInserido.toInt()
    }

    fun buscarUsuario(usuario: Usuario) : Usuario? {
        val cursor: Cursor = dbRead
            .query(TABLE_NAME, arrayOf(ID, NOME, EMAIL, SENHA, CPF, TIMESTAMP), "${EMAIL}='${usuario.email}' OR ${CPF}='${usuario.cpf}'", null, null, null, null)
        return montarUsuario(cursor)
    }

    fun getUsuarioEmail(email: String) : Usuario? {
        val cursor: Cursor = dbRead
            .query(TABLE_NAME, arrayOf(ID, NOME, EMAIL, SENHA, CPF, TIMESTAMP), "${EMAIL}='${email}'", null, null, null, null)
        return montarUsuario(cursor)
    }

    fun updateUsuario(usuario: Usuario) {
        val values = ContentValues()
        values.put(NOME, usuario.nome)
        values.put(EMAIL, usuario.email)
        values.put(SENHA, usuario.senha)
        values.put(CPF, usuario.cpf)
        values.put(TIMESTAMP, System.currentTimeMillis())

        dbWrite.update(TABLE_NAME, values, "${ID}=${usuario.id}", null)
    }

    fun removeUsuario(id: Int): Int {
        return dbWrite.delete(TABLE_NAME, "${ID}=${id}", null)
    }

    private fun montarUsuario(cursor: Cursor): Usuario?{
        var usuario: Usuario? = null
        if(cursor.count > 0) {
            cursor.moveToNext()
            usuario = Usuario(
                cursor.getInt(cursor.getColumnIndex(ID)),
                cursor.getString(cursor.getColumnIndex(NOME)),
                cursor.getString(cursor.getColumnIndex(EMAIL)),
                cursor.getString(cursor.getColumnIndex(SENHA)),
                cursor.getString(cursor.getColumnIndex(CPF))
            );
        }
        cursor.close()
        return usuario
    }

}