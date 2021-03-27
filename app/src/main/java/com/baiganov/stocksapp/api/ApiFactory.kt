package com.baiganov.stocksapp.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object ApiFactory {
    private const val BASE_URL_FIN = "https://finnhub.io/api/v1/"
    private const val BASE_URL_ALPHA = "https://www.alphavantage.co/"
    private val contentType = "application/json".toMediaType()

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @ExperimentalSerializationApi
    private val retrofitFin = Retrofit.Builder()
        .baseUrl(BASE_URL_FIN)
        .addConverterFactory(json.asConverterFactory(contentType))
        .client(client)
        .build()

    @ExperimentalSerializationApi
    private val retrofitAlpha = Retrofit.Builder()
        .baseUrl(BASE_URL_ALPHA)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @ExperimentalSerializationApi
    val apiServiceFin: ApiServiceFin = retrofitFin.create()
    @ExperimentalSerializationApi
    val apiServiceAlpha: ApiServiceAlpha = retrofitAlpha.create()
}