package Route;

import Configurations.ACOConfiguration;
import Importer.Node;

import java.util.ArrayList;

public class SubRoute {
    public static ArrayList<ArrayList<Node>> generateSubRoutes(Node depot, ArrayList<Node> nodes, int count) {
        double anglePerSection = 360.0 / count;
        double rotationOffset = ACOConfiguration.INSTANCE.randomGenerator.nextDouble() * 360;

        // initialize arraylist of arraylists of subroutes
        ArrayList<ArrayList<Node>> subRoutes = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ArrayList<Node> subRoute = new ArrayList<>();
            subRoute.add(depot);
            subRoutes.add(subRoute);
        }

        for (Node node : nodes) {
            // skip depot
            if (node == depot)
                continue;

            // calculate angle between depot and current node
            double angle = Math.toDegrees(
                    Math.atan2(
                            node.getLatitude() - depot.getLatitude(),
                            node.getLongitude() - depot.getLongitude()
                    )
            );

            // make sure that the angle is always between 0 and 360 even after adding the offset
            if (angle < 0)
                angle += 360;

            angle += rotationOffset;

            if (angle >= 360)
                angle -= 360;

            // get section of angle between node and depot and put it in the right inner arraylist
            int section = (int) Math.floor(angle / anglePerSection);
            subRoutes.get(section).add(node);
        }

        return subRoutes;
    }
}