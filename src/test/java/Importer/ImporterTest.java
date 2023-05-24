package Importer;

import Places.Place;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ImporterTest {
    @Test
    void testImport(){
        //Execute Testing Method
        List<Place> placeList = Importer.importFile();

        //placesList should not be Null
        assertNotNull(placeList);
        //placesList should have the 31 places in the file
        assertEquals(31, placeList.size());
        //placesList[1] should have all data set
        assertNotNull(placeList.get(0).getID());
        assertNotEquals(0.0, placeList.get(0).getLatitude());
        assertNotEquals(0.0, placeList.get(0).getLongitude());
    }
}
