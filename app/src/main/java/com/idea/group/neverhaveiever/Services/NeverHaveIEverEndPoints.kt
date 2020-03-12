package com.idea.group.neverhaveiever.Services

import android.os.AsyncTask
import com.idea.group.neverhaveiever.Models.APIModels.QuestionAPIModel
import retrofit2.http.GET
import retrofit2.http.Query

interface NeverHaveIEverEndPoints
{
    @GET("/q/q/")
    suspend fun getCardData() : QuestionAPIModel
}
