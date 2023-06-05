import Algorithms.BruteForce;
import Importer.Importer;
import Logging.Logger;
import Places.Customer;
import Places.Place;
import Vehicle.AFV;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GreenVehicle {
    private static final Logger logger = new Logger();
    private static Random rand = new Random();

    // Start Point of the project
    public static void main(String[] args) throws InterruptedException, IOException {
        // Initiate start of Project
        List<Place> places = Importer.importFile();
        List<Customer> customers = Place.getCusomers(places);
        final LocalTime[] bestTime = {LocalTime.of(23, 59)};
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < 10000000; i++) {
            executor.submit(() -> {
                BruteForce bruteForce = new BruteForce(places, customers, rand.nextInt(20));
                bruteForce.run();
                LocalTime bruteForceTime = bruteForce.getGlobal();
                List<AFV> bruteForceVehicles = bruteForce.getVehicles();

                synchronized (GreenVehicle.class) {
                    if (bruteForceTime.isBefore(bestTime[0])) {
                        bestTime[0] = bruteForceTime;
                        try {
                            logger.log(bestTime[0], bruteForceVehicles);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }
}