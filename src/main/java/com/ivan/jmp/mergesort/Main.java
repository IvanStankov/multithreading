package com.ivan.jmp.mergesort;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Created by Иван on 08.02.2016.
 */
public class Main {

    public static void main(String[] args) {
        Integer[] arr = generateArray();

        long start = System.nanoTime();
        new ForkJoinPool(2).invoke(new MergeAction(arr));
        long end = System.nanoTime();
        System.out.println("Lasted: " + (end - start));

        arr = generateArray();
        start = System.nanoTime();
        Arrays.sort(arr);
        end = System.nanoTime();
        System.out.println("Lasted: " + (end - start));
    }

    private static Integer[] generateArray() {
        Integer[] arr = new Integer[1_000_000];
        Random rand = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rand.nextInt(2000);
        }
        return arr;
    }

    private static class MergeAction extends RecursiveAction {

        private Comparable[] array;

        public MergeAction(Comparable[] array) {
            this.array = array;
        }

        @Override
        protected void compute() {
            if (array.length != 1) {
                Comparable[] first = Arrays.copyOfRange(array, 0, array.length / 2);
                Comparable[] second = Arrays.copyOfRange(array, array.length / 2, array.length);

                invokeAll(new MergeAction(first), new MergeAction(second));

                merge(first, second, array);
            }
        }

        private void merge(Comparable[] first, Comparable[] second, Comparable[] result) {
            int i = 0;
            int j = 0;
            int k = 0;
            while (i < first.length && j < second.length) {
                if (first[i].compareTo(second[j]) < 0) {
                    result[k] = first[i++];
                } else {
                    result[k] = second[j++];
                }
                k++;
            }
            System.arraycopy(first, i, result, k, first.length - i);
            System.arraycopy(second, j, result, k, second.length - j);
        }
    }

}
