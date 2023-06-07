package BruteForce;

import Places.Customer;
import Places.Depot;
import Places.Fuelstation;
import Places.Place;
import Vehicle.AFV;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Executor implements Runnable {
    private List<Place> places;
    private List<Customer> customers;
    private int amount;

    public Executor(){
        Random rand = new Random();
        this.places = importFile();
        this.customers = Place.getCusomers(this.places);
        this.amount = rand.nextInt(customers.size());
    }

    @Override
    public void run(){
        Random rand = new Random();
        List<AFV> vehicles = createVehicles(this.amount, this.places);
        List<Customer> assigned = this.customers;
        while (assigned.size() > 0) {
            for (int j = 0; j < amount; j++) {
                if (assigned.size() > 0) {
                    vehicles.get(j).getCustomers().add(assigned.remove(rand.nextInt(assigned.size())));
                }
            }
        };
        System.out.println(vehicles);
    }


    private List<AFV> createVehicles(int amount, List<Place> places){
        List<AFV> vehicles = new ArrayList<>();
        for (int i = 0; i <= amount; i++){
            vehicles.add(new AFV(i, Place.getDepot(places), places));
        }
        return vehicles;
    }

    public static List<Place> importFile() {
        List<Place> pl = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("data/dataset.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");

                String id = values[0];
                String type = values[1];
                double longitude = Double.parseDouble(values[2]);
                double latitude = Double.parseDouble(values[3]);

                switch (type) {
                    case "d" -> pl.add(new Depot(id, longitude, latitude));
                    case "c" -> pl.add(new Customer(id, longitude, latitude));
                    case "f" -> pl.add(new Fuelstation(id, longitude, latitude));
                    default -> System.out.println("No Place Type found for :" + type);
                }

            }
        } catch (Exception e) {
            System.out.println("URI-Problem");
            e.printStackTrace();
        }
        return pl;
    }

}
