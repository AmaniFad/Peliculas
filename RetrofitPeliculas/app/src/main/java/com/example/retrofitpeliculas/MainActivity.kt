package com.example.retrofitpeliculas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.retrofitpeliculas.data.RetrofitService
import com.example.retrofitpeliculas.databinding.ActivityMainBinding
import com.example.retrofitpeliculas.data.RetrofitServiceFacroty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.retrofitpeliculas.data.model.Result

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    val service = RetrofitServiceFacroty.makeRetrofitService()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showRecyclerView()
    }
    private fun showRecyclerView(){
        val recyclerView = binding.recyclerViewFilms
        recyclerView.layoutManager =  GridLayoutManager(this, 1)
        CoroutineScope(Dispatchers.IO).launch {
            val filmsList = showMovies()
            runOnUiThread {
                recyclerView.adapter = RecyclerViewAdapter(filmsList)
            }
        }
    }
    private suspend fun showMovies() : List<Result> {
        val filmsList = mutableListOf<Result>();

        lifecycleScope.launch {
            val movies = service.listPopularMovies("9adf2368890d9ce6d99d6aa742e07209","US" )
            println(movies)

            for(page in 1..movies.total_results){
                val peli = movies.results.map { movie ->
                    Result(
                        title = movie.title,
                        release_date = movie.release_date.substring(0, 4),
                        overview = movie.overview,
                        poster_path = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/${movie.poster_path}"
                    )
                }
                filmsList.addAll(peli)
            }
        }
        return filmsList
    }
}