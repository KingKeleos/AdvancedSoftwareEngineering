import Logging.Logger;
import java.util.Random;
import java.util.concurrent.*;
import BruteForce.Executor;

public class GreenVehicle {
    private static final Logger logger = new Logger();
    private static Random rand = new Random();

    // Start Point of the project
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Initiate start of Project
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < 10000; i++) {
            System.out.println(i);
            executor.submit(() -> {
                Executor executor1 = new Executor();
                executor1.run();
            });
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }
}