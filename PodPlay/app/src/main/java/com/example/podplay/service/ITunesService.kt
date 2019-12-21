package com.example.podplay.service

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesService {
    @GET("/search?media=podcast") // function annotation, meaning that it applies to a function
    fun searchPodcastByTerm(@Query("term") term: String): Call<PodcastResponse>

    // Singleton object. This ensures that the interface is only instantiated once during the app's lifetime
    companion object {
        val instance: ITunesService by lazy { // "by" is a property delegation from Kotlin
            val retrofit = Retrofit.Builder()
                .baseUrl("https://itunes.apple.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create<ITunesService>(ITunesService::class.java)
        }
    }
}