package Vehicle;

import Places.*;

import java.util.List;

public class AFV implements Driver{
    private double tank;
    private List<Place> placeList;
    private Place currentPosition;

    public AFV(List<Place> places, Depot depot){
        this.currentPosition = depot;
        this.tank = Configurations.AFV.maxVolume;
        this.placeList = places;
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
        this.placeList.remove(this.currentPosition);
    }
}
