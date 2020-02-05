package com.example.spelllist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_all_spells.*

class AllSpells : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_spells)

        spellList.apply {
            layoutManager = LinearLayoutManager(this@AllSpells)
            adapter = SpellAdapter()
        }
    }

}
