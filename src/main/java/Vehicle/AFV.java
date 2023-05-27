package Vehicle;

import Places.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.time.LocalTime;

import java.util.List;

public class AFV extends Thread implements Driver {
    public int ID;
    private double tank;
    private final List<Place> placeList;
    private final List<Customer> customers = new ArrayList<>();
    private final List<Fuelstation> fuelstations;
    private Place currentPosition;
    private final List<Place> route = new ArrayList<>();
    private Customer goal;
    private LocalTime tourTime;

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
        Customer next = customers.get(0);
        for (Customer c : customers){
            if (this.currentPosition.getDistance(c) < this.currentPosition.getDistance(next)){
                next = c;
            }
        }
        double distance = this.currentPosition.getDistance(next);
        this.goal = next;
        if (distance  * Configurations.AFV.consumptionPerMile < this.tank){
            this.drive(this.goal);
        }
        else {
            selectFuelstation();
        }
    }

    @Override
    public void run(){
        for (long i = 0; i < 1000000000; i++){
            this.goal = null;
            if (customers.size() > 0){
                this.selectCustomer();
            }
            else {
                System.out.println("Vehicle " + this.ID + " finished the tour in " + this.tourTime + " with Route:" + this.route);
                break;
            }
        }
    }

    public void selectFuelstation(){
        for (Fuelstation f : fuelstations){
            double rest = this.currentPosition.getDistance(f) * Configurations.AFV.consumptionPerMile;
            if (rest < this.tank) {
                if (f.getDistance(goal) * Configurations.AFV.consumptionPerMile < 60.0){
                    this.drive(f);
                    f.Refuel(this);
                    this.route.add(f);
                    this.tourTime = this.tourTime.plusMinutes(15);
                    return;
                }
                else{
                    Place closestGoal = this.placeList.get(0);
                    Place closestCurr = this.placeList.get(0);
                    Place newGoal;
                    for (Place p : placeList){
                        if(route.contains(p)){
                            continue;
                        }
                        if (p.getDistance(goal) < closestGoal.getDistance(goal)){
                            closestGoal = p;
                            if (p.getDistance(currentPosition) < closestCurr.getDistance(currentPosition)){
                                closestCurr = p;
                                newGoal = p;
                            }
                        }
                        this.drive(p);
                    }
                }
            }

        }
    }
}
