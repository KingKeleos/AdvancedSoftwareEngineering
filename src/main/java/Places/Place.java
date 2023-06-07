package Places;

import java.util.*;

public abstract class Place{
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

    public double getDistance(Place place1) {
        double lat1 = place1.getLatitude();
        double lon1 = place1.getLongitude();
        double radiusOfEarth = 3959; // miles, 6371km;
        double dLat = Math.toRadians(lat1-this.latitude);
        double dLon = Math.toRadians(lon1-this.longitude);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(Math.toRadians(this.longitude)) * Math.cos(Math.toRadians(lat1)) * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return radiusOfEarth * c;
    }
}
