package com.example.gymapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gymapp.R
import com.example.gymapp.model.ClasesGrupales
import com.example.gymapp.model.Inscripcion
import kotlin.math.absoluteValue

class InscripcionAdapter(var lstInscripciones: List<Inscripcion>) :
    RecyclerView.Adapter<InscripcionAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.master_class_card_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lstInscripciones[position]
        holder.class_name.text = item.ClaseGrupal?.Actividad?.Nombre.toString()
        holder.instructor_name.text = item.ClaseGrupal?.Instructor?.Nombres.toString().plus(" ")
            .plus(item.ClaseGrupal?.Instructor?.Apellidos.toString())
        holder.scheduled_time.text =
            item.ClaseGrupal?.Actividad?.DuracionMinutos.toString().plus(" Minutos")
        Glide.with(holder.itemView.context).load(item.ClaseGrupal?.Actividad?.Imagen).into(holder.image_class)
    }

    override fun getItemCount(): Int {
        return lstInscripciones.size
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