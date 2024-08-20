package com.jovita.harrypotter.network


import com.jovita.harrypotter.model.MovieCharacter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("characters")
    suspend fun getCharacters(): Response<ArrayList<MovieCharacter>>

    companion object {
        private const val BASE_URL = "https://hp-api.onrender.com/api/"

        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
