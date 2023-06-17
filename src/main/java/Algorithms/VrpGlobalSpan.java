package Algorithms;

import Importer.DataModel;
import com.google.ortools.constraintsolver.Assignment;
import com.google.ortools.constraintsolver.RoutingIndexManager;
import com.google.ortools.constraintsolver.RoutingModel;

import java.util.logging.Logger;

public class VrpGlobalSpan {
    private static final Logger logger = Logger.getLogger(VrpGlobalSpan.class.getName());

    // print the solution of thr vrp
    // datamodel holds the distancematrix, vehiclecount and depot index
    // routingmodel contains information about nodes, edges, costs and restrictions
    // routingindexmanager manages the realtions of nodes and indices
    // assignment saves the result of the vrp
    public static void printSolution(DataModel data, RoutingModel routing, RoutingIndexManager manager, Assignment solution) {
        // logging the solution cost of the route
        logger.info("Objective : " + solution.objectiveValue());
        // Inspect solution
        long maxRouteDistance = 0;

        // for every vehicle the corresponding subroute will be printed including its cost
        for (int i = 0; i < data.getVehicleNumber(); ++i) {
            // print vehicle number and initialize route and routeDistance
            long index = routing.start(i);
            logger.info("Route for Vehicle " + i + ":");
            long routeDistance = 0;
            StringBuilder route = new StringBuilder();

            // while there are remaining nodes in a route, convert them to node ids, print them and add
            // the cost of the edge change index to next node, rinse and repeat
            while (!routing.isEnd(index)) {
                route.append(data.getReferences().get(manager.indexToNode(index)).getId()).append(" -> ");
                long previousIndex = index;
                index = solution.value(routing.nextVar(index));
                routeDistance += routing.getArcCostForVehicle(previousIndex, index, i);
            }

            // print start node to complete route and print the total cost of the route
            logger.info(route + data.getReferences().get(manager.indexToNode(index)).getId());
            logger.info("Distance of the route: " + routeDistance + "m");

            // compare cost of new route to current maximum length route and set max length route if new one is bigger
            maxRouteDistance = Math.max(routeDistance, maxRouteDistance);
        }
        // print max length route
        logger.info("Maximum of the route distances: " + maxRouteDistance + "m");
    }
}