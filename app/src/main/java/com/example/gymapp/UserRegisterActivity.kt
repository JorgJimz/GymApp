package com.example.gymapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.gymapp.client.WebServiceClient
import com.example.gymapp.model.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class UserRegisterActivity : AppCompatActivity() {
    lateinit var edtFechaNacimiento : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_register)

        val edtNombre : EditText = findViewById(R.id.edtNombre)
        val edtApellido : EditText = findViewById(R.id.edtApellido)
        val edtEmail : EditText = findViewById(R.id.edtEmail)
        val edtDocumento : EditText = findViewById(R.id.edtDocumento)
        val edtTelefono1 : EditText = findViewById(R.id.edtTelefono1)
        val edtTelefono2 : EditText = findViewById(R.id.edtTelefono2)
        edtFechaNacimiento = findViewById(R.id.dpFechaNacimiento)
        edtFechaNacimiento.setOnClickListener {
            showDatePickerDialog()
        }

        val btnRegistrar: Button = findViewById(R.id.BtnRegistrar)
        btnRegistrar.setOnClickListener {
            var sentUser = Usuario(
                Id = 0,
                Nombres = edtNombre.text.toString(),
                TipoDocumento =  1,
                NumeroDocumento =  edtDocumento.text.toString(),
                Apellidos =  edtApellido.text.toString(),
                FechaNacimiento =  "1991-12-05 00:00:00.000",
                Email =  edtEmail.text.toString(),
                Telefono1 =  edtTelefono1.text.toString(),
                Telefono2 =  edtTelefono2.text.toString(),
                Perfil =  1,
                Contrasena =  "123",
                Status = 1
            )
            var request =
                WebServiceClient.retrofitService.RegistrarUsuario(
                    sentUser
                ).enqueue(
                    object : Callback<Usuario> {
                        override fun onFailure(call: Call<Usuario>, t: Throwable) {
                            Toast.makeText( applicationContext,t.message, Toast.LENGTH_LONG).show()
                        }
                        override fun onResponse(
                            call: Call<Usuario>,
                            response: Response<Usuario>
                        ) {
                            val addedUser = response.body()
                            Toast.makeText(applicationContext, addedUser.toString(), Toast.LENGTH_LONG)
                        }
                    }
                );
        }
    }

    private fun showDatePickerDialog() {
        val datepicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datepicker.show(supportFragmentManager, "datePicker")
    }

    fun onDateSelected(day:Int, month:Int, year:Int){
        edtFechaNacimiento.setText("${year}-${month}-${day}")
    }
}