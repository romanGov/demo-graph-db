package com.otus.demo.rabbit


import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class RabbitConsumer {
    var logger: Logger = LoggerFactory.getLogger(this::class.java.name)
    @RabbitListener(queues = ["\${demo.queue}"])
    fun createQueueListenerOne(message: String) {
        println("queue listener one Incoming message: $message")
    }
    @RabbitListener(queues = ["\${demo.queue}"])
    fun createListenerTwo(message: String) {
        println("queue listener two Incoming message: $message")
    }

    @RabbitListener(queues = ["\${demo.fanout.one}"])
    fun fanoutListenerOne(message: String) {
        println("fanout listener one Incoming message: $message")
    }
    @RabbitListener(queues = ["\${demo.fanout.two}"])
    fun fanoutListenerTwo(message: String) {
        println("fanout listener two Incoming message: $message")
    }

    @RabbitListener(queues = ["\${demo.rpc}"])
    fun rpcListener(message: Message): String {
        println("rpc listener  Incoming message: $message")
        println("correlationId: ${message.messageProperties.correlationId}")
        return "message received ${String(message.body)}"
    }
}