package com.idea.group.neverhaveiever

import android.app.Application
import android.content.Context
import com.facebook.ads.AudienceNetworkAds
import io.realm.Realm


class App : Application()
{
    override fun onCreate() {
        super.onCreate()

        context = this
        // Initialize the Audience Network SDK
        if (BuildConfig.ADS_ENABLED) AudienceNetworkAds.initialize(this)

        Realm.init(this)
    }

    companion object
    {
        lateinit var context : Context
    }
}