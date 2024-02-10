package com.example.marvel.data

data class MarvelListState(
    val isLoading: Boolean = false,
    val characterList : List<Character> = emptyList(),
    val error : String = ""
)
