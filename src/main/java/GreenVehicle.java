import Algorithms.BruteForce;
import Configurations.Places;
import Places.Customer;
import Places.Depot;
import Vehicle.AFV;
import Importer.Importer;
import Places.Place;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class GreenVehicle {

    //Start Point of the project
    public static void main(String[] args) {
        // Initiate start of Project
        List<Place> places = Importer.importFile();
        List<Customer> customers = Place.getCusomers(places);

        // Start the Algorithms:
        BruteForce bruteForce = new BruteForce(places, customers, 4);
        bruteForce.start();
    }
}
