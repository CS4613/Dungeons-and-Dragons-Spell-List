package com.example.spelllist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.spells.view.*

class SpellAdapter(private val spellList : ArrayList<Spell>) : RecyclerView.Adapter<SpellAdapter.ViewHolder>() {


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val spellName = view.spellName
        //val spellListx = itemView.findViewById<TextView>(R.id.spellList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.spells,parent,false))
    }

    override fun getItemCount(): Int {
        return spellList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.spellName.text = spellList[position].name
        //holder?.txtTitle?.text = spellList[position].type
    }

}
