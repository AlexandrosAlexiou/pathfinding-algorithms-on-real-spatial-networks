import java.util.ArrayList;

public class GraphEntry {
    private final Node node;
    private final ArrayList<Edge> neighbors = new ArrayList<>();

    public GraphEntry(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
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
        return node.toString() + builder;
    }
}
