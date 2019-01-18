package com.github.controllers

import com.github.Kafka
import com.github.http.models.ClickEvent
import com.github.http.models.ImpressionEvent
import io.javalin.Context

class EventController(private val kafka: Kafka) {

    fun impressionHandler(ctx: Context) {
        // Only accepts valid requests
        // Otherwise return BAD_REQUEST 400
        ctx.validatedBody<ImpressionEvent>()

        val event = ctx.body<ImpressionEvent>()
        kafka.send("impressions", event.requestId, ctx.body())
    }

    fun clickHandler(ctx: Context) {
        // Only accepts valid requests
        // Otherwise return BAD_REQUEST 400
        ctx.validatedBody<ClickEvent>()

        val event = ctx.body<ClickEvent>()
        kafka.send("clicks", event.requestId, ctx.body())
    }

}