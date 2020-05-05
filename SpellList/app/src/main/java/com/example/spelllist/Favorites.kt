package com.example.spelllist

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Favorites: AppCompatActivity()  {

    // Setting up variables for the RecyclerView use
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    //Basic sql query to select everything from main database
    private var favquery = "SELECT * FROM spells WHERE favorite = 1"

    var spells = ArrayList<Spell>()

    private fun setLayout(){
        viewManager = LinearLayoutManager(this)
        viewAdapter = SpellAdapter(spells)
        recyclerView = findViewById<RecyclerView>(R.id.spellList).apply{
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    //Fill spells from the database from the sql query "query"
    private fun fillSpells(query : String){
        val myDatabase = ActsDbHelper(this).readableDatabase
        // Using an sql query to grab all infomation from table
        val c = myDatabase.rawQuery(query, null)

        // Filling spells from database
        if(c != null){
            if(c.moveToFirst()){
                do{
                    var level = c.getString(4).first().toString()
                    if (!level.first().isDigit()){
                        level = "0"
                    }
                    val id = c.getInt(0)
                    val name = c.getString(1)
                    val page = c.getString(2)
                    val keywords = c.getString(3)
                    val type = c.getString(4)
                    val castTime = c.getString(5)
                    val range = c.getString(6)
                    val components = c.getString(7)
                    val duration = c.getString(8)
                    val description = c.getString(9)
                    val favorite = c.getInt(10)
                    spells.add(Spell(id, level, name, page, keywords, type, castTime,
                        range, components, duration, description, favorite))
                }while(c.moveToNext())
            }
        }
        else{
            System.out.println("Empty")
        }
        spells = ArrayList(spells.sortedWith(compareBy { it.level }))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favorites)

        var query = favquery // "SELECT * FROM spells WHERE favorite = 1"
        //val search = findViewById<SearchView>(R.id.searchSpells)
        spells.clear()
        fillSpells(query)  // current search
        viewAdapter = SpellAdapter(spells) //use the created list
        setLayout()

    }
}