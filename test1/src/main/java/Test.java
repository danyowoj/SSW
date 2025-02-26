import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

    private static final int NUM_LISTS = 5; // Количество списков
    private static final int NUM_THREADS = 10; // Количество потоков
    private static final int NUM_ITERATIONS = 5; // Количество успешных записей на поток

    private static List<List<Integer>> lists = new ArrayList<>(); // Список списков для хранения данных
    private static List<ReentrantLock> locks = new ArrayList<>(); // Список ReentrantLock для каждого списка
    private static Semaphore semaphore = new Semaphore(NUM_LISTS); // Семафор для ограничения доступа к поиску свободного списка
    private static Random random = new Random(); // Генератор случайных чисел

    public static void main(String[] args) {
        // Инициализация списков и локов
        for (int i = 0; i < NUM_LISTS; i++) {
            lists.add(new ArrayList<>()); // Создаем пустой список
            locks.add(new ReentrantLock()); // Создаем ReentrantLock для каждого списка
        }

        // Создание и запуск потоков
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < NUM_THREADS; i++) {
            Thread thread = new Thread(new Worker()); // Создаем поток
            threads.add(thread); // Добавляем поток в список
            thread.start(); // Запускаем поток
        }

        // Ожидание завершения всех потоков
        for (Thread thread : threads) {
            try {
                thread.join(); // Ждем завершения каждого потока
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Вывод всех списков после завершения работы потоков
        System.out.println("\nFinal lists:");
        for (int i = 0; i < NUM_LISTS; i++) {
            System.out.println("List " + i + ": " + lists.get(i));
        }
    }

    static class Worker implements Runnable {
        @Override
        public void run() {
            int successfulWrites = 0; // Счетчик успешных записей

            // Пока не выполнено 5 успешных записей
            while (successfulWrites < NUM_ITERATIONS) {
                try {
                    // Ожидание разрешения от семафора (максимум NUM_LISTS потоков могут искать свободный список)
                    semaphore.acquire();

                    // Поиск свободного списка
                    ReentrantLock freeLock = null;
                    int listIndex = -1;
                    for (int i = 0; i < NUM_LISTS; i++) {
                        if (locks.get(i).tryLock()) { // Пытаемся захватить лок
                            //locks.get(i).lock();;
                            freeLock = locks.get(i); // Запоминаем захваченный лок
                            listIndex = i; // Запоминаем индекс списка
                            break; // Выходим из цикла, если нашли свободный список
                        }
                    }

                    if (freeLock != null) {
                        try {
                            // Генерация случайной задержки от 1 до 3 секунд
                            int delay = 1000 + random.nextInt(2000); // Случайное число от 1000 до 3000 мс
                            Thread.sleep(delay); // Симуляция нагрузки

                            // Запись случайного значения в список
                            int value = (int) (Math.random() * 100);
                            lists.get(listIndex).add(value);
                            System.out.println("Thread " + Thread.currentThread().getId() + " wrote " + value + " to list " + listIndex + " after " + delay + " ms delay");
                            successfulWrites++; // Увеличиваем счетчик успешных записей
                        } finally {
                            freeLock.unlock(); // Освобождаем лок
                        }
                    } else {
                        // Если свободный список не найден, выводим сообщение об ожидании
                        System.out.println("Thread " + Thread.currentThread().getId() + " is waiting for a free list...");
                        int delay = 1000 + random.nextInt(2000); // Случайная задержка от 1 до 3 секунд
                        Thread.sleep(delay); // Спим перед повторной попыткой
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release(); // Освобождаем разрешение семафора
                }
            }

            // Сообщение о завершении работы потока
            System.out.println("Thread " + Thread.currentThread().getId() + " has finished its work.");
        }
    }
}