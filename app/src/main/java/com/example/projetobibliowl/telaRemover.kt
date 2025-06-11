package com.example.projetobibliowl

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cadastrousuario.DatabaseHelper

class telaRemover : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var editTextProcurar: EditText
    private lateinit var btnProcurar: Button
    private lateinit var btnVoltarMenu: Button
    private lateinit var listViewUsuarios: ListView
    private lateinit var usuarioDAO: UsuarioDAO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_remover)

        dbHelper = DatabaseHelper(this)
        usuarioDAO = UsuarioDAO(this)
        editTextProcurar = findViewById(R.id.etProcurar)
        btnProcurar = findViewById(R.id.btnProcurar)
        btnVoltarMenu = findViewById(R.id.btnProcurarAtt)
        listViewUsuarios = findViewById(R.id.listViewUsuarios)


        btnProcurar.setOnClickListener {
            val valorPLocalizar = editTextProcurar.text.toString()


            val usuariosLocalizados = usuarioDAO.criaListaDeUsuarios(valorPLocalizar)


            val adapter = UsuarioAdapter(this, usuariosLocalizados.toMutableList())
            listViewUsuarios.adapter = adapter
        }

        btnVoltarMenu.setOnClickListener{
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
            finish()
        }


        listViewUsuarios.setOnItemClickListener { _, _, position, _ ->
            val usuarioSelecionado = (listViewUsuarios.adapter as UsuarioAdapter).getItem(position) as Usuario
            excluirUsuario(usuarioSelecionado)
        }
    }


    private fun excluirUsuario(usuario: Usuario) {
        val sucesso = usuarioDAO.deletarUsuario(usuario.id)

        if (sucesso > 0) {
            Toast.makeText(this, "Usuário excluído com sucesso!", Toast.LENGTH_SHORT).show()

            // Atualiza a lista de usuários após exclusão
            val valorPLocalizar = editTextProcurar.text.toString()
            val usuariosLocalizados = usuarioDAO.criaListaDeUsuarios(valorPLocalizar)
            (listViewUsuarios.adapter as UsuarioAdapter).atualizarUsuarios(usuariosLocalizados)
        } else {
            Toast.makeText(this, "Erro ao excluir o usuário", Toast.LENGTH_SHORT).show()
        }
    }
}
