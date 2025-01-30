package com.example.ticketmaster.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TicketmasterApi {
    @GET("discovery/v2/events")
    fun getEvents(
        @Query("apikey") apiKey: String,
//        @Query("keyword") keyword: String?,
        @Query("size") size: Int =100,
        @Query("page") page: Int = 0
    ): Call<EventResponse>
}
