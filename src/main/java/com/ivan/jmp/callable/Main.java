package com.ivan.jmp.callable;

/**
 * Created by Ivan_Stankov on 02.02.2016.
 */
public class Main {

    static int i;

    public static void main(String[] args) {
        Runnable r = () -> {
            for (int i = 0; i < 1000; i++) {
                Main.i++;
            }
        };
        Thread thread1 = new Thread(r);
        Thread thread2 = new Thread(r);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(i);
    }

}
