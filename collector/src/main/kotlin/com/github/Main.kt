package com.github

import com.github.controllers.EventController
import com.typesafe.config.ConfigFactory
import io.javalin.Javalin

fun main(args: Array<String>) {

    val config = ConfigFactory.load()
    val app = Javalin.create().start(config.getInt("collector.port"))

    // Kafka
    val producerConfig = config.getConfig("collector.kafka.producer").toHashMap()
    val kafka = Kafka(producerConfig)

    val eventController = EventController(kafka)

    app.put("event/impression", eventController::impressionHandler)
    app.put("event/click", eventController::clickHandler)
}