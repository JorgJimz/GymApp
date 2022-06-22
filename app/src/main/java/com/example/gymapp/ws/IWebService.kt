package com.example.gymapp.ws

import com.example.gymapp.model.ClasesGrupales
import com.example.gymapp.model.Inscripcion
import com.example.gymapp.model.Usuario
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
    @GET("api/ClaseGrupal")
    fun ObtenerClases() : Call<List<ClasesGrupales>>
    @POST("api/Inscripcion/ByUser")
    fun ObtenerInscripciones(@Body usuario: Usuario) : Call<List<Inscripcion>>
    @POST("api/Inscripcion")
    fun GenerarInscripciones(@Body inscripcion: Inscripcion) : Call<Inscripcion>
}