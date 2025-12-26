package com.simats.SkillMatchV3.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    // ✅ MUST include :8080
    private const val BASE_URL = "http://10.0.2.2:8080/skillmatch/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
