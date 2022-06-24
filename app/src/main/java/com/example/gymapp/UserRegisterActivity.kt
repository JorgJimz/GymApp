package com.example.gymapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.gymapp.client.WebServiceClient
import com.example.gymapp.model.Usuario
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRegisterActivity : AppCompatActivity() {
    lateinit var edtFechaNacimiento : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_register)
        val edtNombre : EditText = findViewById(R.id.edtNombre)
        val edtApellido : EditText = findViewById(R.id.edtApellido)
        val edtEmail : EditText = findViewById(R.id.edtEmail)
        val edtDocumento : EditText = findViewById(R.id.EdtDocumento)
        val edtTelefono1 : EditText = findViewById(R.id.edtTelefono1)
        val edtTelefono2 : EditText = findViewById(R.id.edtTelefono2)
        val edtContrasena : EditText = findViewById(R.id.edtPwd)
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
                FechaNacimiento =  edtFechaNacimiento.text.toString(),
                Email =  edtEmail.text.toString(),
                Telefono1 =  edtTelefono1.text.toString(),
                Telefono2 =  edtTelefono2.text.toString(),
                Perfil =  1,
                Contrasena =  edtContrasena.text.toString(),
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
                            MaterialAlertDialogBuilder(this@UserRegisterActivity)
                                .setTitle("Confirmación de Registro")
                                .setMessage("¡Usuario registrado exitosamente!")
                                .setPositiveButton("Ok"){dialog, which ->
                                    val intent = Intent(applicationContext, LoginActivity::class.java)
                                    startActivity(intent)
                                }.show()
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
        var sYear: String = twoDigits(year)
        var sMonth: String = twoDigits(month)
        var sDayOfMonth: String = twoDigits(day)
        edtFechaNacimiento.setText("${sYear}-${sMonth}-${sDayOfMonth}")
    }

    private fun twoDigits(n: Int): String {
        return if (n <= 9) "0$n" else n.toString()
    }
}