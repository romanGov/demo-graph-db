package com.otus.demo.rabbit


import com.sun.org.slf4j.internal.LoggerFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component
class RabbitConsumer {
    var logger: Logger = LoggerFactory.getLogger(this::class.java.name)
    @RabbitListener(queues = ["\${demo.queue}"])
    fun createQueueListenerOne(message: String) {
        logger.info("queue listener one Incoming message: $message")
    }
    @RabbitListener(queues = ["\${demo.queue}"])
    fun createListenerTwo(message: String) {
        logger.info("queue listener two Incoming message: $message")
    }

    @RabbitListener(queues = ["\${demo.fanout.one}"])
    fun fanoutListenerOne(message: String) {
        logger.info("fanout listener one Incoming message: $message")
    }
    @RabbitListener(queues = ["\${demo.fanout.two}"])
    fun fanoutListenerTwo(message: String) {
        logger.info("fanout listener two Incoming message: $message")
    }

    @RabbitListener(queues = ["\${demo.rpc}"])
    fun rpcListener(message: Message): String {
        logger.info("rpc listener  Incoming message: $message")
        logger.info("correlationId: ${message.messageProperties.correlationId}")
        return "message received ${String(message.body)}"
    }
}