package com.gforeroc.analyticshandler

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.mixpanel.android.mpmetrics.MixpanelAPI
import org.json.JSONObject

public class AnalyticsHandler(
     val firebaseAnalytics: FirebaseAnalytics?,
     val mixPanel: MixpanelAPI?
) : IAnalyticsHandler {

    override fun logButtonClick(screenName: String, buttonLabel: String) {
        val eventName = "click_" + screenName + "_" + buttonLabel
    }

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

    override fun logCustomEvent(
        screenName: String,
        eventLabel: String,
        additionalProperties: Map<String, String>?
    ) {
        val eventName = "custom_" + screenName + "_" + eventLabel
    }

    data class Builder(
        var firebaseAnalytics: FirebaseAnalytics? = null,
        var mixPanel: MixpanelAPI? = null) {

        fun firebase(firebaseAnalytics: FirebaseAnalytics) = apply { this.firebaseAnalytics = firebaseAnalytics }
        fun mixPanel(mixPanel: MixpanelAPI) = apply { this.mixPanel = mixPanel }
        fun build() = AnalyticsHandler(firebaseAnalytics,mixPanel)
    }

}