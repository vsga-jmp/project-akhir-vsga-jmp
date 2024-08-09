import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import dts.pnj.dimasfebriyanto.R
import java.io.File
import java.io.FileOutputStream

class NewsDatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {
    private val appContext = context.applicationContext

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLE_NEWS (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_TITLE TEXT, " +
                    "$COLUMN_CONTENT TEXT, " +
                    "$COLUMN_PATH_IMAGE TEXT)"
        )

        // Insert dummy data
        insertDummyData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NEWS")
        onCreate(db)
    }

    fun saveDrawableToInternalStorage(context: Context, drawableId: Int, filename: String): String {
        val drawable: Drawable = context.getDrawable(drawableId) ?: throw IllegalArgumentException("Drawable resource not found")
        val bitmap: Bitmap = (drawable as BitmapDrawable).bitmap

        val file = File(context.filesDir, filename)
        FileOutputStream(file).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        }
        return file.absolutePath
    }

    private fun insertDummyData(db: SQLiteDatabase) {
        val titles = listOf(
            "Tech Update: Latest Innovations",
            "Sports Highlights: This Week’s Best Moments",
            "World News: Major Events Around the Globe"
        )

        val contents = listOf(
            "Discover the latest technological innovations and breakthroughs happening in the tech world. From cutting-edge gadgets to revolutionary software, get all the updates on what’s new in technology.",
            "Catch up on the most exciting sports moments from this week. From thrilling match highlights to standout performances, find out what made the headlines in the world of sports.",
            "Stay informed about major global events that are shaping our world. From political developments to significant cultural shifts, get the latest news on what’s happening around the globe."
        )

        val imageResourceIds = listOf(
            "drawable/tech_update",
            "drawable/sports_highlights",
            "drawable/world_news"
        )

        for (i in titles.indices) {
            val values = ContentValues().apply {
                put(COLUMN_TITLE, titles[i])
                put(COLUMN_CONTENT, contents[i])
                put(COLUMN_PATH_IMAGE, imageResourceIds[i])
            }

            // Log values for debugging
            Log.d("NewsDatabaseHelper", "Inserting data: $values")

            val rowId = db.insert(TABLE_NEWS, null, values)
            if (rowId == -1L) {
                Log.e("NewsDatabaseHelper", "Failed to insert row for title: ${titles[i]}")
            } else {
                Log.d("NewsDatabaseHelper", "Inserted row with ID: $rowId")
            }
        }
    }


    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "news.db"
        const val TABLE_NEWS = "news"
        const val COLUMN_ID = "_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_PATH_IMAGE = "path_image"
    }
}
