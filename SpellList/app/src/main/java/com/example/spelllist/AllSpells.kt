package com.example.spelllist

import android.database.Cursor
import android.database.sqlite.SQLiteCursor
import android.database.sqlite.SQLiteCursorDriver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_all_spells.*


// Toast is great // Toast.makeText(this,test.getString(1),Toast.LENGTH_LONG).show()
class AllSpells : AppCompatActivity() {
    // Setting up variables for the RecyclerView use
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    //Basic sql query to select everything from main database
    private var blankquery = "SELECT * FROM spells"
    private var prevFilter = ""
    //spells is a spell list, Look to Spell.kt
    var spells = ArrayList<Spell>()

    //called when the recycler view contents is being updated or initialized
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
                    val name = c.getString(1)
                    val page = c.getString(2)
                    val keywords = c.getString(3)
                    val type = c.getString(4)
                    val castTime = c.getString(5)
                    val range = c.getString(6)
                    val components = c.getString(7)
                    val duration = c.getString(8)
                    val description = c.getString(9)
                    spells.add(Spell(level, name, page, keywords, type, castTime,
                        range, components, duration, description))
                }while(c.moveToNext())
            }
        }
        else{
            System.out.println("Empty")
        }
        spells = ArrayList(spells.sortedWith(compareBy { it.level }))
    }

    /*Setting current activity to Activity_All_Spells
    Note: Sql does not care about case, so case is ignored by
    dropping everything to lowercase in the search to not refresh
    unnecessarily
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_spells)
        var query = blankquery // "SELECT * FROM spells"

        val search = findViewById<SearchView>(R.id.searchSpells)
        bFilter.setOnClickListener {
            val queryItem = search.query
            //Don't repeat work if there is no change
            if(prevFilter != queryItem.toString().toLowerCase()) {
                if (queryItem.isNotBlank()) {
                    query += " WHERE name LIKE '%${queryItem}%'"
                } else {
                    query = blankquery
                }
                prevFilter = queryItem.toString().toLowerCase()
                spells.clear() // clear out old info
                Log.d("We IN here", query)
                fillSpells(query)  // current search
                query = blankquery // reset  query
                viewAdapter = SpellAdapter(spells) //use the created list
                setLayout()
            }
        }

        bClear.setOnClickListener {
            var query = blankquery // "SELECT * FROM spells"
            prevFilter = blankquery
            spells.clear()
            Log.d("We IN clear function", query)
            fillSpells(query)
            viewAdapter = SpellAdapter(spells)
            setLayout()
        }

        fillSpells(query)
        setLayout()
    }

}
