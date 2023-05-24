package Vehicle;

import Places.*;

import java.util.ArrayList;
import java.util.Random;
import java.time.LocalTime;

import java.util.List;

public class AFV implements Driver{
    private double tank;
    private List<Place> placeList;
    private List<Customer> customers = new ArrayList<>();
    private Place currentPosition;
    private Random rand = new Random();
    private LocalTime tourTime;

    public AFV(List<Place> places, Depot depot){
        this.currentPosition = depot;
        this.tank = Configurations.AFV.maxVolume;
        this.placeList = places;
        this.tourTime = LocalTime.of(0, 0);
    }

    @Override
    public void consume(double distance) {
        this.tank = this.tank - distance * Configurations.AFV.consumptionPerMile;
    }

    @Override
    public void drive(Place goal) {
        this.currentPosition = goal;
        this.consume(this.currentPosition.getDistance(goal));
    }

    @Override
    public void refuel() {
        this.tank = Configurations.AFV.maxVolume;
    }

    @Override
    public void checkCustomer() {
        if (this.currentPosition.getClass() == Customer.class){
            this.placeList.remove(this.currentPosition);
        }
    }

    @Override
    public List<Customer> getCustomers() {
        return this.customers;
    }

    @Override
    public void selectCustomer() {
        while (customers.size() >0){
            Customer goal = this.customers.get(rand.nextInt());
            if (this.currentPosition.getDistance(goal) < this.tank * Configurations.AFV.consumptionPerMile){
                this.drive(goal);
                this.selectCustomer();
            }
            else selectCustomer();
        }
        System.out.println("Finished the tour");
    }

    public void selectFuelstation(){

    }

    public List<Place> getPlaceList(){
        return this.placeList;
    }
}
