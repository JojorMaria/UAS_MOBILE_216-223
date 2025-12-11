package com.example.eventapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.eventapp.databinding.ActivityCreateEventBinding
import com.example.eventapp.network.ApiClient
import kotlinx.coroutines.launch

class CreateEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreateEvent.setOnClickListener {
            submitCreateEvent()
        }
    }

    private fun submitCreateEvent() {
        // ambil input
        val title = binding.etTitle.text.toString().trim()
        val date = binding.etDate.text.toString().trim()
        val time = binding.etTime.text.toString().trim()
        val location = binding.etLocation.text.toString().trim()
        val description = binding.etDescription.text.toString().trim()
        val capacityText = binding.etCapacity.text.toString().trim()
        val status = binding.etStatus.text.toString().trim()

        // validasi sederhana
        if (title.isEmpty() || date.isEmpty() || time.isEmpty() || location.isEmpty() || status.isEmpty()) {
            Toast.makeText(this, "Please fill title, date, time, location and status", Toast.LENGTH_SHORT).show()
            return
        }

        val capacity = try {
            if (capacityText.isEmpty()) 0 else capacityText.toInt()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Capacity must be a number", Toast.LENGTH_SHORT).show()
            return
        }

        // disable button sementara
        binding.btnCreateEvent.isEnabled = false
        binding.btnCreateEvent.text = "Creating..."

        lifecycleScope.launch {
            try {
                val response = ApiClient.instance.createEvent(
                    endpoint = "createEvent",
                    title = title,
                    date = date,
                    time = time,
                    location = location,
                    description = description,
                    capacity = capacity,
                    status = status
                )

                // tampilkan pesan dan kembali jika berhasil
                Toast.makeText(this@CreateEventActivity, response.message, Toast.LENGTH_LONG).show()

                if (response.status == 200 || response.status == 1 || response.message.contains("success", ignoreCase = true)) {
                    // tanda sukses: finish dan biar activity pemanggil refresh
                    setResult(RESULT_OK)
                    finish()
                } else {
                    // server balas tetapi tidak sukses
                    binding.btnCreateEvent.isEnabled = true
                    binding.btnCreateEvent.text = "Create Event"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@CreateEventActivity, "Failed: ${e.message}", Toast.LENGTH_LONG).show()
                binding.btnCreateEvent.isEnabled = true
                binding.btnCreateEvent.text = "Create Event"
            }
        }
    }
}
