package com.gforeroc.analyticshandler

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.mixpanel.android.mpmetrics.MixpanelAPI
import org.json.JSONObject

class AnalyticsHandler(
    var firebaseAnalytics: FirebaseAnalytics?,
    var mixPanel: MixpanelAPI?
) : IAnalyticsHandler {

    companion object{
        const val eventLaunchedScreen = "eventLaunchedScreen"
    }

    override
    fun logScreenLaunch(screenName: String) {
        val eventName = "screenLaunch_$screenName"
        firebaseAnalytics?.apply {
            val bundle = Bundle()
            bundle.putString("screenLaunch", eventName)
            logEvent(eventLaunchedScreen, bundle)
        }
        mixPanel?.apply {
            val properties = JSONObject()
            properties.put("screenLaunch", eventName)
            track(eventLaunchedScreen,properties)
        }
    }

}