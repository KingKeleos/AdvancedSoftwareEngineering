package Algorithms.ACO;

import Importer.Dataset;
import Route.Route;

import java.util.ArrayList;

public abstract class AcoExecutor {
    protected ArrayList<Route> execute(Dataset dataset) {
        return null;
    }

    protected Dataset getNewDatasetInstance(Dataset dataset) {
        Dataset ds = new Dataset();
        ds.setTIME_SPENT_AT_CUSTOMER(dataset.getTIME_SPENT_AT_CUSTOMER());
        ds.setTIME_SPENT_AT_GASSTATION(dataset.getTIME_SPENT_AT_GASSTATION());
        ds.setNodes(dataset.getNodes());
        ds.setDepot(dataset.getDepot());
        ds.setCustomerList(dataset.getCustomerList());
        ds.setGasStationList(dataset.getGasStationList());
        ds.setFuelCapacity(dataset.getFuelCapacity());
        ds.setFuelConsumptionRate(dataset.getFuelConsumptionRate());
        ds.setTourLength(dataset.getTourLength());
        ds.setAvgVelocity(dataset.getAvgVelocity());
        ds.setNumVehicles(dataset.getNumVehicles());
        return ds;
    }
}
