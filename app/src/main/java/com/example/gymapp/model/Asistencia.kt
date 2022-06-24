package com.example.gymapp.model

import com.google.gson.annotations.SerializedName

data class Asistencia(
    @SerializedName("id") val Id: Int? = null,
    @SerializedName("fecha") val Fecha: String? = null,
    @SerializedName("usuarioId") val UsuarioId: Int? = null,
    @SerializedName("usuario") val Usuario: Usuario? = null
)
