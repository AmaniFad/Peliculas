package com.example.marvel.data

data class Character(
    val id: Int,
    val name : String,
    val description : String,
    val thumbnail : String,
    val thumbnailExt : String,
    val comics: List<String>
)