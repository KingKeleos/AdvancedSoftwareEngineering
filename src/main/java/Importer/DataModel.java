package Importer;

import Importer.Dataset;
import Importer.Node;
import Algorithms.ACO.AntColonyOptimization;

import java.util.HashMap;

public class DataModel {
    private final HashMap<Integer, Node> references;
    private final double[][] distanceMatrix;
    private final int vehicleNumber;

    public DataModel(Dataset dataset) {
        this.references = AntColonyOptimization.prepareData(dataset.getNodes());
        this.distanceMatrix = AntColonyOptimization.generateDistanceMatrix(references);
        this.vehicleNumber = dataset.getNumVehicles();
    }

    public int getVehicleNumber() {
        return vehicleNumber;
    }

    public double[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public int getDepot() {
        return 0;
    }

    public HashMap<Integer, Node> getReferences() {
        return references;
    }
}
