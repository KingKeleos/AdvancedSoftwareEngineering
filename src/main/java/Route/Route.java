package Route;

import Importer.Node;

import java.util.ArrayList;

public class Route {
    private final ArrayList<Node> route;
    private final double travelTime;

    public Route(ArrayList<Node> route, double travelTime) {
        this.route = route;
        this.travelTime = travelTime;
    }

    public ArrayList<Node> getRouteList() {
        return route;
    }

    public double getTravelTime() {
        return travelTime;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (!route.isEmpty()) {
            stringBuilder.append("\n\nRoute     : ");
            for (Node n : route) {
                stringBuilder.append(n.getId()).append(" ");
            }
            stringBuilder.append("\nTraveltime: ").append(getTravelTime()).append(" h");

            stringBuilder = new StringBuilder(stringBuilder.substring(0, stringBuilder.length() - 1));
            return stringBuilder.toString();
        } else
            return null;
    }
}
