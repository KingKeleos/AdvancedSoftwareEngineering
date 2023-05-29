package Places;

import Vehicle.AFV;

public class Fuelstation extends Place {
    public Fuelstation(String ID, double longitude, double latitude) {
        super(ID, longitude, latitude);
    }

    public void Refuel(AFV vehicle){
        vehicle.refuel();
    }
}
