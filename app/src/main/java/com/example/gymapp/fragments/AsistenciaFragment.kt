package com.example.gymapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gymapp.LoginActivity
import com.example.gymapp.R
import com.example.gymapp.client.WebServiceClient
import com.example.gymapp.model.Asistencia
import com.example.gymapp.model.Membresia
import com.example.gymapp.model.Usuario
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

class AsistenciaFragment : Fragment() {
    private lateinit var edtSocio: EditText
    private lateinit var txtUsuarioId: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_asistencia, container, false)
        edtSocio = view.findViewById(R.id.EdtSocio)
        txtUsuarioId = view.findViewById(R.id.TxtUsuarioId)
        val edtDocumento: EditText = view.findViewById(R.id.EdtDocumento)
        val btnAsisBuscar: Button = view.findViewById(R.id.BtnAsisBuscar)
        btnAsisBuscar.setOnClickListener {
            ObtenerUsuario(edtDocumento.text.toString())
        }
        val btnAsisGrabar: Button = view.findViewById(R.id.BtnAsisGrabar)
        btnAsisGrabar.setOnClickListener {
            var sentAttendance = Asistencia(
                Id = 0,
                Fecha = LocalDateTime.now().toString(),
                UsuarioId = txtUsuarioId.text.toString().toInt()
            )
            RegistrarAsistencia(sentAttendance)
        }
        return view
    }

    private fun RegistrarAsistencia(asistencia: Asistencia) {
        var request =
            WebServiceClient.retrofitService.RegistrarAsistencia(
                asistencia
            ).enqueue(
                object : Callback<Asistencia> {
                    override fun onFailure(call: Call<Asistencia>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                    }
                    override fun onResponse(
                        call: Call<Asistencia>,
                        response: Response<Asistencia>
                    ) {
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Confirmación de Registro")
                            .setMessage("Asistencia Registrada")
                            .setPositiveButton("Ok") { dialog, which ->
                            }.show()
                    }
                }
            );
    }

    private fun ObtenerUsuario(numero: String) {
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
}