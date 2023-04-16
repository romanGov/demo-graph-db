package com.otus.demo.rabbit

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RabbitController(
    private val rabbitSender: RabbitSender
) {
    @PostMapping("send-queue")
    fun sendQueue(@RequestBody hashMap: HashMap<String,Any>){
        rabbitSender.sendQueue(hashMap.toString())
    }
}