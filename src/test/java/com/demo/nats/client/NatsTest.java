package com.demo.nats.client;

import io.nats.client.*;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class NatsTest {

    @Test
    void sendTest() throws Exception {
        //single URL
        Connection nc = Nats.connect("nats://localhost:4222");
        nc.publish("test", "test", "hello world".getBytes(StandardCharsets.UTF_8));
        System.out.println("test");
    }

    @Test
    void receiveTest1() throws Exception {
        Connection nc = Nats.connect("nats://localhost:4222");
        Subscription sub = nc.subscribe("test");
        Message msg = sub.nextMessage(Duration.ofMillis(10000));

        String response = new String(msg.getData(), StandardCharsets.UTF_8);
        System.out.println(response);
    }
    @Test
    void receiveTest2() throws Exception {
        Connection nc = Nats.connect("nats://localhost:4222");
        Dispatcher d = nc.createDispatcher((msg) -> {});

        Subscription s = d.subscribe("test", (msg) -> {
            String response = new String(msg.getData(), StandardCharsets.UTF_8);
            System.out.println("Message received (up to 100 times): " + response);
        });
        d.unsubscribe(s, 100);
    }

}
