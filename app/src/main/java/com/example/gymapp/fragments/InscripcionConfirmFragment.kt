package com.example.gymapp.fragments

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymapp.R
import com.example.gymapp.adapter.MasterClassAdapter
import com.example.gymapp.client.WebServiceClient
import com.example.gymapp.model.ClasesGrupales
import com.example.gymapp.model.Inscripcion
import com.example.gymapp.model.Usuario
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InscripcionConfirmFragment : Fragment() {
    private lateinit var clase_grupal_id: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_inscripcion_confirm, container, false)
        val actividad_label : TextView = view.findViewById(R.id.lblActividad)
        val fechahora_label : TextView = view.findViewById(R.id.lblFechaHora)
        val instructor_label : TextView = view.findViewById(R.id.lblInstructor)
        val duracion_label : TextView = view.findViewById(R.id.lblDuracion)
        clase_grupal_id = view.findViewById(R.id.lblClaseGrupalId)
        val btnInscribirme: Button = view.findViewById(R.id.BtnInscripcion)
        btnInscribirme.setOnClickListener{
            GenerarInscripcion()
        }
        val btnVolver: Button = view.findViewById(R.id.BtmVolverMC)
        btnVolver.setOnClickListener{
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragmentConfirmarInscripcion,MasterClassesFragment())
                commit()
            }
        }

        val clase : ClasesGrupales = arguments?.getSerializable("mc") as ClasesGrupales;
        clase_grupal_id.text = clase?.Id.toString()
        actividad_label.text = clase.Actividad?.Nombre
        fechahora_label.text = clase.FechaHora
        instructor_label.text = clase.Instructor?.Nombres.plus(" ").plus(clase.Instructor?.Apellidos)
        duracion_label.text = clase.Actividad?.DuracionMinutos.toString()

        return view
    }

    fun GenerarInscripcion(){
        val settings = PreferenceManager.getDefaultSharedPreferences(
            context
        )
        var request =
            WebServiceClient.retrofitService.GenerarInscripciones(
                Inscripcion(0, clase_grupal_id.text.toString().toInt(), null, Gson().fromJson(settings.getString("logged", null), Usuario::class.java).Id)
            ).enqueue(
                object : Callback<Inscripcion> {
                    override fun onFailure(call: Call<Inscripcion>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                    }
                    override fun onResponse(
                        call: Call<Inscripcion>,
                        response: Response<Inscripcion>
                    ) {
                        Toast.makeText(context, "Registrado" ,Toast.LENGTH_LONG).show()
                    }
                }
            )
    }

}