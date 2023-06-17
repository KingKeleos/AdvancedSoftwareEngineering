import Algorithms.VrpGlobalSpan;
import Importer.DataModel;
import Importer.Dataset;
import Importer.DatasetReader;
import com.google.ortools.Loader;
import com.google.ortools.constraintsolver.*;


public class GoogleORTools {
    public static void main(String[] args) {

        // dataset initialisieren
        DatasetReader datasetReader = new DatasetReader();
        String file = "Large_VA_Input_111c_22s.txt";//"S1_20c3sU1.txt";
        Dataset dataset = datasetReader.createDataset(file);

        // google or-tools params
        Loader.loadNativeLibraries();

        DataModel dataModel = new DataModel(dataset);

        // Erstellen des Route Managers mit der Datenmatrix, der Anzahl der Fahrzeuge
        // und dem Depot aus dem Datenmodell
        RoutingIndexManager manager = new RoutingIndexManager(
                dataModel.getDistanceMatrix().length,
                dataModel.getVehicleNumber(),
                dataModel.getDepot()
        );

        // Routing Model erstellen mit dem gerade initialisierten Manager
        RoutingModel routing = new RoutingModel(manager);

        // Die Callback Funktion wird erstellt
        // Die Routing Variable wird zum UserNodeIndex umgewandelt
        final int transitCallbackIndex =
                routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                    int fromNode = manager.indexToNode(fromIndex);
                    int toNode = manager.indexToNode(toIndex);
                    return (long) dataModel.getDistanceMatrix()[fromNode][toNode];
                });

        // Die Kosten jeder Kante wird ermittelt
        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

        // Die Distanzbeschränkung wird gesetzt
        routing.addDimension(transitCallbackIndex, 0, 3000,
                true,
                "Distance");
        RoutingDimension distanceDimension = routing.getMutableDimension("Distance");
        distanceDimension.setGlobalSpanCostCoefficient(100);

        // Die Parameter werden zunächst heuristisch gesetzt
        RoutingSearchParameters searchParameters =
                main.defaultRoutingSearchParameters()
                        .toBuilder()
                        .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
                        .build();

        // Aufruf der eigentlichen Lösungsfunktion mit den gesetzten Parametern
        Assignment solution = routing.solveWithParameters(searchParameters);
        VrpGlobalSpan.printSolution(dataModel, routing, manager, solution);
    }
}
