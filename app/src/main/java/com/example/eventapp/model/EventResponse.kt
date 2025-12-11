package com.example.eventapp.model

data class EventResponse(
    val success: Boolean,
    val message: String,
    val data: List<Event>

)

