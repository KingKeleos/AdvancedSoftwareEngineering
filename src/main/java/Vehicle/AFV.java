package Vehicle;

import Places.Customer;
import Places.Depot;
import Places.Fuelstation;
import Places.Place;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AFV implements Driver {
    public int ID;
    private double tank;
    private final List<Place> placeList;
    private final List<Customer> customers = new ArrayList<>();
    private final List<Fuelstation> fuelstations;
    private Place currentPosition;
    private final List<Place> route = new ArrayList<>();
    private Customer goal;
    private LocalTime tourTime;
    private Random rand = new Random();

    public AFV(int ID, List<Place> places, Depot depot, List<Fuelstation> fuelstations){
        this.ID = ID;
        this.currentPosition = depot;
        this.tank = Configurations.AFV.maxVolume;
        // Vehicles can travel through all places
        this.placeList = places;
        // Refuel Time at depot as start time from specification
        this.tourTime = LocalTime.of(0, 15);
        this.fuelstations = fuelstations;
    }

    @Override
    public void consume(double distance) {
        this.tank = this.tank - (distance * Configurations.AFV.consumptionPerMile);
    }

    @Override
    public void drive(Place next) {
        double distance = this.currentPosition.getDistance(goal);
        this.consume(distance);
        this.tourTime = this.tourTime.plusMinutes((long)distance / Configurations.AFV.maxSpeed);
        this.route.add(this.goal);
        this.currentPosition = next;
        this.checkCustomer();
    }

    @Override
    public void refuel() {
        this.tank = Configurations.AFV.maxVolume;
    }

    @Override
    public void checkCustomer() {
        if (this.currentPosition.getClass() == Customer.class){
            this.tourTime = this.tourTime.plusMinutes(30);
            this.customers.remove(this.currentPosition);
        }
    }

    @Override
    public List<Customer> getCustomers() {
        return this.customers;
    }

    @Override
    public void selectCustomer() {
        if (this.goal != null){
            return;
        }
        this.goal = customers.get(rand.nextInt(this.customers.size()));
        double distance = this.currentPosition.getDistance(this.goal);
        if (distance  * Configurations.AFV.consumptionPerMile < this.tank){
            for (Fuelstation f : this.fuelstations){
                double rest = distance  * Configurations.AFV.consumptionPerMile;
                double fuelDistance = this.goal.getDistance(f);
                if (fuelDistance  * Configurations.AFV.consumptionPerMile < (this.tank - rest)){
                    this.drive(this.goal);
                    return;
                }
            }
            selectFuelstation();
        }
    }

    public void run(){
        while (customers.size() > 0){
            this.goal = null;
            this.selectCustomer();
            if(this.tank < this.currentPosition.getDistance(this.goal) * Configurations.AFV.consumptionPerMile){
                selectFuelstation();
            }
        }

    }

    public void selectFuelstation(){
        for (Fuelstation f : fuelstations){
            double rest = this.currentPosition.getDistance(f) * Configurations.AFV.consumptionPerMile;
            if (rest < this.tank) {
                this.drive(f);
                f.Refuel(this);
                this.route.add(f);
                this.tourTime = this.tourTime.plusMinutes(15);
                return;
            }
        }
    }

    public LocalTime getTourTime(){
        return this.tourTime;
    }

    public List<Place> getRoute(){
        return this.route;
    }
}
