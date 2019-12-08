package school.cesar.projetoaula2.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase

abstract class DataSource(){

    protected lateinit var dbWrite: SQLiteDatabase
    protected lateinit var dbRead: SQLiteDatabase

    companion object{
        private lateinit var dbHelper: SQLiteHelper
    }

    constructor(context: Context) : this() {
        dbHelper = SQLiteHelper(context)
        dbRead = dbHelper.readableDatabase
        dbWrite = dbHelper.writableDatabase

    }

}