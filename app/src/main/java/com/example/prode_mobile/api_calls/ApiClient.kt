package com.example.prode_mobile.api_calls

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class ApiClient {
    private val urlBase = "https://api.sportmonks.com/"

    protected val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(urlBase)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
