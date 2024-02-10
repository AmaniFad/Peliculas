package com.example.marvel

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvel.data.Character

class RecycleViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    val charactersTitle  : TextView= view.findViewById(R.id.mvTitle)
    val charactersLayout : LinearLayout= view.findViewById(R.id.charactersLinearLayout)
    val charactersPoster : ImageView = view.findViewById(R.id.poster)

}