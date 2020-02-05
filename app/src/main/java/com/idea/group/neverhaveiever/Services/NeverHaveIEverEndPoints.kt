package com.idea.group.neverhaveiever.Services

import android.os.AsyncTask
import retrofit2.http.GET
import retrofit2.http.Query

interface NeverHaveIEverEndPoints
{
    @GET("/feed/here/")
    suspend fun getCardTeenData() : AsyncTask.Status

    @GET("/feed/here/")
    suspend fun getCardAdultData() : AsyncTask.Status
}
