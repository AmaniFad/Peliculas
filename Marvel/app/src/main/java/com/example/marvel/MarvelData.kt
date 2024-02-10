package com.example.marvel

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MoveData  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.film_data_layout)
        val btnBack: Button = findViewById(R.id.backButton)

        val script = intent.getStringExtra("script")
        val tvScript: TextView = findViewById(R.id.synopsisData)
        tvScript.text = script
        btnBack.setOnClickListener{finish()}
    }
}