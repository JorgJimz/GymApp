package com.example.gymapp.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymapp.MainActivity
import com.example.gymapp.R
import com.example.gymapp.adapter.InscripcionAdapter
import com.example.gymapp.adapter.MasterClassAdapter
import com.example.gymapp.client.WebServiceClient
import com.example.gymapp.model.ClasesGrupales
import com.example.gymapp.model.Inscripcion
import com.example.gymapp.model.Usuario
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    lateinit var rvInscripciones: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        rvInscripciones = view.findViewById(R.id.rvInscripciones)
        rvInscripciones.layoutManager = LinearLayoutManager(requireContext())
        ObtenerInscripcionesPorUsuario()
        return view
    }

    fun ObtenerInscripcionesPorUsuario() {
        val settings = PreferenceManager.getDefaultSharedPreferences(
            context
        )
        val userLogged: Usuario =
            Gson().fromJson(settings.getString("logged", null), Usuario::class.java)

        var request =
            WebServiceClient.retrofitService.ObtenerInscripciones(
                userLogged
            ).enqueue(
                object : Callback<List<Inscripcion>> {
                    override fun onFailure(call: Call<List<Inscripcion>>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                    }
                    override fun onResponse(
                        call: Call<List<Inscripcion>>,
                        response: Response<List<Inscripcion>>
                    ) {
                        rvInscripciones.adapter = InscripcionAdapter(response.body()!!)
                    }
                }
            )
    }
}