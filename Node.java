import java.util.Arrays;

public class Node {
    private final int id;
    private final Float longitude;
    private final Float latitude;

    public Node(int id, Float longitude, Float latitude ) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public Float getLongitude() {
        return this.longitude;
    }

    public Float getLatitude() {
        return this.latitude;
    }

    @Override
    public String toString() {
        return this.id + " " + getLongitude() + " " + getLatitude();
    }
}
