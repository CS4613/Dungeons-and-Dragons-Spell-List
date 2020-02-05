package com.example.spelllist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SpellAdapter : RecyclerView.Adapter<SpellAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.spells,parent,false)
        return ViewHolder(view  )
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

}
