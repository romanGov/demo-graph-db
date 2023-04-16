package com.otus.demo.rabbit

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class RabbitSender(
    @Value("\${demo.exchange.queue}")
    private val demoExchangeQueue: String,
    private val rabbitTemplate: RabbitTemplate,
) {
    fun sendQueue(message: String) {
        rabbitTemplate.convertAndSend(
            demoExchangeQueue, "demo-queue", message
        )

    }
}