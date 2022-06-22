package com.example.gymapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Usuario(
    @SerializedName("id") val Id: Int? = null,
    @SerializedName("nombres") val Nombres: String = "",
    @SerializedName("tipoDocumento") val TipoDocumento: Int? = null,
    @SerializedName("numeroDocumento") val NumeroDocumento: String = "",
    @SerializedName("apellidos") val Apellidos: String = "",
    @SerializedName("fechaNacimiento") val FechaNacimiento: String = "",
    @SerializedName("email") val Email: String = "",
    @SerializedName("telefono1") val Telefono1: String = "",
    @SerializedName("telefono2") val Telefono2: String = "",
    @SerializedName("perfil") val Perfil: Int? = null,
    @SerializedName("contrasena") val Contrasena: String = "",
    @SerializedName("status") val Status: Int? = null
): Serializable