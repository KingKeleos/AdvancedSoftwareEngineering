package Algorithms;

import Logging.Logger;
import Places.Customer;
import Places.Place;
import Result.Result;
import Vehicle.AFV;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class BruteForce implements Callable <Result> {
    private final List<AFV> vehicles = new ArrayList<>();
    private final int amount;
    private final List<Place> places;
    private final List<Customer> customers;
    private final List<Customer> assigned = new ArrayList<>();
    private final Random rand = new Random();
    private boolean done = false;
    private LocalTime globalTime = LocalTime.of(0, 0);

    public BruteForce(List<Place> places, List<Customer> customers, int amount){
        this.amount = amount;
        this.places = places;
        this.customers = customers;
    }

    @Override
    public Result call() {
            createVehicles(this.amount, this.places);
            List<Place> globalRoute = new ArrayList<>();
            this.assigned.addAll(this.customers);
            while (this.assigned.size() > 0 && this.customers.size() > 0) {
            for (int j = 0; j <= amount; j++) {
                if (this.assigned.size() > 0 && this.customers.size() > 0) {
                    this.vehicles.get(j).getCustomers().add(this.assigned.remove(this.rand.nextInt(this.assigned.size())));
                    }
                }
            }
            LocalTime globalTime = LocalTime.of(0, 0);
            boolean allFinished = true;
            for (AFV v : vehicles) {
                v.run();
                globalRoute.addAll(v.getRoute());
                this.globalTime = globalTime.plusHours(v.getTourTime().getHour()).plusMinutes(v.getTourTime().getMinute());
            }
            this.done = true;
            return new Result(globalTime, vehicles);
    }

    private void createVehicles(int amount, List<Place> places){
        for (int i = 0; i <= amount; i++){
            this.vehicles.add(new AFV(i, Place.getDepot(places), Place.getFuelstations(places), places));
        }
    }
}
