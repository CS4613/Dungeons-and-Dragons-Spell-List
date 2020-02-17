package com.example.spelllist
// ALL FLUFFY CRAP
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.provider.BaseColumns

import java.io.File
import java.io.FileOutputStream


import android.content.ContentValues
import android.content.SharedPreferences
import android.content.res.AssetManager
import android.content.res.Resources
import java.io.BufferedReader
import java.lang.Exception


// DONT PASS DATA FROM ACTIVITY TO ACTIVITY - USE GLOBALS.
// going to make a object of DBSpells that will be global.
class ActsDbHelper(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    //val context: Context
    // So maybe need to backspace it back.
   /*     constructor(context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int): super(context, DATABASE_NAME, factory, DATABASE_VERSION)
        {
            this.context = context
        }

    */

    private val preferences: SharedPreferences = context.getSharedPreferences(
        "${context.packageName}.database_versions",
        Context.MODE_PRIVATE
    )
    private fun installedDatabaseIsOutdated(): Boolean {
        return preferences.getInt(DATABASE_NAME, 0) < DATABASE_VERSION
    }

    private fun writeDatabaseVersionInPreferences() {
        preferences.edit().apply {
            putInt(DATABASE_NAME, DATABASE_VERSION)
            apply()
        }
    }

    private fun installDatabaseFromAssets() {
        val inputStream = context.assets.open("$ASSETS_PATH/$DATABASE_NAME.sqlite3")

        try {
            val outputFile = File(context.getDatabasePath(DATABASE_NAME).path)
            val outputStream = FileOutputStream(outputFile)

            inputStream.copyTo(outputStream)
            inputStream.close()

            outputStream.flush()
            outputStream.close()
        }
        catch (exception: Throwable) {
            throw RuntimeException("The $DATABASE_NAME database couldn't be installed.", exception)
        }
    }

    @Synchronized
    private fun installOrUpdateIfNecessary() {
        if (installedDatabaseIsOutdated()) {
            context.deleteDatabase(DATABASE_NAME)
            installDatabaseFromAssets()
            writeDatabaseVersionInPreferences()
        }
    }

    override fun getWritableDatabase(): SQLiteDatabase {
        throw RuntimeException("The $DATABASE_NAME database is not writable.")
    }

    override fun getReadableDatabase(): SQLiteDatabase {
        installOrUpdateIfNecessary()
        return super.getReadableDatabase()
    }


    override fun onCreate(db: SQLiteDatabase?) {
       // db.execSQL(SQL_CREATE_ENTRIES) // Nothing to do
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
         //To change body of created functions use File | Settings | File Templates.
        // Nothing to do
    }

    companion object FeedReaderContract {
        // Table contents are grouped together in an anonymous object.
        const val DATABASE_NAME = "spellData"
        const val DATABASE_VERSION = 1
        const val ASSETS_PATH = "databases"
        object FeedEntry : BaseColumns {
            const val TABLE_NAME = "spells"
            const val COLUMN_NAME_NAME = "Name"
            const val COLUMN_NAME_PAGE = "Page"
            const val COLUMN_NAME_KEYWORD = "Keywords"
            const val COLUMN_NAME_TYPE = "Type"
            const val COLUMN_NAME_CASTTIME = "Cast time"
            const val COLUMN_NAME_RANGE = "Range"
            const val COLUMN_NAME_COMPONENTS = "Components"
            const val COLUMN_NAME_DURATION = "Duration"
            const val COLUMN_NAME_DESCRIPTION = "Description"
        }
    }

    private var SQL_CREATE_ENTRIES =
        "CREATE TABLE ${FeedEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${FeedEntry.COLUMN_NAME_NAME} TEXT," +
                "${FeedEntry.COLUMN_NAME_PAGE} TEXT," +
                "${FeedEntry.COLUMN_NAME_KEYWORD} TEXT)" +
                "${FeedEntry.COLUMN_NAME_TYPE} TEXT)" +
                "${FeedEntry.COLUMN_NAME_CASTTIME} TEXT)" +
                "${FeedEntry.COLUMN_NAME_RANGE} TEXT)" +
                "${FeedEntry.COLUMN_NAME_COMPONENTS} TEXT)" +
                "${FeedEntry.COLUMN_NAME_DURATION} TEXT)" +
                "${FeedEntry.COLUMN_NAME_DESCRIPTION} TEXT)"

    private var SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FeedEntry.TABLE_NAME}"



}