package com.gforeroc.analyticshandler

interface IAnalyticsHandler {
    fun logScreenLaunch(screenName: String)

    fun logCustomEvent(
        screenName: String,
        eventLabel: String,
        additionalProperties: Map<String, String>? = null
    )

    fun logButtonClick(
        screenName: String,
        buttonLabel: String
    )
}

