package com.example.projetobibliowl

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Menu : AppCompatActivity() {

    private lateinit var tvLogoff: TextView
    private lateinit var btnCadastrar: Button
    private lateinit var btnListar: Button
    private lateinit var btnAtualizar: Button
    private lateinit var btnRemover: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_menu)

        btnCadastrar = findViewById(R.id.btnCadastrar)
        btnListar = findViewById(R.id.btnListar)
        btnAtualizar = findViewById(R.id.btnAtualizar)
        btnRemover = findViewById(R.id.btnRemover)
        tvLogoff = findViewById(R.id.tvLogoff)

        btnCadastrar.setOnClickListener {
            val intent = Intent(this, telaCadastro::class.java)
            intent.putExtra("logado", true)
            startActivity(intent)
        }

        btnListar.setOnClickListener{

            val intent = Intent(this, telaListar::class.java)
            startActivity(intent)
        }

        btnAtualizar.setOnClickListener{
            val intent = Intent(this, telaAtualizar::class.java)
            startActivity(intent)
        }

        btnRemover.setOnClickListener{
            val intent = Intent(this, telaRemover::class.java)
            startActivity(intent)
        }

        tvLogoff.setOnClickListener {
            val intent = Intent(this, telaLogin::class.java)
            startActivity(intent)

        }

    }
    }
