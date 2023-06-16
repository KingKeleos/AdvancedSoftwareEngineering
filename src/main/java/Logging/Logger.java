package Logging;

import Places.Place;
import Vehicle.AFV;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class Logger {

    public void log(LocalTime time, List<AFV> vehicles) throws IOException {
        StringBuilder str = new StringBuilder("{Route with time: " + time);
        long milesGlobal = 0;
        for (AFV v : vehicles){
            str.append("\n\t {and route for vehicle ").append(v.ID).append(" with route [D");
            for (Place r : v.getRoute()){
                str.append(" -> ").append(r.getID());
            }
            str.append("], driven : ");
            str.append(v.getTourLength());
            str.append(" Miles}");
            milesGlobal = milesGlobal + v.getTourLength();
        }
        str.append("\nThis iteration drove: ").append(milesGlobal).append(" Miles in general}");
        String location = "data/result.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(location, true));
        writer.append(str);
        writer.newLine();

        writer.close();
    }

}
