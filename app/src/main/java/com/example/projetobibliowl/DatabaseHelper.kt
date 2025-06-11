package com.example.cadastrousuario

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "myAppDB.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "Usuario"
        const val COLUMN_ID = "id"
        const val COLUMN_NOME = "nome"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_CPF = "cpf"
        const val COLUMN_SENHA = "senha"
        const val COLUMN_DATA_NASC = "dataNasc"
        const val COLUMN_NIVEL_ACESSO = "nivelAcesso"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableSQL = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOME VARCHAR(100) NOT NULL,
                $COLUMN_EMAIL VARCHAR(50) NOT NULL UNIQUE,
                $COLUMN_CPF VARCHAR(11),
                $COLUMN_SENHA VARCHAR(30),
                $COLUMN_DATA_NASC DATE,
                $COLUMN_NIVEL_ACESSO INTEGER
            )
        """.trimIndent()

        db.execSQL(createTableSQL)

        // Insere o usuário admin
        val insertAdminSQL = """
            INSERT INTO $TABLE_NAME ($COLUMN_NOME, $COLUMN_EMAIL, $COLUMN_CPF, $COLUMN_SENHA, $COLUMN_DATA_NASC, $COLUMN_NIVEL_ACESSO) 
            VALUES ('Administrador', 'adm@empresa.com.br', '1234', '543210', '1965-05-08', 1)
        """.trimIndent()
        db.execSQL(insertAdminSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Aqui você pode adicionar lógica de upgrade se necessário
        // Exemplo: Caso a tabela precise ser recriada
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}
