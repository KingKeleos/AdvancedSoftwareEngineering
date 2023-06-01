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
        StringBuilder str = new StringBuilder("Route with time: " + time);
        for (AFV v : vehicles){
            str.append("\n\t and route for vehicle ").append(v.ID).append(" with route [D");
            for (Place r : v.getRoute()){
                str.append(" -> " + r.getID());
            }
            str.append("]");
        }
        String location = "out/out.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(location, true));
        writer.append(str);
        writer.newLine();

        writer.close();
    }

}
