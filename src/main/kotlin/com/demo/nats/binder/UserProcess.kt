package com.demo.nats.binder

import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.Output
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Processor
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.handler.annotation.SendTo

@EnableBinding(UserChannel::class)
class UserProcess {

    @StreamListener("userIn")
    @SendTo("userOut")
    fun transform(message: Message<String>): Message<String> {
        println("user > request-id: $message, response-id: $message")
        return message
    }

}

interface UserChannel {

    @Input("userIn")
    fun userInChannel(): MessageChannel

    @Output("userOut")
    fun userOutChannel(): MessageChannel
}