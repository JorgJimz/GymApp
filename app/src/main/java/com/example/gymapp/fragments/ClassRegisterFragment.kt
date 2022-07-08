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
import com.example.gymapp.TimePickerFragment
import com.example.gymapp.client.WebServiceClient
import com.example.gymapp.model.Actividad
import com.example.gymapp.model.ClasesGrupales
import com.example.gymapp.model.Usuario
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.annotations.SerializedName
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class ClassRegisterFragment : Fragment(), AdapterView.OnItemClickListener {
    private lateinit var edtFechaClaseGrupal: EditText
    private lateinit var edtHoraClaseGrupal: EditText
    private lateinit var acActividad: AutoCompleteTextView
    private lateinit var acInstructor: AutoCompleteTextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_class_register, container, false)
        val lblActividadId : TextView = view.findViewById(R.id.lblActividadId)
        val lblInstructorId : TextView = view.findViewById(R.id.lblInstructorId)
        acActividad = view.findViewById(R.id.autoCompleteActividad)
        acInstructor = view.findViewById(R.id.autoCompleteInstructor)
        ObtenerActividades()
        ObtenerInstructores()

        acActividad.onItemClickListener =
            AdapterView.OnItemClickListener{ parent, view, position, id ->
                val a = parent?.getItemAtPosition(position) as Actividad
                lblActividadId.text = a.Id.toString()
            }
        acInstructor.onItemClickListener =
            AdapterView.OnItemClickListener{ parent, view, position, id ->
                val i = parent?.getItemAtPosition(position) as Usuario
                lblInstructorId.text = i.Id.toString()
            }


        edtFechaClaseGrupal = view.findViewById(R.id.dpFechaClaseGrupal)
        edtFechaClaseGrupal.setOnClickListener {
            showDatePickerDialog()
        }
        edtHoraClaseGrupal = view.findViewById(R.id.dpHoraClaseGrupal)
        edtHoraClaseGrupal.setOnClickListener {
            showTimePickerDialog()
        }

        val btnRegClaGruGrabar : Button = view.findViewById(R.id.btnRegClaGruGrabar)
        btnRegClaGruGrabar.setOnClickListener {
            val clase = ClasesGrupales(
                Id = 0,
                FechaHora = "${edtFechaClaseGrupal.text}T${edtHoraClaseGrupal.text}",
                InstructorId = lblInstructorId.text.toString().toInt(),
                ActividadId = lblActividadId.text.toString().toInt(),
                Status = 1
            )
            RegistrarClaseGrupal(clase)
        }

        return view
    }

    private fun RegistrarClaseGrupal(claseGrupal: ClasesGrupales) {
        var request =
            WebServiceClient.retrofitService.RegistrarClaseGrupal(
                claseGrupal
            ).enqueue(
                object : Callback<ClasesGrupales> {
                    override fun onFailure(call: Call<ClasesGrupales>, t: Throwable) {
                        Toast.makeText( context,t.message, Toast.LENGTH_LONG).show()
                    }
                    override fun onResponse(
                        call: Call<ClasesGrupales>,
                        response: Response<ClasesGrupales>
                    ) {
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Confirmación de Registro")
                            .setMessage("¡Clase grupal registrada exitosamente!")
                            .setPositiveButton("Ok"){dialog, which ->
                            }.show()
                    }
                }
            )
    }

    private fun ObtenerInstructores() {
        var request =
            WebServiceClient.retrofitService.ObtenerInstructores().enqueue(
                object : Callback<List<Usuario>> {
                    override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<List<Usuario>>,
                        response: Response<List<Usuario>>
                    ) {
                        val instructors: List<Usuario> = response.body()!!
                        val adapter = ArrayAdapter(
                            context as Context,
                            R.layout.list_item,
                            instructors
                        )
                        acInstructor.setAdapter(adapter)
                    }
                }
            )
    }

    private fun ObtenerActividades() {
        var request =
            WebServiceClient.retrofitService.ObtenerActividades().enqueue(
                object : Callback<List<Actividad>> {
                    override fun onFailure(call: Call<List<Actividad>>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<List<Actividad>>,
                        response: Response<List<Actividad>>
                    ) {
                        val activities: List<Actividad> = response.body()!!
                        val adapter = ArrayAdapter(
                            context as Context,
                            R.layout.list_item,
                            activities
                        )
                        acActividad.setAdapter(adapter)
                    }
                }
            )
    }

    private fun showTimePickerDialog() {
        val timepicker = TimePickerFragment { onTimeSelected(it) }
        fragmentManager?.let { manager ->
            timepicker.show(manager, "timePicker")
        }
    }

    private fun onTimeSelected(it: String) {
        edtHoraClaseGrupal.setText(it)
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
        edtFechaClaseGrupal.setText("${sYear}-${sMonth}-${sDayOfMonth}")
    }

    private fun twoDigits(n: Int): String {
        return if (n <= 9) "0$n" else n.toString()
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("Not yet implemented")
    }

}