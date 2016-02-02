package com.ivan.jmp.car;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Ivan_Stankov on 02.02.2016.
 */
public class Car implements Runnable {
    private static final long MAX_DISTANCE = 10000;

    private long distance;
    private String name;
    private CountDownLatch latch;
    private Random random = new Random();

    public Car(String name, CountDownLatch latch) {
        this.name = name;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            latch.await();
            while (distance < MAX_DISTANCE) {
                if (Thread.currentThread().isInterrupted()) {
                    sayBroken();
                } else {
                    Thread.sleep(50 + random.nextInt(150));
                    distance += 100;
                    System.out.println(name + " " + distance);
                }
            }
        } catch (InterruptedException e) {
            sayBroken();
        }
    }

    private void sayBroken() {
        System.out.println("------------------ " + name + " broke down");
    }
}
