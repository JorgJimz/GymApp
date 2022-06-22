package com.example.gymapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Inscripcion(
    @SerializedName("id") val Id: Int? = null,
    @SerializedName("claseGrupalId") val ClaseGrupalId: Int? = null,
    @SerializedName("claseGrupal") val ClaseGrupal: ClasesGrupales? = null,
    @SerializedName("usuarioId") val UsuarioId: Int? = null
): Serializable
