package com.example.gymapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gymapp.R
import com.example.gymapp.model.ClasesGrupales
import kotlin.math.absoluteValue

class MasterClassAdapter(var lstMasterClasses: List<ClasesGrupales>) :
    RecyclerView.Adapter<MasterClassAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.master_class_card_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lstMasterClasses[position]
        holder.class_name.text = item.Actividad?.Nombre.toString()
        holder.instructor_name.text = item.Instructor?.Nombres.toString().plus(" ").plus(item.Instructor?.Apellidos.toString())
        holder.scheduled_time.text = item.Actividad?.DuracionMinutos.toString().plus(" Minutos")
        //holder.image_class.setImageResource(master_classes[i])
    }

    override fun getItemCount(): Int {
        return lstMasterClasses.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image_class: ImageView
        val class_name: TextView
        val instructor_name: TextView
        val scheduled_time: TextView

        init {
            image_class = itemView.findViewById(R.id.imgClase)
            class_name = itemView.findViewById(R.id.txtNombreActividad)
            instructor_name = itemView.findViewById(R.id.txtNombreInstructor)
            scheduled_time = itemView.findViewById(R.id.txtHorarioActividad)
        }

    }
}