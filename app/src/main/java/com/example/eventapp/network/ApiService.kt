package com.example.eventapp.network

import com.example.eventapp.model.EventRequest
import com.example.eventapp.model.EventResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("api.php")
    suspend fun getEvents(@Query("endpoint") endpoint: String = "events"): EventResponse

    @POST("api.php")
    suspend fun createEvent(@Body event: EventRequest): EventResponse

    @GET("api.php")
    suspend fun getEventById(
        @Query("endpoint") endpoint: String = "eventById",
        @Query("id") id: String
    ): EventResponse

    @GET("api.php")
    suspend fun getEventByDate(
        @Query("endpoint") endpoint: String = "eventByDate",
        @Query("date") date: String
    ): EventResponse
}
