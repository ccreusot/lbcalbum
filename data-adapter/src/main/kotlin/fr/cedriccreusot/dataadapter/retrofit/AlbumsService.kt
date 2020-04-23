package fr.cedriccreusot.dataadapter.retrofit

import fr.cedriccreusot.dataadapter.retrofit.model.Track
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface AlbumsService {

    @GET("/img/shared/technical-test.json")
    fun getTracks(): Call<List<Track>>

    companion object {
        const val BASE_URL = "https://static.leboncoin.fr"

        fun createService(): AlbumsService =
            Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AlbumsService::class.java)
    }
}