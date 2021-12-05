package com.demo.nats.binder

import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.Output
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Processor
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.handler.annotation.SendTo

@EnableBinding(AccountChannel::class)
class AccountProcess {

    @StreamListener("accountIn")
    @SendTo("accountOut")
    fun transform(message: Message<String>): Message<String> {
        println("account > request-id: $message, response-id: $message")
        return message
    }

}

interface AccountChannel {

    @Input("accountIn")
    fun accountInChannel(): MessageChannel

    @Output("accountOut")
    fun accountOutChannel(): MessageChannel
}