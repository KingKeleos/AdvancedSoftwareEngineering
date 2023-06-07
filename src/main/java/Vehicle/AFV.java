package Vehicle;

import Places.Customer;
import Places.Depot;
import Places.Fuelstation;
import Places.Place;

import java.lang.module.Configuration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AFV {
    public int ID;
    private double tank;
    private final Depot depot;
    private final List<Customer> customers = new ArrayList<>();
    private final List<Place> places;
    private Place currentPosition;
    private final List<Place> route = new ArrayList<>();
    private LocalTime tourTime;
    private final Random rand = new Random();
    private Boolean finished = false;
    private long tourLength = 0;

    public AFV(int ID, Depot depot, List<Place> places){
        this.ID = ID;
        this.depot = depot;
        this.tank = Configurations.AFV.maxVolume;
        // Refuel Time at depot as start time from specification
        this.tourTime = LocalTime.of(0, 15);
        this.currentPosition = this.depot;
        this.places = places;
    }
    public List<Customer> getCustomers() {
        return this.customers;
    }


    public Boolean getFinished() {
        return finished;
    }

    public void run(){
        this.route.add(this.depot);
        int i = 0;
        while (this.tourTime.isBefore(LocalTime.of(10,45))){
            System.out.println("Vehicle " + this.ID + " driving route: " + this.route);
            if (customers.size() == 0 && currentPosition.getClass() == Depot.class){
                this.finished = true;
                return;
            }

            Place goal = places.get(rand.nextInt(this.places.size()));
            double distance = this.currentPosition.getDistance(goal);

            this.tank -= distance * Configurations.AFV.consumptionPerMile;
            if (this.tank < 0){
                return;
            }

            double hours = distance / Configurations.AFV.maxSpeed;

            this.tourTime = this.tourTime.plusHours((long)(hours));
            this.tourLength += (long)distance;
            this.currentPosition = goal;
            this.route.add(this.currentPosition);

            if (this.currentPosition.getClass() == Customer.class){
                this.tourTime = this.tourTime.plusMinutes(30);
                if (customers.contains(this.currentPosition)){
                    this.customers.remove(this.currentPosition);
                }
            } else if (this.currentPosition.getClass() == Fuelstation.class){
                this.tourTime = this.tourTime.plusMinutes(15);
                this.tank = Configurations.AFV.maxVolume;
            }
        }
    }

    public LocalTime getTourTime(){
        return this.tourTime;
    }

    public long getTourLength(){
        return tourLength;
    }

    public List<Place> getRoute(){
        return this.route;
    }
}
