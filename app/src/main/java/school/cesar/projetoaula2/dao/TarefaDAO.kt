package school.cesar.projetoaula2.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import school.cesar.projetoaula2.model.Tarefa
import school.cesar.projetoaula2.model.Usuario

class TarefaDAO(context: Context): DataSource(context) {

    companion object {
        const val TABLE_NAME = "tarefa"
        //Columns
        const val ID: String = "id"
        const val DESCRICAO: String = "descricao"
        const val DIA_SEMANA: String = "dia_semana"
        const val USUARIO_ID: String = "usuario_id"
        const val TIMESTAMP: String = "timestamp"

        val SQL_SCRIPT_CREATE =
            "CREATE TABLE if not exists " + TABLE_NAME + " (" +
                    "${ID} integer PRIMARY KEY autoincrement," +
                    "${DESCRICAO} text," +
                    "${DIA_SEMANA} varchar(10)," +
                    "${USUARIO_ID} integer," +
                    "${TIMESTAMP} integer," +
                    "FOREIGN KEY(${USUARIO_ID}) REFERENCES ${UsuarioDAO.TABLE_NAME}(${UsuarioDAO.ID})" +
                    ");"

        val SQL_SCRIPT_DROP = "DROP TABLE IF EXISTS $TABLE_NAME;"
    }

    fun insertTarefa(tarefa: Tarefa, usuarioId: Int) {
        val values = ContentValues()
        values.put(DESCRICAO, tarefa.descricao)
        values.put(DIA_SEMANA, tarefa.diaSemana)
        values.put(USUARIO_ID, usuarioId)
        values.put(TIMESTAMP, System.currentTimeMillis())
        val idInserido = dbWrite.insert(TABLE_NAME, null, values);
        tarefa.id = idInserido.toInt()
    }

    fun getTarefas(usuarioId: Int): MutableList<Tarefa> {
        val cursor: Cursor = dbRead
            .query(TABLE_NAME, arrayOf(ID, DESCRICAO, DIA_SEMANA,  TIMESTAMP), "${USUARIO_ID}=${usuarioId}", null, null, null, null);
        return montarTarefas(cursor)
    }

    fun getTarefa(id: Int, usuarioId: Int) : Tarefa? {
        var tarefa: Tarefa? = null
        val cursor: Cursor = dbRead
            .query(TABLE_NAME, arrayOf(ID, DESCRICAO, DIA_SEMANA,  TIMESTAMP), "${ID}=${id} AND ${USUARIO_ID}=${usuarioId}", null, null, null, null)
        if(cursor.count > 0){
            cursor.moveToNext()
            tarefa = montarTarefa(cursor)
        }
        cursor.close()
        return tarefa
    }

    fun updateTarefa(tarefa: Tarefa, usuarioId: Int) {
        val values = ContentValues()
        values.put(DESCRICAO, tarefa.descricao)
        values.put(DIA_SEMANA, tarefa.diaSemana)
        values.put(USUARIO_ID, usuarioId)
        values.put(TIMESTAMP, System.currentTimeMillis())

        dbWrite.update(TABLE_NAME, values, "${ID}=${tarefa.id} AND ${USUARIO_ID}=${usuarioId}", null)
    }

    fun removeTarefa(id: Int, usuarioId: Int): Int {
        return dbWrite.delete(TABLE_NAME, "${ID}=${id} AND ${USUARIO_ID}=${usuarioId}", null)
    }

    private fun montarTarefas(cursor: Cursor): MutableList<Tarefa>{
        var tarefas = mutableListOf<Tarefa>()
        if(cursor.count > 0) {
            while(cursor.moveToNext()) {
                tarefas.add(montarTarefa(cursor))
            }
        }
        cursor.close()
        return tarefas
    }

    private fun montarTarefa(cursor: Cursor): Tarefa{
        var tarefa: Tarefa = Tarefa(
            cursor.getInt(cursor.getColumnIndex(ID)),
            cursor.getString(cursor.getColumnIndex(DESCRICAO)),
            cursor.getString(cursor.getColumnIndex(DIA_SEMANA))
        );
        return tarefa
    }

}