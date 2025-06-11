package com.example.projetobibliowl

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class telaCadastro : AppCompatActivity() {

    private lateinit var editTextNome: EditText
    private lateinit var checkBoxNivel: CheckBox
    private lateinit var btnSalvar: Button
    private lateinit var editTextEmail: EditText
    private lateinit var editTextCPF: EditText
    private lateinit var editTextSenha: EditText
    private lateinit var editTextDataNasc: EditText
    private lateinit var tvVoltar: TextView
    private lateinit var btnMenu: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_cadastro)

        val logado = intent.getBooleanExtra("logado", false)

        editTextNome = findViewById(R.id.editTextNome)
        checkBoxNivel = findViewById(R.id.checkBoxNivel)
        btnSalvar = findViewById(R.id.btnSalvar)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextCPF = findViewById(R.id.editTextCPF)
        editTextSenha = findViewById(R.id.editTextSenha)
        editTextDataNasc = findViewById(R.id.editTextDate)
        tvVoltar = findViewById(R.id.tvVoltar)
        btnMenu = findViewById(R.id.btnMenu)

        if (logado) {
            btnMenu.visibility = View.VISIBLE
            tvVoltar.visibility = View.GONE
        } else {
            btnMenu.visibility = View.GONE
            tvVoltar.visibility = View.VISIBLE
        }

        btnSalvar.setOnClickListener {
            val nome = editTextNome.text.toString()
            val email = editTextEmail.text.toString()
            val cpf = editTextCPF.text.toString()
            val senha = editTextSenha.text.toString()
            val dataNasc = editTextDataNasc.text.toString()
            val nivelAcesso = if (checkBoxNivel.isChecked) 1 else 0

            val novoUsuario = Usuario(0, nome, email, cpf, senha, dataNasc, nivelAcesso)

            UsuarioDAO(this).insertUsuario(novoUsuario)

            Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show()

            val resultIntent = Intent()
            resultIntent.putExtra("novoUsuario", novoUsuario)
            setResult(Activity.RESULT_OK, resultIntent)

            finish()
        }

        tvVoltar.setOnClickListener {
            val intent = Intent(this, telaLogin::class.java)
            startActivity(intent)
            finish()
        }

        btnMenu.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
            finish()
        }
    }
}
