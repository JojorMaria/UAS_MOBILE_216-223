package com.example.eventapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eventapp.databinding.ItemEventBinding
import com.example.eventapp.model.Event
import android.widget.Toast

class EventAdapter(private val list: List<Event>) :
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = list[position]

        holder.binding.tvTitle.text = event.title
        holder.binding.tvDate.text = "${event.date} ${event.time}"
        holder.binding.tvLocation.text = event.location
        holder.binding.tvStatus.text = event.status

        when (event.status.lowercase()) {
            "completed" -> holder.binding.tvStatus.setTextColor(Color.GREEN)
            "cancelled" -> holder.binding.tvStatus.setTextColor(Color.RED)
            "ongoing" -> holder.binding.tvStatus.setTextColor(Color.BLUE)
            "upcoming" -> holder.binding.tvStatus.setTextColor(Color.MAGENTA)
        }

        holder.binding.btnDetail.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Event: ${event.title}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = list.size

}

