import Algorithms.BruteForce;
import Importer.Importer;
import Places.Customer;
import Places.Place;

import java.io.IOException;
import java.util.List;

public class GreenVehicle {

    //Start Point of the project
    public static void main(String[] args) throws InterruptedException, IOException {
        // Initiate start of Project
        List<Place> places = Importer.importFile();
        List<Customer> customers = Place.getCusomers(places);

        // Start the Algorithms:
        for (int i=0; i<1000000000; i++) {
            BruteForce bruteForce = new BruteForce(places, customers, 3);
            bruteForce.start();
        }
    }
}
