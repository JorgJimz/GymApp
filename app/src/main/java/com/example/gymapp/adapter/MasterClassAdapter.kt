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
import kotlin.math.absoluteValue

public class MasterClassAdapter(var lstMasterClasses: List<ClasesGrupales>) :
    RecyclerView.Adapter<MasterClassAdapter.ViewHolder>() {

    private lateinit var mListener : OnItemClickListener

    public interface OnItemClickListener{
        fun onItemClickListener(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.master_class_card_view, parent, false)
        return ViewHolder(v, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lstMasterClasses[position]
        holder.class_name.text = item.Actividad?.Nombre.toString()
        holder.instructor_name.text = item.Instructor?.Nombres.toString().plus(" ").plus(item.Instructor?.Apellidos.toString())
        holder.scheduled_time.text = item.Actividad?.DuracionMinutos.toString().plus(" Minutos")
        Glide.with(holder.itemView.context).load(item.Actividad?.Imagen).into(holder.image_class)
    }

    override fun getItemCount(): Int {
        return lstMasterClasses.size
    }

    inner class ViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val image_class: ImageView
        val class_name: TextView
        val instructor_name: TextView
        val scheduled_time: TextView

        init {
            image_class = itemView.findViewById(R.id.imgClase)
            class_name = itemView.findViewById(R.id.txtNombreActividad)
            instructor_name = itemView.findViewById(R.id.txtNombreInstructor)
            scheduled_time = itemView.findViewById(R.id.txtHorarioActividad)
            itemView.setOnClickListener{
                listener.onItemClickListener(adapterPosition)
            }
        }

    }
}