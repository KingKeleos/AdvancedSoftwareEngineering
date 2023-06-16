package Importer;

public class Node {

    private String id;
    private String type;
    private double longitude;
    private double latitude;

    public Node(String id, String type, double longitude, double latitude) {
        this.id = id;
        this.type = type;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

}