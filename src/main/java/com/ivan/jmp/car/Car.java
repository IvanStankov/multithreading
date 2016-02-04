package com.ivan.jmp.car;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Ivan_Stankov on 02.02.2016.
 */
public class Car implements Runnable {
    private static final long MAX_DISTANCE = 5000;
    public static List<String> arrivals = new ArrayList<>();

    private long distance;
    private String name;
    private CountDownLatch start;
    private CountDownLatch finish;
    private Random random = new Random();

    public Car(String name, CountDownLatch start, CountDownLatch finish) {
        this.name = name;
        this.start = start;
        this.finish = finish;
    }

    @Override
    public void run() {
        try {
            start.await();
            while (distance < MAX_DISTANCE) {
                if (Thread.currentThread().isInterrupted()) {
                    breakCar();
                } else {
                    Thread.sleep(50 + random.nextInt(150));
                    distance += 100;
                    System.out.println(name + " " + distance);
                }
            }
            synchronized (arrivals) {
                arrivals.add(name);
                finish.countDown();
            }
        } catch (InterruptedException e) {
            breakCar();
        }
    }

    private void breakCar() {
        finish.countDown();
        System.out.println("------------------ " + name + " broke down");
        return;
    }
}
