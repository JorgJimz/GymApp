package com.example.gymapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime

data class Membresia(
    @SerializedName("id") val Id: Int? = null,
    @SerializedName("fecha") val Fecha: String? = null,
    @SerializedName("tipoMembresia") val TipoMembresia: String = "",
    @SerializedName("fechaInicio") val FechaInicio: String? = "",
    @SerializedName("fechaFin") val FechaFin: String? = "",
    @SerializedName("usuarioId") val UsuarioId: Int? = null,
    @SerializedName("usuario") val Usuario: Usuario? = null,
    @SerializedName("status") val Status: Int? = null
): Serializable
