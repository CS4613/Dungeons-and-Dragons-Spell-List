package com.example.spelllist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //Creating the first instance of the app itself, start screen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

<<<<<<< HEAD
        //All spells clicked, swap to all spells activity
        bAllSpells.setOnClickListener {
            startActivity(Intent(this, AllSpells::class.java))

=======


        bAllSpells.setOnClickListener {
            startActivity(Intent(this, AllSpells::class.java))

            // NEW STUFF ADDED BY FLUFFY HERE.

>>>>>>> 421931593032b67a5b80d2d8463f12cf5d7de987
        }
    }
}
