package Vehicle;

import Places.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.time.LocalTime;

import java.util.List;

public class AFV extends Thread implements Driver {
    private double tank;
    private final List<Place> placeList;
    private final List<Customer> customers = new ArrayList<>();
    private final List<Fuelstation> fuelstations;
    private Place currentPosition;
    private final Random rand = new Random();
    private final List<Place> route = new ArrayList<>();
    private Customer goal;
    private LocalTime tourTime;

    public AFV(List<Place> places, Depot depot, List<Fuelstation> fuelstations){
        this.currentPosition = depot;
        this.tank = Configurations.AFV.maxVolume;
        this.placeList = places;
        this.tourTime = LocalTime.of(0, 0);
        this.fuelstations = fuelstations;
    }

    @Override
    public void consume(double distance) {
        this.tank = this.tank - (distance * Configurations.AFV.consumptionPerMile);
        System.out.println("Vehicle " + this + " has " +this.tank+ " left");
    }

    @Override
    public void drive(Place next) {
        double distance = this.currentPosition.getDistance(goal);
        this.consume(distance);
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
        Customer next = this.customers.get(rand.nextInt(customers.size()));
        double distance = this.currentPosition.getDistance(next);
        if (distance  * Configurations.AFV.consumptionPerMile < this.tank){
            this.goal = next;
            System.out.println("Vehicle " +this+ " chose " +this.goal);
            this.drive(this.goal);
        }
    }

    @Override
    public void run(){
        for (long i = 0; i < 1000000000; i++){
            this.goal = null;
            if (customers.size() > 0){
                if (goal == null){
                    this.selectCustomer();
                    if (this.goal == null){
                        this.selectFuelstation();
                    }
                }
                System.out.println(i+" Vehicle tour: " + this.route);
            }
            else {
                System.out.println("Vehicle" + this + "finished the tour in " + this.tourTime);
                break;
            }
        }
    }

    public void selectFuelstation(){
        for (Fuelstation f : fuelstations){
            if (this.route.contains(f)){
                break;
            }
            if (this.currentPosition.getDistance(f) * Configurations.AFV.consumptionPerMile < this.tank){
                this.drive(f);
                f.Refuel(this);
                this.route.add(f);
                return;
            }
        }
    }
}
