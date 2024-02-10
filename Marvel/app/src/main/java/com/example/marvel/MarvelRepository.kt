package com.example.marvel

import android.icu.text.StringSearch
import com.example.marvel.data.dto.CharactersDTO
interface MarvelRepository {
    suspend fun getAllCharacter(offset: Int):CharactersDTO
    suspend fun  getAllSearchCharacters(search: String):CharactersDTO
}