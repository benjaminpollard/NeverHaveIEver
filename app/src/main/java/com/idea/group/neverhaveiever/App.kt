package com.idea.group.neverhaveiever

import android.app.Application
import com.facebook.ads.AudienceNetworkAds
import io.realm.Realm


class App : Application()
{
    override fun onCreate() {
        super.onCreate()

        // Initialize the Audience Network SDK
        if (BuildConfig.ADS_ENABLED) AudienceNetworkAds.initialize(this)

        Realm.init(this)
    }
}