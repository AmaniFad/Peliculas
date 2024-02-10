package com.example.marvel.data

import com.example.marvel.MarvelRepository
import com.example.marvel.MarvelRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.marvel.data.dto.CharactersDTO

import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

interface RetrofitService {
    @GET("/v1/public/characters")
    suspend fun getAllCharacters(
        @Query("apikey") apikey: String = RetrofitServiceFacroty.API_KEY,
        @Query("ts") ts: String = RetrofitServiceFacroty.timeStamp,
        @Query("hash") hash: String = RetrofitServiceFacroty.hash(),
        @Query("offset") offset: String
    ):CharactersDTO

    @GET("/v1/public/characters")
    suspend fun  getAllSearchCharacters(
        @Query("apikey") apikey: String = RetrofitServiceFacroty.API_KEY,
        @Query("ts") ts: String = RetrofitServiceFacroty.timeStamp,
        @Query("hash") hash: String = RetrofitServiceFacroty.hash(),
        @Query("nameStartsWith") search :String
    ): CharactersDTO

}
 object RetrofitServiceFacroty{
    const val  BASE_URL = "https://gateway.marvel.com"
    val timeStamp = Timestamp(System.currentTimeMillis()).time.toString();
    const val API_KEY = "35fe75710471f389d484caacf9b4826b"
    const val PRIVATE_KEY = "b50c65f43b9aa2affe99d83c4fb0011406c58d3a"
    const val limit = "20"
    fun hash():String {
        val input = "$timeStamp$PRIVATE_KEY$API_KEY"
        val md = MessageDigest.getInstance("MD5")
        return  BigInteger(1,md.digest(input.toByteArray())).toString(16).padStart(32,'0')
    }

     fun provideMarvelApi(): RetrofitService{
         return Retrofit.Builder()
             .baseUrl(RetrofitServiceFacroty.BASE_URL)
             .addConverterFactory(GsonConverterFactory.create())
             .build()
             .create(RetrofitService::class.java)
     }
     fun provideMarvelRepository(api:RetrofitService):MarvelRepository{
         return MarvelRepositoryImpl(api)
     }
}