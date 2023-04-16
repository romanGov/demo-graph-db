package com.otus.demo.rabbit

import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Declarables
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@EnableRabbit
@Configuration
class RabbitConfig(
) {

    @Value("\${demo.queue}")
    private lateinit var demoQueueName: String

    @Value("\${demo.fanout}")
    private lateinit var demoFanoutName: String

    @Value("\${demo.rpc}")
    private lateinit var demoRpcName: String


    @Value("\${demo.exchange.queue}")
    private lateinit var demoExchangeQueue: String

    @Value("\${demo.exchange.fanout}")
    private lateinit var demoExchangeFanout: String

    @Value("\${demo.exchange.rpc}")
    private lateinit var demoExchangeRpc: String


    @Bean
    fun fanoutBindings(): Declarables {
        val demoQueue = Queue(demoQueueName, true)
        val demoFanout = Queue(demoFanoutName, true)
        val demoRpc = Queue(demoRpcName, true)

        val demoExchangeQueue = DirectExchange(demoExchangeQueue)
        val demoExchangeFanout = FanoutExchange(demoExchangeFanout)
        val demoExchangeRpc = TopicExchange(demoExchangeRpc)
        return Declarables(
            demoQueue,
            demoFanout,
            demoRpc,
            demoExchangeQueue,
            demoExchangeFanout,
            demoExchangeRpc,
            BindingBuilder.bind(demoQueue).to(demoExchangeQueue).with(demoQueueName),
            BindingBuilder.bind(demoFanout).to(demoExchangeFanout),
            BindingBuilder.bind(demoRpc).to(demoExchangeRpc).with(demoRpcName)
        )
    }
}