package ru.geekbrains.java_algoritms_homework_12;

import java.util.Arrays;

public class Main {

    static final int SIZE = 10000000; // Размер исходного массива
    static final int HALF = SIZE / 2; // Размер для половинчатых массивов

    public static void main(String[] args) {
        System.out.print("Single thread method execution time: ");
        method1(); // Запуск однопоточного метода 1
        System.out.println();
        System.out.print("Double thread method execution time: ");
        method2(); // Запуск двухпоточного метода 2
    }

    public static void method1() { // Создание однопоточного метода
        float[] arr = new float[SIZE]; // Создание полноразмерного массива
        Arrays.fill(arr, 1.0F); // Заполнение массива единицами
        long a = System.currentTimeMillis(); // Измерение времени в миллисекундах перед запуском процесса
        for (int i = 0; i < arr.length; i++) { // Начало процесса заполнения по формуле
            arr[i] = (float) (arr[i] * Math.sin(0.2F + (float) i / 5) * Math.cos(0.2F + (float) i / 5) * Math.cos(0.4F + (float) i / 2));
        }
        System.out.println(System.currentTimeMillis() - a); // Измерение времени и подсчёт разницы между началом и концом процесса
    }

    public static void method2() { // Создание двухпоточного метода
        float[] arr = new float[SIZE]; // Создание полноразмерного массива
        float[] arr1 = new float[HALF]; // Создание первого половинчатого массива
        float[] arr2 = new float[HALF]; // Создание второго половинчатого массива
        Arrays.fill(arr, 1.0F); // Заполнение основного массива единицами
        long a = System.currentTimeMillis(); // Измерение времени в миллисекундах перед запуском процесса
        System.arraycopy(arr, 0, arr1, 0, HALF); // Разбивание массива на два подмассива
        System.arraycopy(arr, HALF, arr2, 0, HALF);
        Thread t1 = new Thread(arrayCalculate(arr1)); // Создание потока на заполнение первого подмассива
        Thread t2 = new Thread(arrayCalculate(arr2)); // Создание потока на заполнение второго подмассива
        t1.start(); // Запуск потока 1
        t2.start(); // Запуск потока 2
        try {
            t1.join(); // Приостановка основного потока до завершения работы потока 1
            t2.join(); // Приостановка основного потока до завершения работы потока 2
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.arraycopy(arr1, 0, arr, 0, HALF); // Сборка полноразмерного массива из двух подмассивов
        System.arraycopy(arr2, 0, arr, HALF, HALF);

        System.out.println(System.currentTimeMillis() - a); // Измерение времени и подсчёт разницы между началом и концом процесса

    }

    static Runnable arrayCalculate(float[] arr) { // Метод заполнения массива по формуле
        return () -> {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (float) (arr[i] * Math.sin(0.2F + (float) i / 5) * Math.cos(0.2F + (float) i / 5) * Math.cos(0.4F + (float) i / 2));
            }
        };
    }
}
