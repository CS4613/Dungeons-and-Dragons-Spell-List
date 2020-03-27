package com.example.spelllist

import android.content.Intent
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.spells.view.*

// An array of Spell (Spell.kt), passed in.
class SpellAdapter(private val spellList : ArrayList<Spell>) : RecyclerView.Adapter<SpellAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val spellName = view.spellName // from the xml view...
        val spellLevel = view.level
        val spellInfo = view.info
        //val spellListx = itemView.findViewById<TextView>(R.id.spellList)
    }

    // creating each spot in the recycle view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.spells,parent,false))
    }

    // The size of the current total database
    override fun getItemCount(): Int {
        return spellList.size
    }
    // holder, from viewHolder = current position name
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.spellName.text = spellList[position].name
        holder.spellLevel.text = spellList[position].level
        holder.spellInfo.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context,Description::class.java)
            intent.putExtra("name",spellList[position].name)
            intent.putExtra("keyword",spellList[position].keywords)
            intent.putExtra("level",spellList[position].type)
            intent.putExtra("components",spellList[position].components)
            intent.putExtra("castTime",spellList[position].castTime)
            intent.putExtra("range",spellList[position].range)
            intent.putExtra("desc",spellList[position].description)

            context.startActivity(intent)
        }
        //holder?.txtTitle?.text = spellList[position].type
    }

}
