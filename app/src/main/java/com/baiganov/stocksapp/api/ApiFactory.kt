package com.baiganov.stocksapp.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.TimeUnit

object ApiFactory {
    private const val BASE_URL = "https://finnhub.io/api/v1/"
    private val contentType = "application/json".toMediaType()

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    private val clientSetup = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES) // write timeout
        .readTimeout(1, TimeUnit.MINUTES) // read timeout
        .build()

    @ExperimentalSerializationApi
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(this.json.asConverterFactory(contentType))
        .client(clientSetup)
        .build()


    @ExperimentalSerializationApi
    val apiService: ApiService = retrofit.create()
}