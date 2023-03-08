package ru.ilka.app;

public class Consumer implements Runnable {
    private final Object lock;
    private final int[] buffer;
    private int count;

    //Конструктор класса Consumer.
    public Consumer(Object lock, int[] buffer) {
        //Объект блокировки для синхронизации.
        this.lock = lock;
        //Буфер для хранения элементов.
        this.buffer = buffer;
        //Начальное значение счётчика элементов.
        this.count = buffer.length;
    }

    @Override
    public void run() {
        //Поток выполняет 20 итераций.
        for (int i = 0; i < 20; i++) {
            try {
                //Получение элемента из буфера.
                int value = consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //Метод для взятия элемента из буфера.
    public int consume() throws InterruptedException {
        synchronized (lock) {
            //Пока буфер пуст, поток ожидает уведомления о появлении новых элементов.
            while (count == 0) {
                lock.wait();
            }
            //Извлечение элемента из буфера.
            int value = buffer[--count];
            System.out.println("Consumed " + value);
            //Оповещение всех потоков, которые ждут уведомления о появлении новых элементов.
            lock.notifyAll();
            //Возвращение извлечённого элемента.
            return value;
        }
    }
}
