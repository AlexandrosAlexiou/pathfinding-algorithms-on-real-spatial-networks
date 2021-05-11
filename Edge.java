public class Edge {
    private final Node neighbor;
    private final Float distance;

    public Edge(Node neighbor, Float distance) {
        this.neighbor = neighbor;
        this.distance = distance;
    }

    public Node getNeighbor() {
        return neighbor;
    }

    public Float getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return " " + this.neighbor.getId() + " " + this.distance;
    }
}
