package com.example.gymapp.model

import com.google.gson.annotations.SerializedName

data class Perfil(
    val Id: Int? = null,
   val Descripcion: String = "",
){
    override fun toString(): String {
        return Descripcion
    }
}
