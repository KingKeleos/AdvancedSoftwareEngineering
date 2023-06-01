import Algorithms.BruteForce;
import Importer.Importer;
import Logging.Logger;
import Places.Customer;
import Places.Place;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class GreenVehicle {
    private static final Logger logger = new Logger();

    //Start Point of the project
    public static void main(String[] args) throws InterruptedException, IOException {
        // Initiate start of Project
        List<Place> places = Importer.importFile();
        List<Customer> customers = Place.getCusomers(places);
        LocalTime bestTime = LocalTime.of(23, 59);

        // Start the Algorithms:
        for (int i=0; i<10000000; i++) {
            BruteForce bruteForce = new BruteForce(places, customers, 5);
            bruteForce.start();
            bruteForce.join();
            if (bruteForce.getGlobal().isBefore(bestTime)){
                bestTime = bruteForce.getGlobal();
                logger.log(bestTime, bruteForce.getVehicles());
            }
        }
    }
}
