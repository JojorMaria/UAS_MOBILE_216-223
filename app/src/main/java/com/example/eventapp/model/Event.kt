package com.example.eventapp.model

data class Event(
    val id: Int,
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val description: String,
    val capacity: Int?,
    val status: String,
    val created_at: String,
    val updated_at: String
)


