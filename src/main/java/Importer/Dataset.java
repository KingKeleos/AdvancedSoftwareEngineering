package Importer;

import Route.Route;

import java.util.ArrayList;

public class Dataset {

    // time spent at customers = 30min -> 0.5 Hours
    // time spent at gasstation = 15 min -> 0.2 hours
    Double TIME_SPENT_AT_CUSTOMER = 0.5;
    Double TIME_SPENT_AT_GASSTATION = 0.2;

    private ArrayList<Node> nodes;

    private Node depot;
    private ArrayList<Node> customerList;
    private ArrayList<Node> gasStationList;
    private Integer fuelCapacity;
    private Double fuelConsumptionRate;
    private Integer tourLength;
    private Integer avgVelocity;
    private Integer numVehicles;

    public Dataset() {
    }

    public static double calculateDistanceBetweenNodes(Node node1, Node node2) {
        // Vorgegebene Formel aus Datensatz zur Kalkulation der Distanz zweier Knoten
        double lat1 = node1.getLatitude();
        double lat2 = node2.getLatitude();
        double lon1 = node1.getLongitude();
        double lon2 = node2.getLongitude();
        double radiusOfEarth = 3959; // miles, 6371km;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return radiusOfEarth * c;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public Integer getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(Integer fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public Double getFuelConsumptionRate() {
        return fuelConsumptionRate;
    }

    public void setFuelConsumptionRate(Double fuelConsumptionRate) {
        this.fuelConsumptionRate = fuelConsumptionRate;
    }

    public Integer getTourLength() {
        return tourLength;
    }

    public void setTourLength(Integer tourLength) {
        this.tourLength = tourLength;
    }

    public Integer getAvgVelocity() {
        return avgVelocity;
    }

    public void setAvgVelocity(Integer avgVelocity) {
        this.avgVelocity = avgVelocity;
    }

    public Integer getNumVehicles() {
        return numVehicles;
    }

    public void setNumVehicles(Integer numVehicles) {
        this.numVehicles = numVehicles;
    }

    public ArrayList<Node> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(ArrayList<Node> customerList) {
        this.customerList = customerList;
    }

    public Node getDepot() {
        return depot;
    }

    public void setDepot(Node depot) {
        this.depot = depot;
    }

    public Double getTIME_SPENT_AT_CUSTOMER() {
        return TIME_SPENT_AT_CUSTOMER;
    }

    public void setTIME_SPENT_AT_CUSTOMER(Double TIME_SPENT_AT_CUSTOMER) {
        this.TIME_SPENT_AT_CUSTOMER = TIME_SPENT_AT_CUSTOMER;
    }

    public Double getTIME_SPENT_AT_GASSTATION() {
        return TIME_SPENT_AT_GASSTATION;
    }

    public void setTIME_SPENT_AT_GASSTATION(Double TIME_SPENT_AT_GASSTATION) {
        this.TIME_SPENT_AT_GASSTATION = TIME_SPENT_AT_GASSTATION;
    }

    public ArrayList<Node> getGasStationList() {
        return gasStationList;
    }

    public void setGasStationList(ArrayList<Node> gasStationList) {
        this.gasStationList = gasStationList;
    }

    // Zielfunktion
    // Gibt einen Zeitwert (Zeit, welche benötigt wird um die Gesamtstrecke der Route abzufahren in Stunden zurück)
    // Überprüft ebenfalls ob genug Sprit für eine Strecke verfügbar, wenn nicht Umweg über nachstgelege Tankstelle
    // Kann verwendet werden um die Qualität der Route zu berechnen
    // Genaue Beschreibung in readme S01
    public Route routeQuality(ArrayList<Node> origRoute) {
        ArrayList<Node> route = createRouteWithGasStations(origRoute);

        Double timeTraveled = 0.0;
        ArrayList<Double> distances = calcDistancesArray(route);
        if (route.isEmpty()) {
            return null;
        }

        for (int i = 0; i <= route.size() - 1; i++) {
            timeTraveled += distances.get(i) / this.avgVelocity;
            if (route.get(i).getType().equals("c")) {
                timeTraveled += this.TIME_SPENT_AT_CUSTOMER;
            } else if (route.get(i).getType().equals("f")) {
                timeTraveled += this.TIME_SPENT_AT_GASSTATION;
            }
        }
        return new Route(route, timeTraveled);
    }

    private ArrayList<Node> createRouteWithGasStations(ArrayList<Node> routeOrig) {

        ArrayList<Node> route = routeOrig;
        ArrayList<Double> distances = calcDistancesArray(routeOrig);

        Double fuel = Double.valueOf(this.fuelCapacity);

        for (int i = 0; i <= route.size() - 1; i++) {

            if (route.get(i).getType().equals("f"))
                fuel = Double.valueOf(this.fuelCapacity);

            if (fuel > distances.get(i) * this.fuelConsumptionRate) {
                fuel -= distances.get(i) * this.fuelConsumptionRate;
            } else {
                Node nearestGS = null;
                if (i != route.size() - 1) {
                    nearestGS = findNearestGasStation(route.get(i), route.get(i + 1));
                } else {
                    nearestGS = findNearestGasStation(route.get(i), route.get(0));
                }

                if (fuel > calculateDistanceBetweenNodes(nearestGS, route.get(i)) * this.fuelConsumptionRate) {
                    if (route.get(i).getId().equals(nearestGS.getId())) {
                        return new ArrayList<>();
                    }
                    route.add(i + 1, nearestGS);
                    distances = calcDistancesArray(route);
                    i = 0;
                } else {
                    nearestGS = findNearestGasStation(route.get(i - 1), route.get(i));
                    if (route.get(i - 1).getId().equals(nearestGS.getId())) {
                        return new ArrayList<>();
                    }
                    route.add(i, nearestGS);
                    distances = calcDistancesArray(route);
                    i = 0;
                }
            }
        }
        return route;
    }

    private ArrayList<Double> calcDistancesArray(ArrayList<Node> routeOrig) {

        ArrayList<Double> distances = new ArrayList<>();
        for (int i = 0; i <= routeOrig.size() - 1; i++) {
            if (i != routeOrig.size() - 1)
                distances.add(calculateDistanceBetweenNodes(routeOrig.get(i), routeOrig.get(i + 1)));
            else
                distances.add(calculateDistanceBetweenNodes(routeOrig.get(i), routeOrig.get(0)));
        }
        return distances;
    }

    private ArrayList<Node> createGasStationList() {
        ArrayList<Node> gasStations = new ArrayList<>(0);
        for (Node node : this.nodes) {
            if (node.getType().equals("f")) {
                gasStations.add(node);
            }
        }
        return gasStations;
    }

    private ArrayList<Node> createCustomerNodeList() {
        ArrayList<Node> customers = new ArrayList<>();
        for (Node node : this.nodes) {
            if (node.getType().equals("c")) {
                customers.add(node);
            }
        }
        return customers;
    }

    private Node findNearestGasStation(Node node, Node nextNode) {
        double shortestDistance = calculateDistanceBetweenNodes(node, gasStationList.get(0)) + calculateDistanceBetweenNodes(gasStationList.get(0), nextNode);
        Node closestGasStation = gasStationList.get(0);

        for (int i = 1; i <= gasStationList.size() - 1; i++) {
            double distance = calculateDistanceBetweenNodes(node, gasStationList.get(i)) + calculateDistanceBetweenNodes(nextNode, gasStationList.get(i));
            if (distance < shortestDistance) {
                shortestDistance = distance;
                closestGasStation = gasStationList.get(i);
            }
        }
        return closestGasStation;
    }

    public void initializeLists() {
        this.gasStationList = createGasStationList();
        this.customerList = createCustomerNodeList();
        this.depot = nodes.get(0);
    }
}
