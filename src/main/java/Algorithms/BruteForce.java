package Algorithms;

import Logging.Logger;
import Places.Customer;
import Places.Place;
import Vehicle.AFV;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class BruteForce implements Callable <List<Place>> {
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
    public List<Place> call() {
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
                if (!v.getFinished()) {
                    allFinished = false;
                    break;
                } else {
                    globalRoute.addAll(v.getRoute());
                    System.out.println(v.getRoute());
                    this.globalTime = globalTime.plusHours(v.getTourTime().getHour()).plusMinutes(v.getTourTime().getMinute());
                }
            }
            if (allFinished){
                System.out.println(this + ": Has finished all routes!");
            } else {
                System.out.println(this + ": Has not finished all routes!");
            }
            this.done = true;
            return globalRoute;
    }

    public LocalTime getGlobal(){
        return this.globalTime;
    }

    public boolean isDone() {
        return done;
    }

    public List<AFV> getVehicles(){
        return this.vehicles;
    }

    private void createVehicles(int amount, List<Place> places){
        for (int i = 0; i <= amount; i++){
            this.vehicles.add(new AFV(i, Place.getDepot(places), Place.getFuelstations(places), places));
        }
    }
}
