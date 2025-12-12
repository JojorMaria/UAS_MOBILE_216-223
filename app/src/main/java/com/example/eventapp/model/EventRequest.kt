package com.example.eventapp.model

data class EventRequest(
    val endpoint: String = "createEvent",
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val description: String,
    val capacity: Int,
    val status: String
)
