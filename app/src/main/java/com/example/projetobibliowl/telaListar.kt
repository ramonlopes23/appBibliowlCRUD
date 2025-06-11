package com.example.projetobibliowl

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.cadastrousuario.DatabaseHelper

class telaListar : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var etProcurar: EditText
    private lateinit var btnProcurar: Button
    private lateinit var btnVoltarMenu: Button
    private lateinit var listViewUsuarios: ListView
    private lateinit var usuarioDAO: UsuarioDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_listar)


        dbHelper = DatabaseHelper(this)
        usuarioDAO = UsuarioDAO(this)
        etProcurar = findViewById(R.id.etProcurar)
        btnProcurar = findViewById(R.id.btnProcurar)
        btnVoltarMenu = findViewById(R.id.btnProcurarAtt)
        listViewUsuarios = findViewById(R.id.listViewUsuarios)

        btnProcurar.setOnClickListener {
            val valorPLocalizar = etProcurar.text.toString()


            if (valorPLocalizar.isNotEmpty()) {
                val usuariosLocalizados = usuarioDAO.criaListaDeUsuarios(valorPLocalizar)


                val adapter = UsuarioAdapter(this, usuariosLocalizados.toMutableList())
                listViewUsuarios.adapter = adapter
                adapter.notifyDataSetChanged()
            } else {
                etProcurar.error = "Campo de busca não pode estar vazio"
            }
        }
        btnVoltarMenu.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
            finish()
        }
    }
}
