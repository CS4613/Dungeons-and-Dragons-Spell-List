package com.example.spelllist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_all_spells.*
// Toast is great // Toast.makeText(this,test.getString(1),Toast.LENGTH_LONG).show()
class AllSpells : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_spells)

        spellList.apply {
            layoutManager = LinearLayoutManager(this@AllSpells)
            adapter = SpellAdapter()
        }

        val myDatabase = ActsDbHelper(this).readableDatabase

        val test = myDatabase.rawQuery("SELECT * FROM spells",null)
        test.moveToFirst()
        // 1 is column is based for column number.
        Toast.makeText(this,test.getString(1),Toast.LENGTH_LONG).show()
    }

}
