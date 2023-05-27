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
        double lat1 = place.getLatitude();
        double lon2 = place.getLongitude();
        double radiusOfEarth = 3959; // miles, 6371km;
        double dLat = Math.toRadians(this.latitude-lat1);
        double dLon = Math.toRadians(lon2-this.longitude);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(this.latitude)) * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return radiusOfEarth * c;
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
