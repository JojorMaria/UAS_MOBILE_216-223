package com.example.eventapp

import android.app.AlertDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventapp.adapter.EventAdapter
import com.example.eventapp.databinding.ActivityMainBinding
import com.example.eventapp.model.Event
import com.example.eventapp.model.EventRequest
import com.example.eventapp.network.ApiClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val eventsList = mutableListOf<Event>()
    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = EventAdapter(eventsList)
        binding.rvEvents.layoutManager = LinearLayoutManager(this)
        binding.rvEvents.adapter = adapter

        loadEvents()

        binding.btnCreateEvent.setOnClickListener { showCreateEventDialog() }
        binding.btnGetEventById.setOnClickListener { showGetEventByIdDialog() }
        binding.btnGetEventByDate.setOnClickListener { showGetEventByDateDialog() }
    }

    private fun loadEvents() {
        lifecycleScope.launch {
            try {
                val response = ApiClient.instance.getEvents()
                eventsList.clear()
                response.data?.let { eventsList.addAll(it) }
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MainActivity, "Failed to load events", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // ---------------- CREATE EVENT ----------------
    private fun showCreateEventDialog() {
        val layout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }

        val inputTitle = EditText(this).apply { hint = "Judul Event" }
        val inputDate = EditText(this).apply { hint = "Tanggal (YYYY-MM-DD)" }
        val inputTime = EditText(this).apply { hint = "Waktu (HH:MM)" }
        val inputLocation = EditText(this).apply { hint = "Lokasi Event" }
        val inputDesc = EditText(this).apply { hint = "Deskripsi Event" }
        val inputCapacity = EditText(this).apply { hint = "Kapasitas (angka)" }
        val inputStatus = EditText(this).apply { hint = "Status (upcoming/ongoing/completed/cancelled)" }

        layout.addView(inputTitle)
        layout.addView(inputDate)
        layout.addView(inputTime)
        layout.addView(inputLocation)
        layout.addView(inputDesc)
        layout.addView(inputCapacity)
        layout.addView(inputStatus)

        AlertDialog.Builder(this)
            .setTitle("Create Event")
            .setView(layout)
            .setPositiveButton("Submit") { _, _ ->
                val title = inputTitle.text.toString().trim()
                val date = inputDate.text.toString().trim()
                val time = inputTime.text.toString().trim()
                val location = inputLocation.text.toString().trim()
                val description = inputDesc.text.toString().trim()
                val capacity = inputCapacity.text.toString().toIntOrNull() ?: 0
                val status = inputStatus.text.toString().trim().lowercase()

                if (title.isEmpty() || date.isEmpty() || time.isEmpty() ||
                    location.isEmpty() || description.isEmpty() ||
                    capacity <= 0 || status !in listOf("upcoming","ongoing","completed","cancelled")
                ) {
                    Toast.makeText(this, "Isi semua field dengan benar!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                createEvent(title, date, time, location, description, capacity, status)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun createEvent(title: String, date: String, time: String, location: String,
                            description: String, capacity: Int, status: String) {
        lifecycleScope.launch {
            try {
                val request = EventRequest(
                    title = title,
                    date = date,
                    time = if (time.length == 5) "$time:00" else time,
                    location = location,
                    description = description,
                    capacity = capacity,
                    status = status
                )
                val response = ApiClient.instance.createEvent(request)

                // Tambahkan response ke RecyclerView
                eventsList.clear()
                response.data?.let { eventsList.addAll(it) }
                response.event?.let { eventsList.add(it) }
                adapter.notifyDataSetChanged()

                Toast.makeText(this@MainActivity, "Event berhasil dibuat!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MainActivity, "Gagal membuat event: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    // ---------------- GET EVENT BY ID ----------------
    private fun showGetEventByIdDialog() {
        val inputId = EditText(this).apply { hint = "Masukkan ID Event" }
        AlertDialog.Builder(this)
            .setTitle("Get Event by ID")
            .setView(inputId)
            .setPositiveButton("Cari") { _, _ ->
                val id = inputId.text.toString().trim()
                if (id.isEmpty()) {
                    Toast.makeText(this, "ID tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                getEventById(id)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun getEventById(id: String) {
        lifecycleScope.launch {
            try {
                val response = ApiClient.instance.getEventById(id = id)
                eventsList.clear()
                response.data?.let { eventsList.addAll(it) }
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MainActivity, "Event tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // ---------------- GET EVENT BY DATE ----------------
    private fun showGetEventByDateDialog() {
        val inputDate = EditText(this).apply { hint = "Masukkan tanggal (YYYY-MM-DD)" }
        AlertDialog.Builder(this)
            .setTitle("Get Event by Date")
            .setView(inputDate)
            .setPositiveButton("Cari") { _, _ ->
                val date = inputDate.text.toString().trim()
                if (date.isEmpty()) {
                    Toast.makeText(this, "Tanggal tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                getEventByDate(date)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun getEventByDate(date: String) {
        lifecycleScope.launch {
            try {
                val response = ApiClient.instance.getEventByDate(date = date)
                eventsList.clear()
                response.data?.let { eventsList.addAll(it) }
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MainActivity, "Tidak ada event di tanggal ini", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
