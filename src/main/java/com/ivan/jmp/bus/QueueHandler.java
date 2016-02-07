package com.ivan.jmp.bus;

import com.ivan.jmp.bus.api.Consumer;
import com.ivan.jmp.bus.api.Message;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by Иван on 05.02.2016.
 */
public class QueueHandler {

    private final Queue<Message> queue;
    private Map<String, List<Consumer>> consumers = new HashMap<>();
    private Executor executor = new ThreadPoolExecutor(0, 3, 60L, TimeUnit.SECONDS,
            new SynchronousQueue<>()); // Cached thread pool with 3 threads maximum

    public QueueHandler(Queue<Message> queue) {
        this.queue = queue;
    }

    public void handle() {
        while (true) {
            Message message = null;
            synchronized (queue) {
                message = queue.poll();
                if (message == null) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        System.out.println("QueueHandler has been interrupted");
                    }
                }
            }
            if (message != null) {
                List<Consumer> consumerList = getConsumerList(message.getTopic());
                notifyConsumers(message.getMessage(), consumerList);
            }
        }
    }

    public void registerConsumer(String topic, Consumer consumer) {
        getConsumerList(topic).add(consumer);
    }

    private List<Consumer> getConsumerList(String topic) {
        if (!consumers.containsKey(topic)) {
            consumers.put(topic, new ArrayList<>());
        }
        return consumers.get(topic);
    }

    private void notifyConsumers(String message, List<Consumer> consumers) {
        consumers.forEach(consumer -> executor.execute(() -> consumer.handle(message)));
    }

}
