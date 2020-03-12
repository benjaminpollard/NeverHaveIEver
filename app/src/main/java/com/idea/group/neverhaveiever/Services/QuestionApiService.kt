package com.idea.group.neverhaveiever.Services

class QuestionApiService ()
{
    var client = BaseNetworkingService().getNetworkingService()
    suspend fun getQuestions() = client.create(NeverHaveIEverEndPoints::class.java).getCardData()
}