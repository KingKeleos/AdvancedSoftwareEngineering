package Vehicle;

import Algorithms.ACO.AntColonyOptimization;
import Importer.Dataset;
import Importer.Node;
import Route.Route;

import java.util.HashMap;

public class Ant {
    protected Dataset dataset;
    protected int trailSize;
    public int[] trail;
    protected boolean[] visited;

    public Ant(int tourSize, Dataset dataset) {
        this.dataset = dataset;
        this.trailSize = tourSize;
        trail = new int[tourSize];
        visited = new boolean[tourSize];
    }

    public void visitCity(int currentIndex, int city) {
        trail[currentIndex + 1] = city;
        visited[city] = true;
    }

    public boolean visited(int i) {
        return visited[i];
    }

    public Route trailCost(HashMap<Integer, Node> references) {
        return dataset.routeQuality(AntColonyOptimization.trailToNodes(trail, references));
    }

    public void clear() {
        for (int i = 0; i < trailSize; i++) {
            visited[i] = false;
        }
    }
}
