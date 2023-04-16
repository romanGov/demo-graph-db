package com.otus.demo.rabbit

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class RabbitConsumer {
    var logger: Logger = LoggerFactory.getLogger(this::class.java.name)
    @RabbitListener(queues = ["\${demo.queue}"])
    fun createListener(message: String) {
        println()
        logger.info("Incoming message: $message")
    }
}