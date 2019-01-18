package com.github

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import kotlin.collections.HashMap

class Kafka(producerConfig: HashMap<String, Any>) {

    private val logger = LoggerFactory.getLogger(Kafka::class.java)
    private val producer = KafkaProducer<String,String>(producerConfig)

    fun send(topic: String, key: String, value: String) {
        val record = ProducerRecord(topic, key, value)
        producer.send(record) { _,exc ->
            if (exc != null) {
                logger.error("Error in sending record to kafka", exc)
            }
        }
    }

}