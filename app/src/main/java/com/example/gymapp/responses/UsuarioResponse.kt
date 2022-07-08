package com.example.gymapp.responses

import com.example.gymapp.model.Usuario
import com.google.gson.annotations.SerializedName

data class UsuarioResponse(
    @SerializedName("header") val Header: Header? = null,
    @SerializedName("usuarioEntity") val UsuarioEntity: Usuario? = null,
    @SerializedName("usuarioList") val UsuarioList: List<Usuario>? = null
)
