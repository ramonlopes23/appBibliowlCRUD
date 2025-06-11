package com.example.projetobibliowl

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class LoginActivity() : AppCompatActivity() {

    private lateinit var txtEmail: EditText
    private lateinit var txtSenha: EditText
    private lateinit var btnAutenticar: Button
    private lateinit var btnNovoUsuario: Button
    private lateinit var btnVoltar: Button

    /*constructor(parcel: Parcel) : this()*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_inicio)

        txtEmail = findViewById(R.id.txtEmail)
        txtSenha = findViewById(R.id.txtSenha)
        btnAutenticar = findViewById(R.id.btnAutenticar)
        btnNovoUsuario = findViewById(R.id.btnNovoUsuario)
        btnVoltar = findViewById(R.id.btnVoltar)

        btnAutenticar.setOnClickListener {
            val email = txtEmail.text.toString()
            val senha = txtSenha.text.toString()
            val intent = Intent(this, telaAtualizar::class.java)
            startActivity(intent)
        }

        btnNovoUsuario.setOnClickListener {
            val intent = Intent(this, telaCadastro::class.java)
            startActivity(intent)
        }

        btnVoltar.setOnClickListener {
            val intent = Intent(this, telaInicio1::class.java)
            startActivity(intent)

            fun onBtnVoltarClick() {
                finish()
            }

            /*override fun writeToParcel(parcel: Parcel, flags: Int) {

            }

            override fun describeContents(): Int {
                return 0
            }

            companion object CREATOR : Parcelable.Creator<LoginActivity> {
                override fun createFromParcel(parcel: Parcel): LoginActivity {
                    return LoginActivity(parcel)
                }

                override fun newArray(size: Int): Array<LoginActivity?> {
                    return arrayOfNulls(size)

                }*/
        }
    }
}
