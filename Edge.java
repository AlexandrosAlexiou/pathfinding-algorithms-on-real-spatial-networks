public class Edge {
    private final Vertex destination;
    private final Float distance;

    public Edge( Vertex destination, Float distance) {
        this.destination = destination;
        this.distance = distance;
    }

    public Vertex getDestination() {
        return destination;
    }

    public Float getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return " " + this.destination.getId() + " " + this.distance;
    }
}
