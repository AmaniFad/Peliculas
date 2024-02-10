package com.example.marvel

import com.example.marvel.data.dto.CharactersDTO
import com.example.marvel.data.RetrofitService
import javax.inject.Inject

class MarvelRepositoryImpl @Inject constructor(
    private val api:RetrofitService
): MarvelRepository{
    override suspend fun getAllCharacter(offset: Int): CharactersDTO {
        return api.getAllCharacters(offset = offset.toString())
    }
    override suspend fun getAllSearchCharacters(search: String):CharactersDTO {
        return api.getAllSearchCharacters(search =search)
    }
}