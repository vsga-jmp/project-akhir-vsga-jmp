package dts.pnj.dimasfebriyanto.database

import NewsDatabaseHelper
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class NewsDAO(context: Context) {
    private val dbHelper = NewsDatabaseHelper(context)

    fun getAllNews(): Cursor {
        val db = dbHelper.readableDatabase
        return db.query(
            NewsDatabaseHelper.TABLE_NEWS,
            null,
            null,
            null,
            null,
            null,
            null
        )
    }
}
