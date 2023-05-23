package Importer;

import Places.Places;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ImporterTest {
    @Test
    void testImport(){
        //Execute Testing Method
        List<Places> placesList = Importer.importFile();

        //placesList should not be Null
        assertNotNull(placesList);
        //placesList should have the 31 places in the file
        assertEquals(31, placesList.size());
        //placesList[1] should have all data set
        assertNotNull(placesList.get(0).getID());
        assertNotEquals(0.0, placesList.get(0).getLatitude());
        assertNotEquals(0.0, placesList.get(0).getLongitude());
        assertNotNull(placesList.get(0).getType());
    }
}
