import java.util.ArrayList;

public class Vertex implements Comparable<Vertex> {
    private final String id;
    private final Double longitude;
    private final Double latitude;
    private boolean visited;
    private Vertex previous;
    private double distance = Double.MAX_VALUE;
    private final ArrayList<Edge> adj = new ArrayList<>();

    public Vertex(String id, Double longitude, Double latitude) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getId() {
        return id;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public boolean isVisited() {
        return visited;
    }

    public Vertex getPrevious() {
        return previous;
    }

    public double getDistance() {
        return distance;
    }

    public ArrayList<Edge> getAdj() {
        return adj;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setPrevious(Vertex previous) {
        this.previous = previous;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vertex other = (Vertex) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }

    @Override
    public String toString() {
        StringBuilder builder =  new StringBuilder();
        for (Edge edge : adj) {
            builder.append(edge.toString());
        }
        return this.id + " " + getLongitude() + " " + getLatitude() + builder;
    }

    @Override
    public int compareTo(Vertex other) {
        return Double.compare(this.distance, other.getDistance());
    }

}
