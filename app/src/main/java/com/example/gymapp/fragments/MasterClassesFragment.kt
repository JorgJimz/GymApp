package com.example.gymapp.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymapp.MainActivity
import com.example.gymapp.R
import com.example.gymapp.adapter.MasterClassAdapter
import com.example.gymapp.client.WebServiceClient
import com.example.gymapp.model.ClasesGrupales
import com.example.gymapp.model.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MasterClassesFragment : Fragment() {
    lateinit var rvClasesGrupales: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_master_classes, container, false)
        rvClasesGrupales = view.findViewById(R.id.rvMasterClass)
        rvClasesGrupales.layoutManager = LinearLayoutManager(requireContext())
        ObtenerClases()
        return view
    }

    fun ObtenerClases() {
        var request =
            WebServiceClient.retrofitService.ObtenerClases().enqueue(
                object : Callback<List<ClasesGrupales>> {
                    override fun onFailure(call: Call<List<ClasesGrupales>>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<List<ClasesGrupales>>,
                        response: Response<List<ClasesGrupales>>
                    ) {
                        var adapter = MasterClassAdapter(response.body()!!)
                        rvClasesGrupales.adapter = adapter
                        adapter.setOnItemClickListener(object :
                            MasterClassAdapter.OnItemClickListener {
                            override fun onItemClickListener(position: Int) {
                                Toast.makeText(context, "Clickeaste $position", Toast.LENGTH_LONG)
                                var selectedObject = adapter.lstMasterClasses[position]
                                var fMc: InscripcionConfirmFragment = InscripcionConfirmFragment()
                                var b: Bundle = Bundle()
                                b.putSerializable("mc", selectedObject);
                                fMc.arguments = b
                                fragmentManager?.beginTransaction()?.apply {
                                    replace(R.id.fragmentMasterClass, fMc)
                                    commit()
                                }
                            }
                        })
                    }
                }
            )
    }
}