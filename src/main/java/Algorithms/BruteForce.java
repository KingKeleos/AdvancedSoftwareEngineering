package Algorithms;

import Logging.Logger;
import Places.Customer;
import Places.Place;
import Vehicle.AFV;

import java.io.IOException;
import java.lang.module.Configuration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BruteForce extends Thread{
    private final List<AFV> vehicles = new ArrayList<>();
    private final int amount;
    private final List<Place> places;
    private final List<Customer> customers;
    private final List<Customer> assigned = new ArrayList<>();
    private final Random rand = new Random();
    private final Logger logger;
    private LocalTime bestTime = LocalTime.of(23,59);

    public BruteForce(List<Place> places, List<Customer> customers, int amount){
        this.amount = amount;
        this.places = places;
        this.customers = customers;

        this.logger = new Logger();
    }

    @Override
    public void run() {
        for (int i=0; i<1000000000; i++) {
            createVehicles(this.amount, this.places);
            this.assigned.addAll(this.customers);
            while (this.assigned.size() > 0) {
                for (int j = 0; j < amount; j++) {
                    if (this.assigned.size() > 0) {
                        this.vehicles.get(j).getCustomers().add(this.assigned.remove(this.rand.nextInt(this.assigned.size())));
                    }
                }
            }
            LocalTime globalTime = LocalTime.of(0, 0);
            for (AFV v : vehicles) {
                v.start();
                try {
                    v.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                globalTime = globalTime.plusHours(v.getTourTime().getHour()).plusMinutes(v.getTourTime().getMinute());
            }
            if (globalTime.isBefore(this.bestTime) && globalTime.isBefore(Configurations.AFV.maxTime)) {
                this.bestTime = globalTime;
                try {
                    logger.log(globalTime, vehicles);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            this.vehicles.clear();
        }
    }

    private void createVehicles(int amount, List<Place> places){
        for (int i = 0; i < amount; i++){
            this.vehicles.add(new AFV(i, places, Place.getDepot(places), Place.getFuelstations(places)));
        }
    }
}
