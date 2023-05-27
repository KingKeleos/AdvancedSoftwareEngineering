package Algorithms;

import Places.Customer;
import Places.Place;
import Vehicle.AFV;

import java.lang.module.Configuration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BruteForce{
    private final List<AFV> vehicles = new ArrayList<>();
    private final int amount;
    private final List<Place> places;
    private final List<Customer> customers;
    private final List<Customer> assigend = new ArrayList<>();
    private final Random rand = new Random();

    public BruteForce(List<Place> places, List<Customer> customers, int amount){
        this.amount = amount;
        this.places = places;
        this.customers = customers;
    }

    public void start() throws InterruptedException {
        for (int i=0; i<1000000000; i++) {
            createVehicles(this.amount, this.places);
            this.assigend.addAll(this.customers);
            while (this.assigend.size() > 0){
                for (int j = 0; j < amount; j++){
                    if (this.assigend.size() > 0){
                        this.vehicles.get(j).getCustomers().add(this.assigend.remove(this.rand.nextInt(this.assigend.size())));
                    }
                }
            }
            LocalTime globalTime = LocalTime.of(0, 0);
            for (AFV v : vehicles) {
                System.out.println("Created Vehicle " + v.ID + " with Route: " + v.getCustomers());
                v.start();
                v.join();
                globalTime = globalTime.plusHours(v.getTourTime().getHour()).plusMinutes(v.getTourTime().getMinute());
            }
            if (globalTime.equals(Configurations.AFV.maxTime)) {
                System.out.println("Tour finished with global Time: " + globalTime);

            } else {
                System.out.println("Maximum Time was exceeded, doing next route");
                this.vehicles.clear();
            }
        }
    }

    private void createVehicles(int amount, List<Place> places){
        for (int i = 0; i < amount; i++){
            this.vehicles.add(new AFV(i, places, Place.getDepot(places), Place.getFuelstations(places)));
        }
    }
}
