package ru.ilka.app;

public class Main {
    public static void main(String[] args) {
        //Объект-блокировка для синхронизации потоков.
        Object lock = new Object();
        //Размер буфера.
        int bufferSize = 10;
        int[] buffer = new int[bufferSize];

        //Создаем объекты Producer и Consumer, передавая им lock и buffer.
        Producer producer = new Producer(lock, buffer);
        Consumer consumer = new Consumer(lock, buffer);

        //Создаем потоки для Producer и Consumer, передавая им соответствующие объекты.
        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        //Запускаем потоки Producer и Consumer.
        producerThread.start();
        consumerThread.start();

        try {
            //Ждем завершения потоков Producer и Consumer.
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
