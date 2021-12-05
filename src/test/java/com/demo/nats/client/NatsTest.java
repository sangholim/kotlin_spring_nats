package com.demo.nats.client;

import io.nats.client.*;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

public class NatsTest {

    private final List<String> ACCOUNT_DESTINATIONS = List.of("account.register");

    private final List<String> USER_DESTINATIONS = List.of("users.profile");

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
        Dispatcher d = nc.createDispatcher((msg) -> {
        });

        Subscription s = d.subscribe("test", (msg) -> {
            String response = new String(msg.getData(), StandardCharsets.UTF_8);
            System.out.println("Message received (up to 100 times): " + response);
        });
        d.unsubscribe(s, 100);
    }

    @Test
    void requestReplyTest() throws Exception {
        //single URL
        Connection nc = Nats.connect("nats://localhost:4222");
        for (int i = 0; i < 100; i++) {
            Message message = nc.request("dataIn", String.valueOf(i).getBytes(StandardCharsets.UTF_8), Duration.ofSeconds(1000));
            System.out.println("request-id: " + i + ", response-id: " + new String(message.getData(), StandardCharsets.UTF_8));
        }

    }

    @Test
    void accountDestinationsTest() throws Exception {
        //single URL
        Connection nc = Nats.connect("nats://localhost:4222");
        for (String destination : ACCOUNT_DESTINATIONS) {
            Message message = nc.request(destination, destination.concat(" test").getBytes(StandardCharsets.UTF_8), Duration.ofSeconds(1000));
            System.out.println(destination + " > " + new String(message.getData(), StandardCharsets.UTF_8));
        }
    }

    @Test
    void userDestinationsTest() throws Exception {
        //single URL
        Connection nc = Nats.connect("nats://localhost:4222");
        for (String destination : USER_DESTINATIONS) {
            Message message = nc.request(destination, destination.concat(" test").getBytes(StandardCharsets.UTF_8), Duration.ofSeconds(1000));
            System.out.println(destination + " > " + new String(message.getData(), StandardCharsets.UTF_8));
        }
    }

}