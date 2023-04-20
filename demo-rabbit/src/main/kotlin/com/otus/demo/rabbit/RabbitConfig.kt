package com.otus.demo.rabbit

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct


@EnableRabbit
@Configuration
class RabbitConfig(
    private val rabbitTemplate: RabbitTemplate
) {


    @Value("\${demo.queue}")
    private lateinit var demoQueueName: String


    @Value("\${demo.fanout.one}")
    private lateinit var demoFanoutNameOne: String

    @Value("\${demo.fanout.two}")
    private lateinit var demoFanoutNameTwo: String

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
        val demoQueueOne = Queue(demoQueueName, true)
        val demoFanoutOne = Queue(demoFanoutNameOne, true)
        val demoFanoutTwo = Queue(demoFanoutNameTwo, true)
        val demoRpc = Queue(demoRpcName, true)

        val demoExchangeQueue = DirectExchange(demoExchangeQueue)
        val demoExchangeFanout = FanoutExchange(demoExchangeFanout)
        val demoExchangeRpc = TopicExchange(demoExchangeRpc)
        return Declarables(
            demoQueueOne,
            demoFanoutOne,
            demoFanoutTwo,
            demoRpc,
            demoExchangeQueue,
            demoExchangeFanout,
            demoExchangeRpc,
            BindingBuilder.bind(demoQueueOne).to(demoExchangeQueue).with(demoQueueName),
            BindingBuilder.bind(demoFanoutOne).to(demoExchangeFanout),
            BindingBuilder.bind(demoFanoutTwo).to(demoExchangeFanout),
            BindingBuilder.bind(demoRpc).to(demoExchangeRpc).with(demoRpcName)
        )
    }

    @PostConstruct
    fun rabbitTemplate() {
        rabbitTemplate.setReplyTimeout(6000)
    }
}