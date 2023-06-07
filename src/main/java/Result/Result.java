package Result;

import Places.Place;
import Vehicle.AFV;

import java.time.LocalTime;
import java.util.List;
import java.util.Locale;

public class Result {
    public LocalTime tourTime;
    public List<AFV> vehicles;

    public Result(LocalTime tourTime, List<AFV> vehicles){
        this.tourTime = tourTime;
        this.vehicles = vehicles;
    }
}
