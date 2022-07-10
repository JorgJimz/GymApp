package com.example.gymapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.gymapp.client.WebServiceClient
import com.example.gymapp.model.Usuario
import com.example.gymapp.responses.UsuarioResponse
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    private lateinit var pbLogin: ProgressBar
    private lateinit var btnIniciarSesion: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val edtUsuario: EditText = findViewById(R.id.edtUsuario)
        val edtContrasena: EditText = findViewById(R.id.edtContrasena)

        pbLogin = findViewById(R.id.pbLogin)
        val btnRegistrar: Button = findViewById(R.id.BtnRegistrar)
        btnRegistrar.setOnClickListener {
            val intent = Intent(applicationContext, UserRegisterActivity::class.java)
            startActivity(intent)
        }

        btnIniciarSesion  = findViewById(R.id.BtnLogin)
        btnIniciarSesion.setOnClickListener {
            var sentUser = Usuario(
                NumeroDocumento = edtUsuario.text.toString(),
                Contrasena = edtContrasena.text.toString()
            )
            Login(sentUser)
        }
    }

    fun Login(sentUser: Usuario) {
        btnIniciarSesion.isVisible = false
        pbLogin.isVisible = true
        var request =
            WebServiceClient.retrofitService.IniciarSesion(
                sentUser.NumeroDocumento, sentUser.Contrasena
            ).enqueue(
                object : Callback<UsuarioResponse> {
                    override fun onFailure(call: Call<UsuarioResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        btnIniciarSesion.isVisible = true
                        pbLogin.isVisible = false
                    }

                    override fun onResponse(
                        call: Call<UsuarioResponse>,
                        response: Response<UsuarioResponse>
                    ) {
                        val logged = response.body()
                        if (logged?.UsuarioEntity != null) {
                            val settings: SharedPreferences =
                                PreferenceManager.getDefaultSharedPreferences(applicationContext)
                            val editor: SharedPreferences.Editor = settings.edit()

                            if(logged?.Header?.Descripcion.isNullOrEmpty()){
                                logged.UsuarioEntity.Id?.toInt()?.let { editor.putInt("id", it) }
                                editor.putString("logged", Gson().toJson(logged.UsuarioEntity))
                                editor.commit()
                                val intent = Intent(applicationContext, MainActivity::class.java)
                                startActivity(intent)
                            }else{
                                MaterialAlertDialogBuilder(this@LoginActivity)
                                    .setTitle("Confirmación de Registro")
                                    .setMessage("No cuenta con membresía activa")
                                    .setPositiveButton("Ok"){dialog, which ->
                                    }.show()
                            }
                        } else {
                            btnIniciarSesion.isVisible = true
                            pbLogin.isVisible = false
                            MaterialAlertDialogBuilder(this@LoginActivity)
                                .setTitle("Confirmación de Registro")
                                .setMessage("Error de Usuario / Contraseña")
                                .setPositiveButton("Ok"){dialog, which ->
                                }.show()
                        }
                    }
                }
            )
    }
}