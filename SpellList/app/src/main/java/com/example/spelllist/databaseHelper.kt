package com.example.spelllist

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

    // Check version
    private val preferences: SharedPreferences = context.getSharedPreferences(
        "${context.packageName}.database_versions",
        Context.MODE_PRIVATE
    )

    // Is it out of date?
    private fun installedDatabaseIsOutdated(): Boolean {
        return preferences.getInt(DATABASE_NAME, 0) < DATABASE_VERSION
    }

    // writing the name and current version and applying it to current use
    private fun writeDatabaseVersionInPreferences() {
        preferences.edit().apply {
            putInt(DATABASE_NAME, DATABASE_VERSION)
            apply()
        }
    }

    // Taking the already made sql3 lite database to apply it to our needs.
    private fun installDatabaseFromAssets() { // Opening asset at given path to made file handle
        val inputStream = context.assets.open("$ASSETS_PATH/$DATABASE_NAME.sqlite3")

        try { // Try to create a written version of a database from given path location.
            val outputFile = File(context.getDatabasePath(DATABASE_NAME).path)
            val outputStream = FileOutputStream(outputFile)
            //Using the assets found via inputStream and writing it to outputStream
            inputStream.copyTo(outputStream)
            inputStream.close() // Close the file when done

            outputStream.flush() // Close any extra spacing
            outputStream.close() // Close the file when done
        }// If fail, say why
        catch (exception: Throwable) {
            throw RuntimeException("The $DATABASE_NAME database couldn't be installed.", exception)
        }
    }

    @Synchronized // If new version is found, delete the old and install the new one found.
    private fun installOrUpdateIfNecessary() {
        if (installedDatabaseIsOutdated()) {
            context.deleteDatabase(DATABASE_NAME)
            installDatabaseFromAssets()
            writeDatabaseVersionInPreferences()
        }
    }

    // Premade database, overwrite the base function and make it not changeable.
    override fun getWritableDatabase(): SQLiteDatabase {
        return super.getWritableDatabase()
    }

    // Override the base fun, Check if database needs updated, return a readable DB view
    override fun getReadableDatabase(): SQLiteDatabase {
        installOrUpdateIfNecessary()
        return super.getReadableDatabase()
    }

    //On call, make database from assets
    override fun onCreate(db: SQLiteDatabase) {
       db.execSQL(SQL_CREATE_ENTRIES)
    }

    /* Given how it works, however, our database should be a non-changing database or if
       it does change the premade database will be the change. Updating via in app to main database
       should not be a need.*/
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
         //To change body of created functions use File | Settings | File Templates
    }

    // Creating the database itself, or in this case the column name headers. along with connections
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
            const val COLUMN_NAME_FAVORITE = "Favorite"
        }
    }
    // The data being feed into the table.
    private var SQL_CREATE_ENTRIES =
        "CREATE TABLE IF NOT EXISTS ${FeedEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${FeedEntry.COLUMN_NAME_NAME} TEXT," +
                "${FeedEntry.COLUMN_NAME_PAGE} TEXT," +
                "${FeedEntry.COLUMN_NAME_KEYWORD} TEXT," +
                "${FeedEntry.COLUMN_NAME_TYPE} TEXT," +
                "${FeedEntry.COLUMN_NAME_CASTTIME} TEXT," +
                "${FeedEntry.COLUMN_NAME_RANGE} TEXT," +
                "${FeedEntry.COLUMN_NAME_COMPONENTS} TEXT," +
                "${FeedEntry.COLUMN_NAME_DURATION} TEXT," +
                "${FeedEntry.COLUMN_NAME_DESCRIPTION} TEXT," +
                "${FeedEntry.COLUMN_NAME_FAVORITE} INTEGER)"

    // If table exists already, drop it so as to not repeat data.
   // private var SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FeedEntry.TABLE_NAME}"

}