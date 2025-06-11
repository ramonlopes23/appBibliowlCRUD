package com.example.projetobibliowl

import android.os.Parcel
import android.os.Parcelable

data class Usuario(
    val id: Long,
    val nome: String,
    val email: String,
    val cpf: String,
    val senha: String,
    val dataNasc: String,
    val nivelAcesso: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(nome)
        parcel.writeString(email)
        parcel.writeString(cpf)
        parcel.writeString(senha)
        parcel.writeString(dataNasc)
        parcel.writeInt(nivelAcesso)

    }
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Usuario> {
        override fun createFromParcel(parcel: Parcel): Usuario {
            return Usuario(parcel)
        }

        override fun newArray(size: Int): Array<Usuario?> {
            return arrayOfNulls(size)
        }
    }
}