package Algorithms.ACO;

import Configurations.ACOConfiguration;
import Importer.Dataset;
import Importer.Node;
import Route.Route;
import Vehicle.Ant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AntColonyOptimization {
    private final double[][] graph;
    private final HashMap<Integer, Node> references;
    private final double[][] trails;
    private final List<Ant> ants = new ArrayList<>();
    private final double[] probabilities;
    private final int numberOfAnts;
    private int currentIndex;
    private Route bestRoute;

    private int[] bestTourOrder;
    private double bestTourLength;

    public AntColonyOptimization(Dataset dataset) {
        this.bestRoute = null;
        this.references = prepareData(dataset.getCustomerList());
        this.graph = generateDistanceMatrix(references);
        this.trails = new double[references.size()][references.size()];
        this.probabilities = new double[references.size()];
        int antCount = (int) (references.size() * ACOConfiguration.INSTANCE.antFactor);

        if (antCount == 0)
            this.numberOfAnts = references.size();
        else
            this.numberOfAnts = antCount;

        for (int i = 0; i < numberOfAnts; i++) {
            ants.add(new Ant(references.size(), dataset));
        }
    }

    public static HashMap<Integer, Node> prepareData(ArrayList<Node> nodes) {
        HashMap<Integer, Node> map = new HashMap<>();

        for (int i = 0; i < nodes.size(); i++) {
            map.put(i, nodes.get(i));
        }

        return map;
    }

    public static double[][] generateDistanceMatrix(HashMap<Integer, Node> nodes) {
        double[][] distanceMatrix = new double[nodes.size()][nodes.size()];

        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size(); j++) {
                if (i == j) {
                    distanceMatrix[i][j] = 0;
                } else {
                    distanceMatrix[i][j] = Math.abs(Dataset.calculateDistanceBetweenNodes(nodes.get(i), nodes.get(j)));
                }
            }
        }

        return distanceMatrix;
    }

    public static ArrayList<Node> trailToNodes(int[] route, HashMap<Integer, Node> references) {
        ArrayList<Node> nodes = new ArrayList<>();
        for (int i = 0; i < route.length; i++) {
            nodes.add(references.get(i));
        }
        return nodes;
    }

    public Route run() {
        setupAnts();
        clearTrails();

        for (int i = 0; i < ACOConfiguration.INSTANCE.maximumIterations; i++) {
            moveAnts();
            updateTrails();
            updateBest();
        }

        return bestRoute;
    }

    private void setupAnts() {
        for (int i = 0; i < numberOfAnts; i++) {
            for (Ant ant : ants) {
                ant.clear();
                ant.visitCity(-1, 0);
            }
        }
        currentIndex = 0;
    }

    private void moveAnts() {
        for (int i = currentIndex; i < references.size() - 1; i++) {
            for (Ant ant : ants) {
                ant.visitCity(currentIndex, selectNextCity(ant));
            }
            currentIndex++;
        }
    }

    private int selectNextCity(Ant ant) {
        int t = ACOConfiguration.INSTANCE.randomGenerator.nextInt(references.size() - currentIndex);
        if (ACOConfiguration.INSTANCE.randomGenerator.nextDouble() < ACOConfiguration.INSTANCE.randomFactor) {
            int cityIndex = -999;

            for (int i = 0; i < references.size(); i++) {
                if (i == t && !ant.visited(i)) {
                    cityIndex = i;
                    break;
                }
            }

            if (cityIndex != -999) {
                return cityIndex;
            }
        }

        for (int i = 0; i < references.size(); i++) {
            if (graph[ant.trail[currentIndex]][i] == 0.0 && !ant.visited(i)) {
                return i;
            }
        }

        calculateProbabilities(ant);

        double randomNumber = ACOConfiguration.INSTANCE.randomGenerator.nextDouble();
        double total = 0;

        for (int i = 0; i < references.size(); i++) {
            total += probabilities[i];
            if (total >= randomNumber) {
                return i;
            }
        }

        throw new RuntimeException("runtime exception : other cities");
    }

    public void calculateProbabilities(Ant ant) {
        int i = ant.trail[currentIndex];
        double pheromone = 0.0;

        for (int l = 0; l < references.size(); l++) {
            if (!ant.visited(l)) {
                pheromone += Math.pow(trails[i][l], ACOConfiguration.INSTANCE.alpha) * Math.pow(1.0 / graph[i][l], ACOConfiguration.INSTANCE.beta);
            }
        }

        for (int j = 0; j < references.size(); j++) {
            if (ant.visited(j)) {
                probabilities[j] = 0.0;
            } else {
                double numerator = Math.pow(trails[i][j], ACOConfiguration.INSTANCE.alpha) * Math.pow(1.0 / graph[i][j], ACOConfiguration.INSTANCE.beta);
                probabilities[j] = numerator / pheromone;
            }
        }
    }

    private void updateTrails() {
        for (int i = 0; i < references.size(); i++) {
            for (int j = 0; j < references.size(); j++) {
                trails[i][j] *= ACOConfiguration.INSTANCE.evaporation;
            }
        }

        for (Ant ant : ants) {
            if (ant.trailCost(references) != null) {
                double contribution = ACOConfiguration.INSTANCE.q / ant.trailCost(references).getTravelTime();
                for (int i = 0; i < references.size() - 1; i++) {
                    trails[ant.trail[i]][ant.trail[i + 1]] += contribution;
                }
                trails[ant.trail[references.size() - 1]][ant.trail[0]] += contribution;
            }
        }
    }

    private void updateBest() {
        if (bestTourOrder == null && ants.get(0).trailCost(references) != null) {
            bestTourOrder = ants.get(0).trail;
            bestRoute = ants.get(0).trailCost(references);
            bestTourLength = bestRoute.getTravelTime();
        }

        for (Ant ant : ants) {
            if (ant.trailCost(references) != null) {
                if (ant.trailCost(references).getTravelTime() < bestTourLength) {
                    bestRoute = ant.trailCost(references);
                    bestTourLength = bestRoute.getTravelTime();
                    bestTourOrder = ant.trail.clone();
                }
            }
        }
    }

    private void clearTrails() {
        for (int i = 0; i < references.size(); i++) {
            for (int j = 0; j < references.size(); j++) {
                trails[i][j] = ACOConfiguration.INSTANCE.initialPheromoneValue;
            }
        }
    }
}
