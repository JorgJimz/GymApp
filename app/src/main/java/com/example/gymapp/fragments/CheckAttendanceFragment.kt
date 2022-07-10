package com.example.gymapp.fragments

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.gymapp.R
import com.example.gymapp.client.WebServiceClient
import com.example.gymapp.model.Asistencia
import com.example.gymapp.model.Inscripcion
import com.example.gymapp.model.Usuario
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckAttendanceFragment : Fragment() {
    lateinit var lsView : ListView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_check_attendance, container, false)
        lsView = view.findViewById<ListView>(R.id.lsAsistencias)
        ObtenerAsistenciasPorUsuario()
        return view
    }

    private fun ObtenerAsistenciasPorUsuario() {
        val settings = PreferenceManager.getDefaultSharedPreferences(
            context
        )
        var request =
            Gson().fromJson(settings.getString("logged", null), Usuario::class.java).Id?.let {
                WebServiceClient.retrofitService.BuscarAsistenciasPorUsuario(
                    it
                ).enqueue(
                    object : Callback<List<Asistencia>> {
                        override fun onFailure(call: Call<List<Asistencia>>, t: Throwable) {
                            Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(
                            call: Call<List<Asistencia>>,
                            response: Response<List<Asistencia>>
                        ) {
                            val arrayAdapter =
                                context?.let { it1 ->
                                    ArrayAdapter(
                                        it1,
                                        android.R.layout.simple_list_item_1,
                                        response.body()!!
                                    )
                                }
                            lsView.adapter = arrayAdapter
                        }
                    }
                )
            }
    }



}