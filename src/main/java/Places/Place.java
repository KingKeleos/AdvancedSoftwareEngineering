package Places;

import java.util.*;

public abstract class Place  implements PlaceInterface{
    private final String ID;
    private final double longitude;
    private final double latitude;

    public Place(String ID, double longitude, double latitude){
        this.ID = ID;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getID(){
        return this.ID;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public double getLatitude(){
        return this.latitude;
    }

    @Override
    public double getDistance(Place place) {
        double distanceA = this.getLongitude()-place.getLongitude();
        double distanceB = this.getLatitude()-place.getLatitude();
        return Math.sqrt(Math.pow(distanceA, 2.0)+Math.pow(distanceB, 2.0));
    }

    public static List<Customer> getCusomers(List<Place> places){
        List <Customer> customers = new ArrayList<>();
        for (Place p : places){
            if (p.getClass() == Customer.class){
                customers.add((Customer) p);
            }
        }
        return customers;
    }

    public static Depot getDepot(List<Place> places){
        for (Place p : places){
            if (p.getClass() == Depot.class){
                return (Depot) p;
            }
        }
        return null;
    }

    public static List<Fuelstation> getFuelstations(List<Place> places){
        List <Fuelstation> fuelstations = new ArrayList<>();
        for (Place p : places){
            if (p.getClass() == Fuelstation.class){
                fuelstations.add((Fuelstation) p);
            }
        }
        return fuelstations;
    }
}
