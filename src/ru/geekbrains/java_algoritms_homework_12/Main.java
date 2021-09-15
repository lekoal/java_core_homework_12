package ru.geekbrains.java_algoritms_homework_12;

import java.util.Arrays;

public class Main {

    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;

    public static void main(String[] args) {
        System.out.print("Single thread method execution time: ");
        method1();
        System.out.println();
        System.out.print("Double thread method execution time: ");
        method2();
    }

    public static void method1() {
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1.0F);
        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2F + (float) i / 5) * Math.cos(0.2F + (float) i / 5) * Math.cos(0.4F + (float) i / 2));
        }
        System.out.println(System.currentTimeMillis() - a);
    }

    public static void method2() {
        float[] arr = new float[SIZE];
        float[] arr1 = new float[HALF];
        float[] arr2 = new float[HALF];
        Arrays.fill(arr, 1.0F);
        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, arr1, 0, HALF);
        System.arraycopy(arr, HALF, arr2, 0, HALF);
        Thread t1 = new Thread(arrayCalculate(arr1));
        Thread t2 = new Thread(arrayCalculate(arr2));
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.arraycopy(arr1, 0, arr, 0, HALF);
        System.arraycopy(arr2, 0, arr, HALF, HALF);

        System.out.println(System.currentTimeMillis() - a);

    }

    static Runnable arrayCalculate(float[] arr) {
        return () -> {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (float) (arr[i] * Math.sin(0.2F + (float) i / 5) * Math.cos(0.2F + (float) i / 5) * Math.cos(0.4F + (float) i / 2));
            }
        };
    }
}
