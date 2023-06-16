import Route.Route;
import Algorithms.ACO.SingleThreadedAcoExecutor;

import java.util.ArrayList;
import Importer.DatasetReader;
import Importer.Dataset;



public class AntAlgo {


    public static void main(String[] args) {

        // dataset initialisieren
        DatasetReader datasetReader = new DatasetReader();
        String file = "Large_VA_Input_111c_22s.txt";//"S1_20c3sU1.txt";
        Dataset dataset = datasetReader.createDataset(file);
        // aco params
        SingleThreadedAcoExecutor aco = new SingleThreadedAcoExecutor();




                        System.out.println("\nExecuting ACO, this may take a while...\n");
                        ArrayList<Route> route = aco.execute(dataset);
                        if (route != null) {
                            double totalTravelTime = 0.0;
                            for (Route r : route) {
                                totalTravelTime += r.getTravelTime();
                                System.out.println(r);
                            }
                            System.out.println("Total Time: " + totalTravelTime + " h\n");
                        } else
                            System.out.println("Sorry the ants are currently out of office. Please try again later");
                    }


    }

