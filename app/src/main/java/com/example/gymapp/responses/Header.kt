package com.example.gymapp.responses

import com.google.gson.annotations.SerializedName

data class Header(
    @SerializedName("codigo") val Codigo : String? = null,
    @SerializedName("descripcion ") val Descripcion : String? = null
)
