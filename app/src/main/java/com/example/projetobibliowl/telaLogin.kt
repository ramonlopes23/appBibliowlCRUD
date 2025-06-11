package com.example.projetobibliowl

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cadastrousuario.DatabaseHelper
import org.mindrot.jbcrypt.BCrypt

class telaLogin : AppCompatActivity() {
    private lateinit var txtEmail: EditText
    private lateinit var txtSenha: EditText
    private lateinit var btnAutenticar: Button
    private lateinit var btnNovoUsuario: Button
    private lateinit var btnVoltar: Button
    private lateinit var usuarioDAO: UsuarioDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_inicio)

        txtEmail = findViewById(R.id.txtEmail)
        txtSenha = findViewById(R.id.txtSenha)
        btnAutenticar = findViewById(R.id.btnAutenticar)
        btnNovoUsuario = findViewById(R.id.btnNovoUsuario)
        btnVoltar = findViewById(R.id.btnVoltar)

        usuarioDAO = UsuarioDAO(this)



        btnAutenticar.setOnClickListener {
            val email = txtEmail.text.toString()
            val senha = txtSenha.text.toString()

            if (email.isNotEmpty() && senha.isNotEmpty()) {
                if (validarLogin(email, senha)) {
                    val intent = Intent(this, Menu::class.java)
                    startActivity(intent)
                    finish()  // Finaliza a tela de login para evitar voltar
                } else {
                    Toast.makeText(this, "Email ou senha inválidos", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
            
           
        }

        btnNovoUsuario.setOnClickListener {
            val intent = Intent(this, telaCadastro::class.java)
            intent.putExtra("logado", false)
            startActivity(intent)
        }

        btnVoltar.setOnClickListener {
            val intent = Intent(this, telaInicio1::class.java)
            startActivity(intent)


            fun onBtnVoltarClick() {
                finish()
            }

        }
    }

    private fun validarLogin(email: String, senha: String): Boolean {
        val usuario = usuarioDAO.getUsuarioByEmail(email)

        return if (usuario != null) {
            BCrypt.checkpw(senha, usuario.senha)
        } else {
            false
        }
    }
}
