package com.example.spelllist

import android.database.Cursor
import android.database.sqlite.SQLiteCursor
import android.database.sqlite.SQLiteCursorDriver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_all_spells.*


// Toast is great // Toast.makeText(this,test.getString(1),Toast.LENGTH_LONG).show()
class AllSpells : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_spells)


        val myDatabase = ActsDbHelper(this).readableDatabase
        val c = myDatabase.rawQuery("Select * from spells", null)
        val spells = ArrayList<Spell>()
        if(c != null){
            if(c.moveToFirst()){
                do{
                    val name = c.getString(1)
                    val page = c.getString(2)
                    val keywords = c.getString(3)
                    val type = c.getString(4)
                    val castTime = c.getString(5)
                    val range = c.getString(6)
                    val components = c.getString(7)
                    val duration = c.getString(8)
                    val description = c.getString(9)
                    spells.add(Spell(name, page, keywords, type, castTime,
                                range, components, duration, description))
                }while(c.moveToNext())
            }
        }
        else{
            System.out.println("Empty")
        }
        myDatabase.close()
        System.out.println(spells[1].type)
        //val cursorDrive: SQLiteCursorDriver
        viewManager = LinearLayoutManager(this)
        viewAdapter = SpellAdapter(spells)
        recyclerView = findViewById<RecyclerView>(R.id.spellList).apply{
            layoutManager = viewManager
            adapter = viewAdapter
        }

        //RecyclerView recycle = (RecyclerView) findViewByID(R.id.spellList)
        /*spellList.apply {
            layoutManager = LinearLayoutManager(this@AllSpells)
            adapter = SpellAdapter(spells)
        }*/

        /*val test = myDatabase.rawQuery("SELECT * FROM spells",null)
        test.moveToFirst()
        // 1 is column is based for column number.
        Toast.makeText(this,test.getString(1),Toast.LENGTH_LONG).show()*/
    }

}
