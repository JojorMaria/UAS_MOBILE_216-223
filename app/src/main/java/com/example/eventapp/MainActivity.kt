package com.example.eventapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventapp.adapter.EventAdapter
import com.example.eventapp.databinding.ActivityMainBinding
import com.example.eventapp.network.ApiClient
import kotlinx.coroutines.launch
import android.content.Intent

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // tombol
        binding.btnOpenCreate.setOnClickListener {
            startActivity(Intent(this, CreateEventActivity::class.java))
        }

        // rv
        binding.rvEvents.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        loadEvents()
    }

    private fun loadEvents() {
        lifecycleScope.launch {
            try {
                val response = ApiClient.instance.getEvents()
                val events = response.data

                binding.rvEvents.adapter = EventAdapter(events)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
