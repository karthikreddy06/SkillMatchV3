package com.simats.SkillMatchV3.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    // ✅ FIXED: Physical device must use PC LAN IP
    // Your PC IP from ipconfig = 10.68.202.44
    private const val BASE_URL = "http://10.68.202.44:8080/skillmatch/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
