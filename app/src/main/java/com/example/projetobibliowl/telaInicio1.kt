package com.example.projetobibliowl

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class telaInicio1 : AppCompatActivity() {
    private lateinit var btnEntrar: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnEntrar = findViewById(R.id.btnEntrar)

        btnEntrar.setOnClickListener {
            val intent = Intent(this, telaLogin::class.java)
            startActivity(intent)

        }
    }

}

