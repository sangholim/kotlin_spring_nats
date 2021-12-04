package com.demo.nats.binder

import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Processor
import org.springframework.messaging.handler.annotation.SendTo

@EnableBinding(Processor::class)
class SourceProcess {

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    fun transform(message: Any?): String {
        println(message)
        return "test"
    }

}