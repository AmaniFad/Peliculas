package com.example.retrofitpeliculas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofitpeliculas.databinding.FilmDataLayoutBinding

class MoveData  : AppCompatActivity() {
    private lateinit var binding: FilmDataLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FilmDataLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val synopsis = intent.getStringExtra("synopsis")
        binding.synopsisData.text = synopsis

        binding.backButton.setOnClickListener { finish() }
    }
}