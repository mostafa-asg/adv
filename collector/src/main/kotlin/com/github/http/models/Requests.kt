package com.github.http.models

data class ImpressionEvent(
        val requestId: String,
        val adId: String?,
        val adTitle: String?,
        val advertiserCost: Double,
        val appId: String?,
        val appTitle: String?,
        val impressionTime: Long
)

data class ClickEvent(val requestId: String,val clickTime: Long)