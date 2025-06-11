package com.example.projetobibliowl

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.cadastrousuario.DatabaseHelper

class telaAtualizar : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var editTextProcurar: EditText
    private lateinit var btnProcurarAtt: Button
    private lateinit var btnMenuatt: Button
    private lateinit var listViewUsuarios: ListView
    private lateinit var usuarioDAO: UsuarioDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_atualizar)

        dbHelper = DatabaseHelper(this)
        usuarioDAO = UsuarioDAO(this)
        editTextProcurar = findViewById(R.id.etProcurar)
        btnProcurarAtt = findViewById(R.id.btnProcurarAtt)
        btnMenuatt = findViewById(R.id.btnMenuatt)
        listViewUsuarios = findViewById(R.id.listViewUsuarios)

        btnProcurarAtt.setOnClickListener {
            val valorPLocalizar = editTextProcurar.text.toString()

            val usuariosLocalizados = usuarioDAO.criaListaDeUsuarios(valorPLocalizar)

            val adapter = UsuarioAdapter(this, usuariosLocalizados.toMutableList())
            listViewUsuarios.adapter = adapter
        }

        btnMenuatt.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
            finish()
        }

        listViewUsuarios.setOnItemClickListener { _, _, position, _ ->
            val usuarioSelecionado = listViewUsuarios.adapter.getItem(position) as Usuario

            val dialogView = layoutInflater.inflate(R.layout.dialog_editar_usuario, null)
            val etNome = dialogView.findViewById<EditText>(R.id.etNome)
            val etEmail = dialogView.findViewById<EditText>(R.id.etEmail)
            val etCPF = dialogView.findViewById<EditText>(R.id.etCPF)
            val etDataNasc = dialogView.findViewById<EditText>(R.id.etDataNasc)
            val cbNivel = dialogView.findViewById<CheckBox>(R.id.cbNivel)

            etNome.setText(usuarioSelecionado.nome)
            etEmail.setText(usuarioSelecionado.email)
            etCPF.setText(usuarioSelecionado.cpf)
            etDataNasc.setText(usuarioSelecionado.dataNasc)
            cbNivel.isChecked = usuarioSelecionado.nivelAcesso == 1

            AlertDialog.Builder(this)
                .setTitle("Atualizar Usuário")
                .setView(dialogView)
                .setPositiveButton("Salvar") { _, _ ->
                    val usuarioAtualizado = Usuario(
                        id = usuarioSelecionado.id,
                        nome = etNome.text.toString(),
                        email = etEmail.text.toString(),
                        cpf = etCPF.text.toString(),
                        senha = usuarioSelecionado.senha,
                        dataNasc = etDataNasc.text.toString(),
                        nivelAcesso = if (cbNivel.isChecked) 1 else 0
                    )

                    val sucesso = usuarioDAO.atualizarUsuario(usuarioAtualizado)

                    if (sucesso > 0) {
                        Toast.makeText(this, "Usuário atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                        val listaAtualizada = usuarioDAO.criaListaDeUsuarios(editTextProcurar.text.toString())
                        listViewUsuarios.adapter = UsuarioAdapter(this, listaAtualizada.toMutableList())
                    } else {
                        Toast.makeText(this, "Erro ao atualizar usuário", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancelar", null)
                .show()


        }
    }
}