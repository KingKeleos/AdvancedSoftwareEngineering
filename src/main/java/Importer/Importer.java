package Importer;

import Places.Places;

import java.io.*;
import java.util.*;

public class Importer {
    public static List<Places> importFile() {
        List<Places> pl = new ArrayList<Places>();
        try (BufferedReader br = new BufferedReader(new FileReader("data/dataset.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");

                String id = values[0];
                String type = values[1];
                double longitude = Double.parseDouble(values[2]);
                double latitude = Double.parseDouble(values[3]);

                pl.add(new Places(id, longitude, latitude, type));
            }
        } catch (Exception e) {
            System.out.println("URI-Problem");
            e.printStackTrace();
        }
        return pl;
    }
}
