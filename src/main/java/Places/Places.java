package Places;

public class Places {
    private final String ID;
    private final double longitude;
    private final double latitude;
    private final String Type;

    public Places(String ID, double longitude, double latitude, String Type){
        this.ID = ID;
        this.longitude = longitude;
        this.latitude = latitude;
        this.Type = Type;
    }

    public String getID(){
        return this.ID;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public double getLatitude(){
        return this.latitude;
    }

    public String getType(){
        return this.Type;
    }
}
