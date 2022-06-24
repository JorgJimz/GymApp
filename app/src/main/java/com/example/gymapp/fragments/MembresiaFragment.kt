package com.example.gymapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.gymapp.DatePickerFragment
import com.example.gymapp.LoginActivity
import com.example.gymapp.R
import com.example.gymapp.client.WebServiceClient
import com.example.gymapp.model.Membresia
import com.example.gymapp.model.Usuario
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime

class MembresiaFragment : Fragment() {
    private lateinit var edtSocio: EditText
    private lateinit var edtInicioMembresia: EditText
    private lateinit var edtFinMembresia: EditText
    private lateinit var rgPlanes: RadioGroup
    private lateinit var radioButton: RadioButton
    private lateinit var txtUsuarioId: TextView
    private lateinit var plantype: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_membresia, container, false)
        edtSocio = view.findViewById(R.id.EdtSocio)
        val edtDocumento: EditText = view.findViewById(R.id.EdtDocumento)
        edtInicioMembresia = view.findViewById(R.id.EdtInicioMembresia)
        edtFinMembresia = view.findViewById(R.id.EdtFinMembresia)
        txtUsuarioId = view.findViewById(R.id.TxtUsuarioId)
        rgPlanes = view.findViewById(R.id.RgPlanes)
        rgPlanes.setOnCheckedChangeListener { radioGroup, i ->
            val selectedOption: Int = radioGroup!!.checkedRadioButtonId
            radioButton = view.findViewById(selectedOption)
            Toast.makeText(context, radioButton.text, Toast.LENGTH_SHORT).show()
        }
        edtInicioMembresia.setOnClickListener {
            showDatePickerDialog()
        }
        val btnRegMemBuscar: Button = view.findViewById(R.id.BtnRegMemBuscar)
        btnRegMemBuscar.setOnClickListener {
            ObtenerUsuario(edtDocumento.text.toString())
        }
        val btnRegMemGrabar: Button = view.findViewById(R.id.BtnRegMemGrabar)
        btnRegMemGrabar.setOnClickListener {
            var sentMembership = Membresia(
                Id = 0,
                TipoMembresia = radioButton.text.toString(),
                Fecha = LocalDateTime.now().toString(),
                FechaInicio = LocalDateTime.now().toString(),
                FechaFin = LocalDateTime.now().toString(),
                UsuarioId = txtUsuarioId.text.toString().toInt(),
                Status = 1
            )
            RegistrarMembresia(sentMembership)
        }
        return view
    }

    fun ObtenerUsuario(numero: String) {
        var request =
            WebServiceClient.retrofitService.BuscarUsuario(
                numero
            ).enqueue(
                object : Callback<Usuario> {
                    override fun onFailure(call: Call<Usuario>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<Usuario>,
                        response: Response<Usuario>
                    ) {
                        val userFound: Usuario = response.body()!!
                        txtUsuarioId.setText(userFound.Id.toString())
                        edtSocio.setText(userFound.Nombres.plus(" ").plus(userFound.Apellidos))
                    }
                }
            )
    }

    fun RegistrarMembresia(membresia: Membresia) {
        var request =
            WebServiceClient.retrofitService.RegistrarMembresia(
                membresia
            ).enqueue(
                object : Callback<Membresia> {
                    override fun onFailure(call: Call<Membresia>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<Membresia>,
                        response: Response<Membresia>
                    ) {
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Confirmación de Registro")
                            .setMessage("Membresía Adquirida")
                            .setPositiveButton("Ok") { dialog, which ->
                                val intent = Intent(context, LoginActivity::class.java)
                                startActivity(intent)
                            }.show()
                    }
                }
            )
    }

    private fun showDatePickerDialog() {
        val datepicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        fragmentManager?.let { manager ->
            datepicker.show(manager, "datePicker")
        }
    }

    fun onDateSelected(day: Int, month: Int, year: Int) {
        val planSelected: Int
        when (radioButton.text) {
            "Mensual" -> planSelected = 1
            "Trimestral" -> planSelected = 3
            "Semestral" -> planSelected = 6
            else -> {
                planSelected = 12
            }
        }
        var sYear: String = twoDigits(year)
        var sMonth: String = twoDigits(month)
        var sDayOfMonth: String = twoDigits(day)
        edtInicioMembresia.setText("${sYear}-${sMonth}-${sDayOfMonth}")
        val start: LocalDate = LocalDate.of(year, month, day)
        edtFinMembresia.setText(start.plusMonths(planSelected.toLong()).toString())
    }

    private fun twoDigits(n: Int): String {
        return if (n <= 9) "0$n" else n.toString()
    }
}