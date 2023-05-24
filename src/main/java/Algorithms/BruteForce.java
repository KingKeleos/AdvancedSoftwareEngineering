package Algorithms;

import Places.Customer;
import Places.Place;
import Vehicle.AFV;

import java.util.ArrayList;
import java.util.List;

public class BruteForce {
    private List<AFV> vehicles = new ArrayList<>();
    private List<Place> places;

    public BruteForce(List<Place> places, List<Customer> customers, int amount){
        this.places = places;
        createVehicles(amount, places);

        while (customers.size() > 0){
            for (int j = 0; j < amount; j++){
                if (customers.size() > 0){
                    vehicles.get(j).getCustomers().add(customers.remove(0));
                }
            }
        }
    }

    public void start(){

    }

    private void createVehicles(int amount, List<Place> places){
        for (int i = 0; i < amount; i++){
            this.vehicles.add(new AFV(places, Place.getDepot(places)));
        }
    }
}
