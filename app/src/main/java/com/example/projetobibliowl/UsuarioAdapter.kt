package com.example.projetobibliowl

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.example.projetobibliowl.R

class UsuarioAdapter(
    private val context: Context,
    private var usuarios: MutableList<Usuario>
) : BaseAdapter() {

    override fun getCount(): Int {
        return usuarios.size
    }

    override fun getItem(position: Int): Any {
        return usuarios[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_usuario, parent, false)

        val usuario = usuarios[position]

        val textViewNome = view.findViewById<TextView>(R.id.textViewNome)
        val textViewEmail = view.findViewById<TextView>(R.id.textViewEmail)
        val textViewCPF = view.findViewById<TextView>(R.id.textViewCPF)
        val textViewDataNasc = view.findViewById<TextView>(R.id.textViewDataNasc)
        val textViewNivel = view.findViewById<TextView>(R.id.textViewNivel)


        textViewNome.text = usuario.nome
        textViewEmail.text = usuario.email
        textViewCPF.text = usuario.cpf
        textViewDataNasc.text = usuario.dataNasc
        textViewNivel.text = if (usuario.nivelAcesso == 1) "Administrador" else "Usuário"

        return view
    }

    fun atualizarUsuarios(novosUsuarios: List<Usuario>) {
        usuarios.clear()
        usuarios.addAll(novosUsuarios)
        notifyDataSetChanged()
    }
}
