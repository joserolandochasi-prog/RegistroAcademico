package com.example.registroacademico.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registroacademico.databinding.ItemUsuarioBinding
import com.example.registroacademico.model.entities.Usuario

class UsuarioAdapter(
    private var usuarios: List<Usuario>,
    private val onAccionClick: (Usuario, String) -> Unit
) : RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {

    inner class UsuarioViewHolder(val binding: ItemUsuarioBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val binding = ItemUsuarioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsuarioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = usuarios[position]
        holder.binding.tvNombreUsuario.text = usuario.nombre
        holder.binding.tvEmailUsuario.text = usuario.email
        holder.binding.tvRolUsuario.text = "Rol actual: ${usuario.rol}"

        // Mostrar botones solo si el rol es "pendiente" o si queremos permitir re-asignar
        if (usuario.rol == "pendiente") {
            holder.binding.layoutAcciones.visibility = View.VISIBLE
            
            holder.binding.btnAsignarEstudiante.setOnClickListener { 
                onAccionClick(usuario, "estudiante") 
            }
            
            holder.binding.btnAsignarDocente.setOnClickListener { 
                onAccionClick(usuario, "docente") 
            }
        } else {
            // Si ya tiene un rol, ocultamos los botones o podrías dejarlos para cambiar de nuevo
            holder.binding.layoutAcciones.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = usuarios.size

    fun actualizarLista(nuevaLista: List<Usuario>) {
        usuarios = nuevaLista
        notifyDataSetChanged()
    }
}