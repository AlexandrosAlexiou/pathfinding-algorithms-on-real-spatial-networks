import java.util.ArrayList;

public class GraphEntry {
    private final Vertex vertex;
    private final ArrayList<Edge> neighbors = new ArrayList<>();

    public GraphEntry(Vertex vertex) {
        this.vertex = vertex;
    }

    public Vertex getNode() {
        return vertex;
    }

    public ArrayList<Edge> getNeighbors() {
        return neighbors;
    }

    @Override
    public String toString() {
        StringBuilder builder =  new StringBuilder();
        for (Edge edge : neighbors) {
            builder.append(edge.toString());
        }
        return vertex.toString() + builder;
    }
}
