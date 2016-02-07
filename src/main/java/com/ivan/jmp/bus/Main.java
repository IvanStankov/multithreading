package com.ivan.jmp.bus;

import com.ivan.jmp.bus.api.Message;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Created by Иван on 07.02.2016.
 */
public class Main {

    public static void main(String[] args) {
        Queue<Message> queue = new LinkedList<>();

        Producer p = new Producer(queue);
        QueueHandler c = new QueueHandler(queue);

        Thread t1 = new Thread(new Sender("Topic 1", p));
        Thread t2 = new Thread(new Sender("Topic 2", p));
        t1.start();
        t2.start();

        Thread t3 = new Thread(c::handle);
        t3.start();

        c.registerConsumer("Topic 1", message -> {
            System.out.println("Topic 1: " + message);
        });
        c.registerConsumer("Topic 2", message -> {
            System.out.println("Topic 2: " + message);
        });
    }

    private static class Sender implements Runnable {
        private String topic;
        private Producer p;

        public Sender(String topic, Producer p) {
            this.topic = topic;
            this.p = p;
        }

        @Override
        public void run() {
            while (true) {
                Random random = new Random();
                p.produce(new Message(topic, "Number is: " + random.nextInt(200)));
                try {
                    Thread.sleep(500 + random.nextInt(1500));
                } catch (InterruptedException e) {
                    System.out.println("Thread has been broken");
                }
            }
        }
    }

}
