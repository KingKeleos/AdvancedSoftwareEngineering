package BruteForce;

import java.util.concurrent.atomic.AtomicInteger;

public class GreenVehicleBruteForce {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final int TOTAL_ITERATIONS = 1000000000;

    private static final AtomicInteger counter = new AtomicInteger(0);
    private static final Object lock = new Object();

    public static void main(String[] args) {
        Thread[] threads = new Thread[NUM_THREADS];

        // Start threads
        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new Thread(new BruteForceWorker());
            threads[i].start();
        }

        // Wait for threads to finish
        for (int i = 0; i < NUM_THREADS; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Print the final count
        System.out.println("Total count: " + counter.get());
    }

    static class BruteForceWorker implements Runnable {
        @Override
        public void run() {
            int count = 0;

            for (int i = 0; i < TOTAL_ITERATIONS; i++) {
                // Perform the brute-force calculation here
                // ...

                count++;

                // Increment the shared counter atomically
                synchronized (lock) {
                    counter.incrementAndGet();
                }
            }

            System.out.println("Thread finished. Count: " + count);
        }
    }
}