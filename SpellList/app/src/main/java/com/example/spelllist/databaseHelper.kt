package com.example.spelllist

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.content.ContentValues
import android.content.res.AssetManager
import android.content.res.Resources
import java.io.BufferedReader
import java.lang.Exception

// DONT PASS DATA FROM ACTIVITY TO ACTIVITY - USE GLOBALS.

class DBSpells : SQLiteOpenHelper { // going to make a object of DBSpells that will be global.

    val context: Context
    // So maybe need to backspace it back.
    constructor(context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int)
            : super(context, DATABASE_NAME, factory, DATABASE_VERSION)
    {
        this.context = context
    }

    override fun onCreate(db: SQLiteDatabase) {

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object FeedReaderContract {
        // Table contents are grouped together in an anonymous object.
        object FeedEntry : BaseColumns {
            const val TABLE_NAME = "Spells"
            const val COLUMN_NAME_NAME = "Name"
            const val COLUMN_NAME_PAGE = "Page"
        }
    }

}