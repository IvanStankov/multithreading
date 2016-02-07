package com.ivan.jmp.bus;

import com.ivan.jmp.bus.api.Message;

import java.util.Queue;

/**
 * Created by Иван on 05.02.2016.
 */
public class Producer {

    private final Queue<Message> queue;

    public Producer(Queue<Message> queue) {
        this.queue = queue;
    }

    public void produce(Message message) {
        synchronized (queue) {
            queue.add(message);
            queue.notifyAll();
        }
    }

}
