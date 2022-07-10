package com.example.gymapp.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.gymapp.DatePickerFragment
import com.example.gymapp.LoginActivity
import com.example.gymapp.R
import com.example.gymapp.client.WebServiceClient
import com.example.gymapp.model.Perfil
import com.example.gymapp.model.Usuario
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminRegisterFragment : Fragment() {
    lateinit var edtFechaNacimiento: EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_admin_register, container, false)
        val listProfile =
            listOf(Perfil(1, "Usuario"), Perfil(2, "Admin"), Perfil(3, "Instructor"))
        val edtNombre: EditText = view.findViewById(R.id.edtNombre_Adm)
        val edtApellido: EditText = view.findViewById(R.id.edtApellido_Adm)
        val edtEmail: EditText = view.findViewById(R.id.edtEmail_Adm)
        val edtDocumento: EditText = view.findViewById(R.id.EdtDocumento_Adm)
        val edtTelefono1: EditText = view.findViewById(R.id.edtTelefono1_Adm)
        val edtTelefono2: EditText = view.findViewById(R.id.edtTelefono2_Adm)
        val edtContrasena: EditText = view.findViewById(R.id.edtPwd_Adm)
        val lblPerfil: TextView = view.findViewById(R.id.lblPerfil)
        val acPerfil: AutoCompleteTextView = view.findViewById(R.id.autoCompletePerfil)
        val adapter = ArrayAdapter(
            context as Context,
            R.layout.list_item,
            listProfile
        )
        acPerfil.setAdapter(adapter)
        acPerfil.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val i = parent?.getItemAtPosition(position) as Perfil
                lblPerfil.text = i.Id.toString()
            }

        edtFechaNacimiento = view.findViewById(R.id.dpFechaNacimiento_Adm)
        edtFechaNacimiento.setOnClickListener {
            showDatePickerDialog()
        }

        val btnRegistrar: Button = view.findViewById(R.id.BtnRegistrar_Adm)
        btnRegistrar.setOnClickListener {
            var sentUser = Usuario(
                Id = 0,
                Nombres = edtNombre.text.toString(),
                TipoDocumento = 1,
                NumeroDocumento = edtDocumento.text.toString(),
                Apellidos = edtApellido.text.toString(),
                FechaNacimiento = edtFechaNacimiento.text.toString(),
                Email = edtEmail.text.toString(),
                Telefono1 = edtTelefono1.text.toString(),
                Telefono2 = edtTelefono2.text.toString(),
                Perfil = lblPerfil.text.toString().toInt(),
                Contrasena = edtContrasena.text.toString(),
                Status = 1,
                Menu = configureMenu(lblPerfil.text.toString().toInt())
            )
            var request =
                WebServiceClient.retrofitService.RegistrarUsuario(
                    sentUser
                ).enqueue(
                    object : Callback<Usuario> {
                        override fun onFailure(call: Call<Usuario>, t: Throwable) {
                            Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(
                            call: Call<Usuario>,
                            response: Response<Usuario>
                        ) {
                            MaterialAlertDialogBuilder(requireContext())
                                .setTitle("Confirmación de Registro")
                                .setMessage("¡Usuario registrado exitosamente!")
                                .setPositiveButton("Ok") { dialog, which ->
                                }.show()
                        }
                    }
                );
        }

        return view
    }

    private fun showDatePickerDialog() {
        val datepicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        fragmentManager?.let { manager ->
            datepicker.show(manager, "datePicker")
        }
    }

    fun onDateSelected(day: Int, month: Int, year: Int) {
        var sYear: String = twoDigits(year)
        var sMonth: String = twoDigits(month)
        var sDayOfMonth: String = twoDigits(day)
        edtFechaNacimiento.setText("${sYear}-${sMonth}-${sDayOfMonth}")
    }

    private fun twoDigits(n: Int): String {
        return if (n <= 9) "0$n" else n.toString()
    }

    private fun configureMenu(perfil: Int): String {
        var menu: String = ""
        when (perfil) {
            1 -> menu = "0,2,4,6,8"
            2 -> menu = "0,1,3,5,7,8"
            else -> {
                menu = "0"
            }
        }
        return menu
    }
}