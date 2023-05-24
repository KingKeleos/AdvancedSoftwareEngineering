import Importer.Importer;
import Places.Place;

import java.util.List;

public class GreenVehicle {
    static List<Place> places;

    //Start Point of the project
    public static void main(String[] args) {
        places = Importer.importFile();
    }
}
