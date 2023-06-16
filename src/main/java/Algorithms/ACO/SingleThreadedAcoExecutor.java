package Algorithms.ACO;

import Importer.Dataset;
import Importer.Node;
import Route.Route;
import Route.SubRoute;

import java.util.ArrayList;

public class SingleThreadedAcoExecutor extends AcoExecutor {
    @Override
    public ArrayList<Route> execute(Dataset dataset) {
        // prepare data
        // any number of vehicles allowed? say less!
        int vehicle = dataset.getNumVehicles() * 3;
        ArrayList<Route> routes = null;
        double totalTravelTime = 0.0;
        boolean validTour = false;
        int count = 0;

        // generate new subroutes and run the aco till a result is valid
        while (!validTour && count < 100) {
            // prepare data
            Dataset ds = getNewDatasetInstance(dataset);
            routes = new ArrayList<>();
            boolean isValid = true;
            count++;

            // create subroutes
            ArrayList<ArrayList<Node>> subRoutes = SubRoute.generateSubRoutes(
                    ds.getDepot(),
                    ds.getCustomerList(),
                    vehicle
            );

            // Search for subroutes
            for (int i = 0; i < vehicle; i++) {
                if (subRoutes.get(i).size() <= 1) {
                    totalTravelTime = 0.0;
//                    isValid = false;
                    continue;
                }
                ds.setCustomerList(subRoutes.get(i));
                AntColonyOptimization aco = new AntColonyOptimization(ds);

                Route route = aco.run();

                if (route == null) {
                    break;
                }
                routes.add(route);

                totalTravelTime = totalTravelTime + route.getTravelTime();

                isValid = isValid && (route.getTravelTime() <= Configuration.INSTANCE.MAX_TOUR_LENGTH);
                if (route.getTravelTime() > Configuration.INSTANCE.MAX_TOUR_LENGTH) {
                    break;
                }
            }

            if (isValid) {
                validTour = true;
            } else {
                totalTravelTime = 0.0;
            }
        }
        return routes;
    }
}
