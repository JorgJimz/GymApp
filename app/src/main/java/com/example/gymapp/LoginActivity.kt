package com.example.gymapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gymapp.client.WebServiceClient
import com.example.gymapp.model.Usuario
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val edtUsuario: EditText = findViewById(R.id.edtUsuario)
        val edtContrasena: EditText = findViewById(R.id.edtContrasena)
        val btnRegistrar: Button = findViewById(R.id.BtnRegistrar)
        btnRegistrar.setOnClickListener{
            val intent = Intent(applicationContext, UserRegisterActivity::class.java)
            startActivity(intent)
        }
        val btnIniciarSesion: Button = findViewById(R.id.BtnLogin)
        btnIniciarSesion.setOnClickListener{
            var sentUser = Usuario(
                NumeroDocumento =  edtUsuario.text.toString(),
                Contrasena =  edtContrasena.text.toString()
            )
            Login(sentUser)
        }
    }

    fun Login(sentUser: Usuario){
        var request =
            WebServiceClient.retrofitService.IniciarSesion(
                "46910224","123"
            ).enqueue(
                object : Callback<Usuario> {
                    override fun onFailure(call: Call<Usuario>, t: Throwable) {
                        Toast.makeText( applicationContext,t.message, Toast.LENGTH_LONG).show()
                    }
                    override fun onResponse(
                        call: Call<Usuario>,
                        response: Response<Usuario>
                    ) {
                        val logged  = response.body()
                        if(logged != null){
                            val settings : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                            val editor: SharedPreferences.Editor = settings.edit()
                            logged.Id?.toInt()?.let { editor.putInt("id", it) }
                            editor.putString("logged", Gson().toJson(logged))
                            editor.commit()
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(applicationContext, "Usuario y/o Contraseña incorrectos.", Toast.LENGTH_LONG)
                        }
                    }
                }
            )
    }
}