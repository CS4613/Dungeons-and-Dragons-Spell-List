package com.example.spelllist

//This class is used within SpellAdapter.kt as a class holding data for the database
data class Spell(val id: Int, val level: String, val name: String, val page: String, val keywords: String,
                 val type: String, val castTime: String, val range: String,
                 val components: String, val duration: String, val description: String, val favorite: Int)
