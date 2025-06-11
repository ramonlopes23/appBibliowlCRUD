package com.example.projetobibliowl

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.cadastrousuario.DatabaseHelper
import org.mindrot.jbcrypt.BCrypt
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UsuarioDAO(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "myApp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "Usuario"  // Mude para o nome da tabela que você criou (sensível a maiúsculas)
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOME = "nome"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_CPF = "cpf"
        private const val COLUMN_SENHA = "senha"
        private const val COLUMN_DATA_NASC = "dataNasc"
        private const val COLUMN_NIVEL_ACESSO = "nivelAcesso"
    }

    private val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreate(db: SQLiteDatabase) {
        val sqlCreate = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOME VARCHAR(100) NOT NULL,
                $COLUMN_EMAIL VARCHAR(50) NOT NULL UNIQUE,
                $COLUMN_CPF VARCHAR(11),
                $COLUMN_SENHA VARCHAR(60),
                $COLUMN_DATA_NASC DATE,
                $COLUMN_NIVEL_ACESSO INTEGER
            )
        """.trimIndent()

        db.execSQL(sqlCreate)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }


    fun insertUsuario(usuario: Usuario) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOME, usuario.nome)
            put(COLUMN_EMAIL, usuario.email)
            put(COLUMN_CPF, usuario.cpf)
            put(COLUMN_SENHA, BCrypt.hashpw(usuario.senha, BCrypt.gensalt()))
            put(COLUMN_DATA_NASC, usuario.dataNasc)  // Assumindo dataNasc já em formato ISO yyyy-MM-dd
            put(COLUMN_NIVEL_ACESSO, usuario.nivelAcesso)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }


    fun getUsuarioByEmail(email: String): Usuario? {
        val db = readableDatabase
        val selection = "${DatabaseHelper.COLUMN_EMAIL} = ?"
        val selectionArgs = arrayOf(email)

        val cursor = db.query(
            DatabaseHelper.TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null, null, null
        )

        if (cursor != null && cursor.moveToFirst()) {
            val usuario = Usuario(
                id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                nome = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOME)),
                email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL)),
                cpf = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CPF)),
                senha = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SENHA)),
                dataNasc = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATA_NASC)),
                nivelAcesso = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_NIVEL_ACESSO))
            )
            cursor.close()
            return usuario
        }
        cursor?.close()
        return null
    }

    fun deletarUsuario(id: Long): Int {
        val db = writableDatabase
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(id.toString())
        val deleted = db.delete(TABLE_NAME, selection, selectionArgs)
        db.close()
        return deleted
    }

    fun atualizarUsuario(usuario: Usuario): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOME, usuario.nome)
            put(COLUMN_EMAIL, usuario.email)
            put(COLUMN_CPF, usuario.cpf)
            put(COLUMN_SENHA, BCrypt.hashpw(usuario.senha, BCrypt.gensalt()))
            put(COLUMN_DATA_NASC, usuario.dataNasc)
            put(COLUMN_NIVEL_ACESSO, usuario.nivelAcesso)
        }
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(usuario.id.toString())
        val updated = db.update(TABLE_NAME, values, selection, selectionArgs)
        db.close()
        return updated
    }

    fun procuraUsuarioPorEmail(email: String): Usuario? {
        val db = readableDatabase
        val projection = arrayOf(COLUMN_ID, COLUMN_NOME, COLUMN_EMAIL, COLUMN_CPF, COLUMN_SENHA, COLUMN_DATA_NASC, COLUMN_NIVEL_ACESSO)
        val selection = "$COLUMN_EMAIL = ?"
        val selectionArgs = arrayOf(email)
        val cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null)

        var usuario: Usuario? = null
        if (cursor.moveToFirst()) {
            usuario = criarUsuario(cursor)
        }
        cursor.close()
        db.close()
        return usuario
    }

    fun procuraUsuarioPorNome(nome: String): Usuario? {
        val db = readableDatabase
        val projection = arrayOf(COLUMN_ID, COLUMN_NOME, COLUMN_EMAIL, COLUMN_CPF, COLUMN_SENHA, COLUMN_DATA_NASC, COLUMN_NIVEL_ACESSO)
        val selection = "$COLUMN_NOME = ?"
        val selectionArgs = arrayOf(nome)
        val cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null)

        var usuario: Usuario? = null
        if (cursor.moveToFirst()) {
            usuario = criarUsuario(cursor)
        }
        cursor.close()
        db.close()
        return usuario
    }

    fun procuraUsuarioPorEmailESenha(email: String, senha: String): Usuario? {
        val db = readableDatabase
        val projection = arrayOf(COLUMN_ID, COLUMN_NOME, COLUMN_EMAIL, COLUMN_CPF, COLUMN_SENHA, COLUMN_DATA_NASC, COLUMN_NIVEL_ACESSO)
        val selection = "$COLUMN_EMAIL = ?"
        val selectionArgs = arrayOf(email)
        val cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null)

        var usuario: Usuario? = null
        if (cursor.moveToFirst()) {
            val storedPassword = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SENHA))
            if (BCrypt.checkpw(senha, storedPassword)) {
                usuario = criarUsuario(cursor)
            }
        }
        cursor.close()
        db.close()
        return usuario
    }

    fun criaListaDeUsuarios(valor: String): List<Usuario> {
        val db = readableDatabase
        val projection = arrayOf(COLUMN_ID, COLUMN_NOME, COLUMN_EMAIL, COLUMN_CPF, COLUMN_SENHA, COLUMN_DATA_NASC, COLUMN_NIVEL_ACESSO)
        val selection = "$COLUMN_EMAIL LIKE ? OR $COLUMN_CPF LIKE ? OR $COLUMN_NOME LIKE ?"
        val likeArg = "%$valor%"
        val selectionArgs = arrayOf(likeArg, likeArg, likeArg)
        val cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null)

        val usuarios = mutableListOf<Usuario>()
        while (cursor.moveToNext()) {
            usuarios.add(criarUsuario(cursor))
        }
        cursor.close()
        db.close()
        return usuarios
    }

    private fun criarUsuario(cursor: Cursor): Usuario {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME))
        val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
        val cpf = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CPF))
        val senha = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SENHA))
        val dataNasc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATA_NASC))
        val nivelAcesso = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NIVEL_ACESSO))
        return Usuario(id, nome, email, cpf, senha, dataNasc, nivelAcesso)
    }

    fun selectAll(): ArrayList<Usuario> {
        val usuarios = ArrayList<Usuario>()
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                usuarios.add(criarUsuario(cursor))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return usuarios
    }
}