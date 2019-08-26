package com.gforeroc.analyticshandler

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager
import com.appsee.Appsee
import com.facebook.FacebookSdk
import com.flurry.android.FlurryAgent
import com.google.firebase.analytics.FirebaseAnalytics
import com.mixpanel.android.mpmetrics.MixpanelAPI
import org.json.JSONObject


class AnalyticsHandler(
    builder: Builder
) : IAnalyticsHandler {

    private var context: Context?
    private var firebaseAnalytics: FirebaseAnalytics?
    private var mixPanel: MixpanelAPI?

    override fun logButtonClick(screenName: String, buttonLabel: String) {
        val eventName = "click_" + screenName + "_" + buttonLabel
    }

    companion object {
        const val eventLaunchedScreen = "eventLaunchedScreen"
    }

    init {
        context = builder.context
        firebaseAnalytics = builder.firebaseAnalytics
        mixPanel = builder.mixPanel
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
            track(eventLaunchedScreen, properties)
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
        var context: Context
    ) {
        var firebaseAnalytics: FirebaseAnalytics? = null
        var mixPanel: MixpanelAPI? = null
        var pinpointManager: PinpointManager? = null
        
        fun amazon(pinpointManager: PinpointManager) = apply {
            this.pinpointManager = pinpointManager
        }

        fun firebase(firebaseAnalytics: FirebaseAnalytics) =
            apply { this.firebaseAnalytics = firebaseAnalytics }

        fun mixPanel(mixPanel: MixpanelAPI) = apply { this.mixPanel = mixPanel }

        fun appSee() = apply {
            Appsee.start("something")
        }

        fun facebookAnalytics(appId: String) = apply {
            FacebookSdk.setApplicationId(appId)
        }

        fun flurry(apiKey: String) = apply {
            FlurryAgent.Builder()
                .withLogEnabled(true)
                .withCaptureUncaughtExceptions(true)
                .withContinueSessionMillis(10000)
                .withLogLevel(Log.VERBOSE)
                .build(context, apiKey)
        }

        fun build(): AnalyticsHandler {
            return AnalyticsHandler(this)
        }
    }

}


