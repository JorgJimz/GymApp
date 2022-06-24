package com.example.gymapp.ws

import com.example.gymapp.model.*
import retrofit2.Call
import retrofit2.http.*

interface IWebService {
    @GET
    fun ListarUsuarios() : Call<List<Usuario>>
    @Headers("Content-Type: application/json")
    @POST("api/Usuario")
    fun RegistrarUsuario(@Body usuario: Usuario) : Call<Usuario>
    @Headers("Content-Type: application/json")
    @POST("api/Usuario/Login")
    fun IniciarSesion(@Query("usuario") usuario: String, @Query("contrasena") contrasena: String) : Call<Usuario>
    @POST("api/Usuario/ByDocumentNumber")
    fun BuscarUsuario(@Query("numero") numero: String) : Call<Usuario>
    @GET("api/ClaseGrupal")
    fun ObtenerClases() : Call<List<ClasesGrupales>>
    @POST("api/Inscripcion/ByUser")
    fun ObtenerInscripciones(@Body usuario: Usuario) : Call<List<Inscripcion>>
    @POST("api/Inscripcion")
    fun GenerarInscripciones(@Body inscripcion: Inscripcion) : Call<Inscripcion>
    @POST("api/Membresia")
    fun RegistrarMembresia(@Body membresia: Membresia) : Call<Membresia>
    @POST("api/Asistencia")
    fun RegistrarAsistencia(@Body asistencia: Asistencia) : Call<Asistencia>
}