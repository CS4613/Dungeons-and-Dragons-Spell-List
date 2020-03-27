package com.example.spelllist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.description.*

class Description : AppCompatActivity() {
    //Creating the first instance of the app itself, start screen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.description)

        spellName.text = intent.getStringExtra("name")
        keyWords.text = intent.getStringExtra("keyword")
        levelType.text = intent.getStringExtra("type")
        components.text = intent.getStringExtra("components")
        castingTime.text = intent.getStringExtra("castTime")
        range.text = intent.getStringExtra("range")
        description.text = intent.getStringExtra("desc")

    }
}