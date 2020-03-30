package com.idea.group.neverhaveiever

import android.app.Application
import android.content.Context
import com.facebook.ads.AudienceNetworkAds
import io.realm.Realm
import io.realm.RealmConfiguration


class App : Application()
{
    override fun onCreate() {
        super.onCreate()

        context = this
        // Initialize the Audience Network SDK
        if (BuildConfig.ADS_ENABLED) AudienceNetworkAds.initialize(this)

        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .schemaVersion(1) // Must be bumped when the schema changes
            .build()

        Realm.setDefaultConfiguration(config)
    }

    companion object
    {
        lateinit var context : Context
    }
}