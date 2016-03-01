package com.ivan.jmp.bus;

import com.ivan.jmp.bus.api.Message;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.*;

/**
 * Created by Иван on 01.03.2016.
 */
public class ProducerTest {

    @Test
    public void produce_ShouldAddMessageInQueue() throws Exception {
        Queue<Message> messageQueue = new LinkedList<>();
        Producer producer = new Producer(messageQueue);

        producer.produce(new Message("topic", "message"));

        assertEquals(1, messageQueue.size());
    }
}