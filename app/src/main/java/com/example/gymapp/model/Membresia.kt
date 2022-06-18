package com.example.gymapp.model

import java.time.LocalDateTime

data class Membresia(
    val Id: Int? = null,
    val Fecha: LocalDateTime? = null,
    val FechaInicio: LocalDateTime? = null,
    val FechaFin: LocalDateTime? = null,
    val UsuarioId: Int? = null,
    val Usuario: Usuario? = null,
    val Status: Int? = null
)
