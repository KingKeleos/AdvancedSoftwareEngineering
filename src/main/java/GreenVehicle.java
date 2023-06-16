import Algorithms.BruteForce;
import Importer.Importer;
import Logging.Logger;
import Places.Customer;
import Places.Place;
import Result.Result;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class GreenVehicle {
    private static final Logger logger = new Logger();
    private static Random rand = new Random();

    // Start Point of the project
    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        // Initiate start of Project
        List<Place> places = Importer.importFile();
        List<Customer> customers = Place.getCusomers(places);
        final LocalTime[] bestTime = {LocalTime.of(10, 45)};
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (long i = 0; i < 1000000000L; i++) {
            BruteForce bruteForce = new BruteForce(places, customers, rand.nextInt(20));
            Future<Result> future = executor.submit(bruteForce);

            Result result = future.get();
            System.out.println("Thread: " + i + " finished");
            if (result.tourTime.isBefore(bestTime[0]) && result.tourTime != LocalTime.of(0,0)){
                logger.log(result.tourTime, result.vehicles);
            }
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }
}