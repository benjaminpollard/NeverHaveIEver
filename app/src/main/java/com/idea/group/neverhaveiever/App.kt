package com.idea.group.neverhaveiever

import android.app.Application
import com.facebook.ads.AudienceNetworkAds


class App : Application()
{
    override fun onCreate() {
        super.onCreate()
        // Initialize the Audience Network SDK
        AudienceNetworkAds.initialize(this)
    }
}