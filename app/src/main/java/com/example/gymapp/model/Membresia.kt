package com.example.gymapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.LocalDateTime

data class Membresia(
    @SerializedName("id") val Id: Int? = null,
    @SerializedName("fecha") val Fecha: LocalDateTime? = null,
    @SerializedName("fechaInicio") val FechaInicio: LocalDateTime? = null,
    @SerializedName("fechaFin") val FechaFin: LocalDateTime? = null,
    @SerializedName("usuarioId") val UsuarioId: Int? = null,
    @SerializedName("usuario") val Usuario: Usuario? = null,
    @SerializedName("status") val Status: Int? = null
): Serializable
