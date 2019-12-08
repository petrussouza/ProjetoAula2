package school.cesar.projetoaula2.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val TAG = "MyDatabase"
        private val DATABASE_NAME = "aula2.db"
        private val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("MyDatabase", "Criando Banco de Dados")
        db?.execSQL(UsuarioDAO.SQL_SCRIPT_CREATE)
        db?.execSQL(TarefaDAO.SQL_SCRIPT_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(TarefaDAO.SQL_SCRIPT_DROP);
        db?.execSQL(UsuarioDAO.SQL_SCRIPT_DROP);
        onCreate(db);
    }
}
