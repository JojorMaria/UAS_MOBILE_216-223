package com.example.eventapp.model

data class EventResponse(
    val message: String,
    val data: List<Event>? = null, // untuk GET semua events atau by date
    val event: Event? = null       // untuk POST createEvent (jika server kirim object tunggal)
)
