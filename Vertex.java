// ALEXANDROS ALEXIOU 2929

import java.util.ArrayList;

public class Vertex implements Comparable<Vertex> {
    private final String id;
    private final Double longitude;
    private final Double latitude;
    private Vertex previous;
    private double distance = Double.MAX_VALUE;
    private double prediction = 0.0;
    private ArrayList<Edge> adj = new ArrayList<>();
    private int visits;

    public Vertex(String id, Double longitude, Double latitude) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getId() {
        return id;
    }

    public Integer getIntegerId() {
        return Integer.parseInt(id);
    }

    public double[] getLocation() {
        return new double[]{longitude, latitude};
    }

    public Vertex getPrevious() {
        return previous;
    }

    public double getDistance() {
        return distance;
    }

    public double getPrediction() {
        return prediction;
    }

    public ArrayList<Edge> getAdj() {
        return adj;
    }

    public int getVisits() {
        return visits;
    }

    public void advanceVisits() {
        this.visits += 1;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setPrediction(double prediction) {
        this.prediction = prediction;
    }

    public void setPrevious(Vertex previous) {
        this.previous = previous;
    }

    public void setAdj(ArrayList<Edge> adj) {
        this.adj = adj;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public double get_euclidean_distance(Vertex other){
        double ycoord = Math.abs(this.latitude - other.latitude);
        double xcoord = Math.abs(this.longitude - other.longitude);
        return Math.sqrt(ycoord*ycoord + xcoord*xcoord);
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
        return this.id + " " + longitude + " " + latitude + builder;
    }

    @Override
    public int compareTo(Vertex other) {
        return Double.compare(this.distance + prediction, other.getDistance() + other.getPrediction());
    }

    public Vertex deepCopy() {
        Vertex copy = new Vertex(this.id, this.longitude, this.latitude);
        copy.setAdj(new ArrayList<Edge>(this.getAdj()));
        copy.setVisits(this.getVisits());
        return copy;
    }
}
