package com.example.spelllist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bAllSpells.setOnClickListener {
            startActivity(Intent(this, AllSpells::class.java))

            // NEW STUFF ADDED BY FLUFFY HERE.

            //val myDatabase = ActsDbHelper(this).readableDatabase

            //myDatabase.rawQuery("SELECT * FROM spells",null)

        }
    }
}
