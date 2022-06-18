package com.example.gymapp.model

data class ClasesGrupales(
    val Id: Int? = null,
    val FechaHora: String = "",
    val InstructorId: Int? = null,
    val Instructor: Usuario? = null,
    val ActividadId: Int?  = null,
    val Actividad: Actividad? = null,
    val Status: Int? = null,
)
