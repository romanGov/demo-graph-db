package com.otus.demo.rabbit

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class RabbitSender(
    @Value("\${demo.exchange.queue}")
    private val demoExchangeQueue: String,
    @Value("\${demo.exchange.fanout}")
    private val demoExchangeFanout: String,


    @Value("\${demo.rpc}")
    private var demoRpcName: String,

    @Value("\${demo.exchange.rpc}")
    private val demoExchangeRpc: String,

    private val rabbitTemplate: RabbitTemplate,

    @Value("\${demo.queue}")
    private  var demoQueueRoutingKey: String
) {
    fun sendQueue(message: String) {
        rabbitTemplate.convertAndSend(
            demoExchangeQueue, demoQueueRoutingKey, message
        )
    }
    fun sendFanout(message: String) {
        rabbitTemplate.convertAndSend(demoExchangeFanout,"*", message)
    }
    fun sendRpc(message: String): String {
        val any = rabbitTemplate.convertSendAndReceive(demoExchangeRpc, demoRpcName, message)
        return any.toString()
    }
}