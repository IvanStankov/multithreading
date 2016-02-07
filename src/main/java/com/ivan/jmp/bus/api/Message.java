package com.ivan.jmp.bus.api;

/**
 * Created by Иван on 07.02.2016.
 */
public class Message {

    private String topic;
    private String message;

    public Message(String topic, String message) {
        this.topic = topic;
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public String getMessage() {
        return message;
    }
}
