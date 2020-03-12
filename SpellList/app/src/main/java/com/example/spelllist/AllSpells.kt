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

    //spells is a spell list, Look to Spell.kt
    val spells = ArrayList<Spell>()

    //Setting current activity to Activity_All_Spells
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_spells)
        var query = "SELECT * FROM spells"
        // On click open info, using description screen.
        //bAllSpells.setOnClickListener {
        //    startActivity(Intent(this, AllSpells::class.java))
        //}
        val search = findViewById<SearchView>(R.id.searchSpells)
        bFilter.setOnClickListener {
            if(recyclerView != null) { // no work : (
                recyclerView.removeAllViewsInLayout(); // Removes all the views
            }
            Toast.makeText(this, "AM I DUMB", Toast.LENGTH_LONG)
            val queryItem = search.query
            if(queryItem.isNotBlank()){
                query += " WHERE name LIKE '%${queryItem}%'"
            }
            Log.d("We IN here", query)
            //viewAdapter = SpellAdapter(spells)
            val myDatabase = ActsDbHelper(this).readableDatabase
            // Using an sql query to grab all infomation from table
            val c = myDatabase.rawQuery(query, null)

            // Filling spells from database
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
            viewAdapter = SpellAdapter(spells)
        }


        //Creating a DatabaseHelper.kt object, ActsDbHelper to read
        val myDatabase = ActsDbHelper(this).readableDatabase
        // Using an sql query to grab all infomation from table
        val c = myDatabase.rawQuery(query, null)

        // Filling spells from database
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
        // Close the file, or in this case database when done
        myDatabase.close()

        //pulled from internet. It works. Sometimes it be like that.
        viewManager = LinearLayoutManager(this)
        viewAdapter = SpellAdapter(spells) // sending spells to SpellAdapter.kt for view
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
