package com.example.marvel.data

import com.example.marvel.MarvelRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class SearchCharactersUseCase @Inject constructor(
    private val repository: MarvelRepository
){
    operator fun invoke(search:String):Flow<Response<List<Character>>> = flow{
        try {
            emit(Response.Loading<List<Character>>())
            val list = repository.getAllSearchCharacters(search).data.results.map{
                it.toCharacter()
            }
            emit(Response.Success<List<Character>>(list))
        }
        catch (e:HttpException){
            emit(Response.Error<List<Character>>(e.printStackTrace().toString()))
        }
        catch (e:IOException){
            emit(Response.Error<List<Character>>(e.printStackTrace().toString()))
        }
    }
}