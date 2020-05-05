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

        //All spells clicked, swap to all spells activity
        bAllSpells.setOnClickListener {
            println("\n\ntest\n\n")
            startActivity(Intent(this, AllSpells::class.java))

        }
        bFavorites.setOnClickListener {
            println("\n\ntest\n\n")
            startActivity(Intent(this, Favorites::class.java))

        }
    }
}
