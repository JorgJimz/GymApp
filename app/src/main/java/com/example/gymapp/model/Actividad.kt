package com.example.gymapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Actividad(
    @SerializedName("id")  val Id: Int? = null,
    @SerializedName("nombre")  val Nombre: String = "",
    @SerializedName("descripcion")  val Descripcion: String = "",
    @SerializedName("duracionMinutos")  val DuracionMinutos: Int? = null
): Serializable
