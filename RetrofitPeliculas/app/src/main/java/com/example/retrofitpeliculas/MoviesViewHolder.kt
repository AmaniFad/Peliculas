package com.example.retrofitpeliculas

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitpeliculas.databinding.FilmLayoutBinding
import com.example.retrofitpeliculas.data.model.Result
import com.bumptech.glide.Glide

class MoviesViewHolder (view : View) : RecyclerView.ViewHolder(view) {

    val binding = FilmLayoutBinding.bind(view)

    fun bind(film : Result){
        binding.mvTitle.text = film.title
        binding.mvYear.text = film.release_date
        Glide.with(binding.poster.context).load(film.poster_path).into(binding.poster)
    }
}