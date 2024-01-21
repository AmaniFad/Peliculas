package com.example.retrofitpeliculas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import com.example.retrofitpeliculas.data.model.Result

class RecyclerViewAdapter(var films : List<Result>) : RecyclerView.Adapter<MoviesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MoviesViewHolder(layoutInflater.inflate(R.layout.film_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return films.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = films[position]
        holder.bind(item)

        holder.itemView.setOnClickListener{
            val context = holder.itemView.context
            val intent = Intent(context, MoveData::class.java)
            intent.putExtra("synopsis", item.overview)
            context.startActivity(intent)
        }
    }
}