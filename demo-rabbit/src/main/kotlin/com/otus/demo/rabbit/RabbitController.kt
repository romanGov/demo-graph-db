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
    @PostMapping("send-fanout")
    fun sendFanout(@RequestBody hashMap: HashMap<String,Any>){
        rabbitSender.sendFanout(hashMap.toString())
    }
    @PostMapping("send-rpc")
    fun sendRpc(@RequestBody hashMap: HashMap<String,Any>): String {
        return rabbitSender.sendRpc(hashMap.toString())
    }
}