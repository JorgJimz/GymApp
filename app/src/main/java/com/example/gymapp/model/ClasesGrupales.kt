package com.example.gymapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ClasesGrupales(
    @SerializedName("id") val Id: Int? = null,
    @SerializedName("fechaHora") val FechaHora: String = "",
    @SerializedName("instructorId") val InstructorId: Int? = null,
    @SerializedName("instructor") val Instructor: Usuario? = null,
    @SerializedName("actividadId") val ActividadId: Int?  = null,
    @SerializedName("actividad") val Actividad: Actividad? = null,
    @SerializedName("status") val Status: Int? = null,
): Serializable
