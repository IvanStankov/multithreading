package com.ivan.jmp.car;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Ivan_Stankov on 02.02.2016.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch finish = new CountDownLatch(3);

        List<Thread> threads = new ArrayList<>();
        threads.add(new Thread(new Car("Chevrolet", start, finish)));
        threads.add(new Thread(new Car("Volkswagen", start, finish)));
        threads.add(new Thread(new Car("Lada sedan", start, finish)));
        threads.forEach(Thread::start);

        start.countDown();
        disqualify(threads);

        finish.await();
        System.out.println("Winner is " + Car.arrivals.get(0));
    }

    private static void disqualify(List<Thread> threads) {
        try {
            Thread.sleep(2000);
            int number = new Random().nextInt(threads.size());
            threads.get(number).interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
