import java.util.Random;

public class MultiThreadedCalculation {


    private static final int THREAD_COUNT = 5;         // Количество потоков
    private static final int PROGRESS_BAR_LENGTH = 50; // Длина расчёта (количество шагов прогресс-бара)
    private static final int MIN_DELAY = 50;          // Минимальная задержка (в миллисекундах)
    private static final int MAX_DELAY = 250;          // Максимальная задержка (в миллисекундах)
    private static final Object lock = new Object();   // Общий объект для синхронизации вывода
    private static final Random random = new Random(); // Генератор случайных чисел

    public static void main(String[] args) {
        int[] progress = new int[THREAD_COUNT];          // Массив для хранения прогресса каждого потока
        long[] completionTimes = new long[THREAD_COUNT]; // Массив для хранения времени выполнения каждого потока
        Thread[] threads = new Thread[THREAD_COUNT];     // Массив для хранения потоков

        // Создаём и запускаем потоки
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadIndex = i;
            threads[i] = new Thread(() -> {
                long startTime = System.currentTimeMillis();

                for (int j = 0; j <= PROGRESS_BAR_LENGTH; j++) {
                    synchronized (lock) {
                        progress[threadIndex] = j; // Обновляем прогресс текущего потока
                        clearConsole();            // Очищаем консоль и выводим прогресс-бары всех потоков

                        for (int k = 0; k < THREAD_COUNT; k++) {
                            System.out.printf("Thread %d (ID: %d): [%-" + PROGRESS_BAR_LENGTH + "s] %d%%%n",
                                    k + 1,
                                    threads[k].getId(),
                                    "=".repeat(progress[k]),
                                    (int) ((progress[k] / (double) PROGRESS_BAR_LENGTH) * 100));
                        }
                    }

                    // Случайная задержка от 50 до 100 мс
                    try {
                        Thread.sleep(MIN_DELAY + random.nextInt(MAX_DELAY - MIN_DELAY + 1));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Записываем время выполнения потока
                completionTimes[threadIndex] = System.currentTimeMillis() - startTime;
            });
            threads[i].start();
        }

        // Ждём завершения всех потоков
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Выводим итоговое время выполнения каждого потока
        System.out.println("\nCompletion times:");
        for (int i = 0; i < THREAD_COUNT; i++) {
            System.out.printf("Thread %d (ID: %d): completed in %d ms%n",
                    i + 1,
                    threads[i].getId(),
                    completionTimes[i]);
        }
    }

    // Метод для очистки консоли (работает в большинстве консолей)
    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}