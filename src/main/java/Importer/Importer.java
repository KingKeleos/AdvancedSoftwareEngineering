package Importer;

import Places.*;

import java.io.*;
import java.util.*;

public class Importer {
    public static List<Place> importFile() {
        List<Place> pl = new ArrayList<Place>();
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

    public static Depot getDepot(List<Place> places){
        for (Place p : places){
            if (p.getClass() == Depot.class){
                return (Depot) p;
            }
        }
        System.out.println("Couldn't find a Depot!");
        return null;
    }
}
