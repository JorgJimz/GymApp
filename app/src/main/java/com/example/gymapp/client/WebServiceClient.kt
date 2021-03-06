package com.example.gymapp.client

import com.example.gymapp.ws.IWebService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object WebServiceClient {

    private var url = "https://desmovilesan-apim.azure-api.net/"
    private var httpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private fun buildRetrofit() = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitService : IWebService by lazy{
        buildRetrofit().create(IWebService::class.java)
    }

}