package Importer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DatasetReader {

    String datasetPath;

    public DatasetReader() {
        this.datasetPath = Paths.get("").toAbsolutePath().normalize().toString() + "/data/";;
    }

    public Dataset createDataset(String filename) {
        return readDataFromFile(filename);
    }


    private Dataset readDataFromFile(String filename) {

        Dataset dataset = new Dataset();
        File file = new File(datasetPath + filename);

        if (file.canRead() && file.isFile()) {

            BufferedReader reader = null;

            try {
                reader = new BufferedReader(new FileReader(file));
                ArrayList<Node> nodes = new ArrayList<Node>(0);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    if (!line.contains("Type") || !line.contains("Longitude")) {
                        if (line.contains("\t")) {
                            nodes.add(this.createNode(line.split("\t")));
                        } else {
                            setDatasetAttribute(dataset, line);
                        }
                    }
                }
                dataset.setNodes(nodes);
                dataset.initializeLists();
            } catch (Exception e) {
                System.out.println("Error reading File: " + filename);
            } finally {
                try {
                    reader.close();
                } catch (Exception e) {
                    System.out.println("Error reading File: " + filename);
                }
            }
            return dataset;
        } else {
            System.out.println("Error reading File - Filename may be invalid");
            return null;
        }
    }

    private Node createNode(String[] props) {
        return new Node(props[0], props[1], Double.valueOf(props[2]), Double.valueOf(props[3]));
    }

    private void setDatasetAttribute(Dataset ds, String line) {
        if (line.contains("capacity")) {
            ds.setFuelCapacity(Integer.valueOf(line.split("/")[1]));
        } else if (line.contains("consumption")) {
            ds.setFuelConsumptionRate(Double.valueOf(line.split("/")[1]));

        } else if (line.contains("TourLength")) {
            ds.setTourLength(Integer.valueOf(line.split("/")[1]));
        } else if (line.contains("Velocity")) {
            ds.setAvgVelocity(Integer.valueOf(line.split("/")[1]));
        } else if (line.contains("numVeh")) {
            ds.setNumVehicles(Integer.valueOf(line.split("/")[1]));
        }
    }


}