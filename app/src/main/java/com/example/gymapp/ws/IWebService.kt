package com.example.gymapp.ws

import com.example.gymapp.model.ClasesGrupales
import com.example.gymapp.model.Inscripcion
import com.example.gymapp.model.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface IWebService {
    @GET
    fun ListarUsuarios() : Call<List<Usuario>>
    @Headers("Content-Type: application/json")
    @POST("api/Usuarios")
    fun RegistrarUsuario(@Body usuario: Usuario) : Call<Usuario>
    @Headers("Content-Type: application/json")
    @POST("api/Usuarios/Login")
    fun IniciarSesion(@Body usuario: Usuario) : Call<Usuario>
    @GET("api/ClaseGrupal")
    fun ObtenerClases() : Call<List<ClasesGrupales>>
    @POST("api/Inscripcion/ByUser")
    fun ObtenerInscripciones(@Body usuario: Usuario) : Call<List<Inscripcion>>
}