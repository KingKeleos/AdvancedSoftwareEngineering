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
        return Math.sqrt((this.getLongitude()-place.getLongitude())+(this.getLatitude()-place.getLatitude()));
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
}
