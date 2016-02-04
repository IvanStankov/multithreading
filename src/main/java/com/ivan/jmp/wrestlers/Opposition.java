package com.ivan.jmp.wrestlers;

import java.util.Random;

/**
 * Created by Иван on 04.02.2016.
 */
public class Opposition {
    public class Counter {
        private int count = 10;

        public void increment() {
            count++;
        }

        public void decrement() {
            count--;
        }

        public int get() {
            return count;
        }
    }

    public class Wrestler implements Runnable {
        private Counter counter;
        private boolean increment;
        private Random rand = new Random();

        public Wrestler(Counter counter, boolean increment) {
            this.counter = counter;
            this.increment = increment;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (counter) {
                    int x = counter.get();

                    if (increment) {
                        counter.increment();
                        if (x == 0) {// if 0 then it might be another thread waits notifying so it is needed to release it
                            counter.notify();
                        }
                    } else {
                        if (x == 0) {// if 0 then thread will wait until another thread increments counter
                            try {
                                counter.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        counter.decrement();
                    }

                    if (x < 0) {
                        t1.interrupt();
                        t2.interrupt();
                        throw new IllegalStateException("We have below zero!");
                    }
                    System.out.println("Wrestler" + Thread.currentThread().getName() + " " + x);
                }

                try {
                    Thread.sleep(rand.nextInt(100));
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

    }

    private Thread t1, t2;

    public static void main(String[] args) {
        new Opposition().start();
    }

    private void start() {
        Counter counter = new Counter();
        t1 = new Thread(new Wrestler(counter, false));
        t2 = new Thread(new Wrestler(counter, true));
        t1.start();
        t2.start();
        try {
            while (true) {
                Thread.sleep(100);
                if (!(t1.isAlive() && t2.isAlive())) {
                    break;
                }
            }
        } catch (InterruptedException e) {
        }
        System.out.println("Finished");
    }
}
