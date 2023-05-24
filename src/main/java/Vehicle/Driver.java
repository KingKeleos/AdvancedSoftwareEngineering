package Vehicle;

import Places.Place;

public interface Driver {
    void consume(double distance);
    void drive(Place goal);
    void refuel();
    void checkCustomer();
}
