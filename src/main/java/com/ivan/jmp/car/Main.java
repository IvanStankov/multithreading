package com.ivan.jmp.car;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Ivan_Stankov on 02.02.2016.
 */
public class Main {

    public static void main(String[] args) {
        CountDownLatch start = new CountDownLatch(1);

        List<Thread> threads = new ArrayList<>();
        threads.add(new Thread(new Car("Chevrolet", start)));
        threads.add(new Thread(new Car("Volkswagen", start)));
        threads.add(new Thread(new Car("Lada sedan", start)));
        threads.forEach(Thread::start);

        start.countDown();
        disqualify(threads);

        CountDownLatch finish = new CountDownLatch(threads.size());

        System.out.println("Winner is ");
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
