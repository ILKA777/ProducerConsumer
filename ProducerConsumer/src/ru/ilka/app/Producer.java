package ru.ilka.app;

public class Producer implements Runnable {
    //Объект блокировки для синхронизации доступа к буферу.
    private final Object lock;
    //Буфер для хранения значений.
    private final int[] buffer;
    //Текущее количество значений в буфере.
    private int count;

    public Producer(Object lock, int[] buffer) {
        this.lock = lock;
        this.buffer = buffer;
        this.count = 0;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                produce(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void produce(int value) throws InterruptedException {
        //Синхронизация доступа к буферу.
        synchronized (lock) {
            //Пока буфер полный, ждем
            while (count == buffer.length) {
                lock.wait();
            }
            //Добавляем значение в буфер и увеличиваем счетчик.
            buffer[count++] = value;
            System.out.println("Produced " + value);
            //Уведомляем другой поток, что можно продолжать работу.
            lock.notifyAll();
        }
    }
}
