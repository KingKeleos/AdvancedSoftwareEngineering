import Importer.Importer;
import Places.Places;

import java.util.List;

public class GreenVehicle {
    static List<Places> places;

    //Start Point of the project
    public static void main(String[] args) {
        places = Importer.importFile();
    }
}
