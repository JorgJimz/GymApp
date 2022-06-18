package com.example.gymapp.model

data class Usuario(
    val Id: Int? = null,
    val Nombres: String = "",
    val TipoDocumento: Int? = null,
    val NumeroDocumento: String = "",
    val Apellidos: String = "",
    val FechaNacimiento: String = "",
    val Email: String = "",
    val Telefono1: String = "",
    val Telefono2: String = "",
    val Perfil: Int? = null,
    val Contrasena: String = "",
    val Status: Int? = null
)