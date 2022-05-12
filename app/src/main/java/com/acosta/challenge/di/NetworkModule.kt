package com.acosta.challenge.di

import com.acosta.challenge.BuildConfig
import com.acosta.challenge.net.ServerApi
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule(
    val baseUrl: String = BuildConfig.SERVER_URL,
    val client: OkHttpClient = defaultClient()
) {

    val module = module {
        // For any json management
        single {
            GsonBuilder().create()
        }
        // OkHttp
        single { client }
        // Retrofit
        single {
            Retrofit.Builder()
                .client(get())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(get()))
                .build()
        }
        // Services
        single {
            get<Retrofit>().create(ServerApi::class.java)
        }
    }

    companion object {
        fun defaultClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(createHttpLogger())
                .build()
        }

        fun createHttpLogger() = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
    }

}