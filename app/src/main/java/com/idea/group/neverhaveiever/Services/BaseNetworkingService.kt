package com.idea.group.neverhaveiever.Services

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class BaseNetworkingService
{
    fun getNetworkingService (): Retrofit {
       return Retrofit.Builder()
            .baseUrl("https://api.example.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}