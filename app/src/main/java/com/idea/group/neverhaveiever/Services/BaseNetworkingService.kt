package com.idea.group.neverhaveiever.Services

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class BaseNetworkingService
{
    fun getNetworkingService (): Retrofit {
       return Retrofit.Builder()
            .baseUrl("http://neverhaveieverben.herokuapp.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}