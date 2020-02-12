package com.idea.group.neverhaveiever.Services

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.idea.group.neverhaveiever.App


class AnalyticsService
{
    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    init {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(App.context)
    }

    public fun OnVotedBad(id: String , info : String)
    {

        val params = Bundle()
        params.putString("id", id)
        params.putString("text", info)
        mFirebaseAnalytics!!.logEvent("VotedBad", params)

    }
}