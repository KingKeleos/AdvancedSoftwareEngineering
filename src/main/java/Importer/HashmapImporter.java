package Importer;

import Places.*;

import java.io.*;
import java.util.*;
public class HashmapImporter {
    public static Map<String, Place> importFile() {
        Map<String, Place> placeMap = new HashMap<>();
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
                    case "d" -> placeMap.put(id, new Depot(id, longitude, latitude));
                    case "c" -> placeMap.put(id, new Customer(id, longitude, latitude));
                    case "f" -> placeMap.put(id, new Fuelstation(id, longitude, latitude));
                    default -> System.out.println("No Place Type found for: " + type);
                }
            }
        } catch (Exception e) {
            System.out.println("URI-Problem");
            e.printStackTrace();
        }
        return placeMap;
    }

    public static Depot getDepot(Map<String, Place> placeMap) {
        for (Place p : placeMap.values()) {
            if (p instanceof Depot) {
                return (Depot) p;
            }
        }
        System.out.println("Couldn't find a Depot!");
        return null;
    }
}
