public class Edge {
    public final Vertex source;
    public final Vertex dest;
    public final Double distance;

    public Edge(Vertex source, Vertex dest, Double distance) {
        this.source = source;
        this.dest = dest;
        this.distance = distance;
    }

    public Vertex getSource() {
        return source;
    }

    public Vertex getDest() {
        return dest;
    }

    public Double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return " " + this.dest.getId() + " " + this.distance;
    }
}
