package Vehicle;

import Places.Customer;
import Places.Depot;
import Places.Fuelstation;
import Places.Place;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AFV extends Thread implements Driver {
    public int ID;
    private double tank;
    private final List<Customer> customers = new ArrayList<>();
    private final List<Fuelstation> fuelstations;
    private Place currentPosition;
    private final List<Place> route = new ArrayList<>();
    private Customer goal;
    private LocalTime tourTime;
    private Random rand = new Random();
    private Boolean finished = false;

    public AFV(int ID, Depot depot, List<Fuelstation> fuelstations){
        this.ID = ID;
        this.currentPosition = depot;
        this.tank = Configurations.AFV.maxVolume;
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
        this.currentPosition = next;
        this.route.add(this.currentPosition);
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
        if (this.tourTime.isAfter(LocalTime.of(10,45))){
            return;
        }
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

    public Boolean getFinished() {
        return finished;
    }

    @Override
    public void run(){
        while (customers.size() > 0 && this.tourTime.isBefore(LocalTime.of(10,45))){
            this.goal = null;
            this.selectCustomer();
            if(this.tank < this.currentPosition.getDistance(this.goal) * Configurations.AFV.consumptionPerMile){
                selectFuelstation();
            }
            this.checkCustomer();
        }
        if (this.tourTime.isAfter(LocalTime.of(10,45))){
            return;
        }
        this.finished = true;
    }

    public void selectFuelstation(){
        if (this.tourTime.isAfter(LocalTime.of(10,45))){
            return;
        }
        for (Fuelstation f : fuelstations){
            double rest = this.currentPosition.getDistance(f) * Configurations.AFV.consumptionPerMile;
            if (rest < this.tank) {
                this.drive(f);
                f.Refuel(this);
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
