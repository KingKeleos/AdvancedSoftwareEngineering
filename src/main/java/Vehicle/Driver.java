package Vehicle;

import Places.Customer;
import Places.Place;

import java.util.List;

public interface Driver {
    void consume(double distance);
    void drive(Place goal);
    void refuel();
    void checkCustomer();
    List<Customer> getCustomers();
    void selectCustomer();
}
