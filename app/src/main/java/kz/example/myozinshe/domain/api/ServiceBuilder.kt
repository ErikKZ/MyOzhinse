package kz.example.myozinshe.domain.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private val retforit = Retrofit.Builder()
        .baseUrl("http://api.ozinshe.com")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()

    fun<T> buildService(service: Class<T>): T = retforit.create(service)

}