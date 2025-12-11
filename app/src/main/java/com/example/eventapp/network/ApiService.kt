package com.example.eventapp.network

import com.example.eventapp.model.Event
import com.example.eventapp.model.EventResponse
import retrofit2.http.*


interface ApiService {

    @GET("api.php")
    suspend fun getEvents(
        @Query("endpoint") endpoint: String = "events"
    ): EventResponse

    @FormUrlEncoded
    @POST("api.php")
    suspend fun createEvent(
        @Field("endpoint") endpoint: String = "createEvent",
        @Field("title") title: String,
        @Field("date") date: String,
        @Field("time") time: String,
        @Field("location") location: String,
        @Field("description") description: String,
        @Field("capacity") capacity: Int,
        @Field("status") status: String
    ): EventResponse

    @GET("api.php")
    suspend fun getEventById(
        @Query("endpoint") endpoint: String = "eventById",
        @Query("id") id: Int
    ): EventResponse

    @GET("api.php")
    suspend fun getEventByDate(
        @Query("endpoint") endpoint: String = "eventByDate",
        @Query("date") date: String
    ): EventResponse
}



