import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NRA {

    static class VertexInQueue implements Comparable<VertexInQueue>{
        Vertex v;
        double distance;

        public VertexInQueue(Vertex v, double distance) {
            this.v = v;
            this.distance = distance;
        }

        public Vertex getV() {
            return v;
        }

        public double getDistance() {
            return distance;
        }

        @Override
        public int compareTo(VertexInQueue o) {
            return Double.compare(this.distance, o.getDistance());
        }
    };

    static class MinimumDistanceVertex {
        int vertexId;
        double distance;

        public MinimumDistanceVertex(int vertexId, double distance) {
            this.vertexId = vertexId;
            this.distance = distance;
        }
    }

    private final Graph graph;
    private final HashMap<Vertex, ArrayList<Double>> SPDs = new HashMap<>();
    private final HashMap<Vertex, ArrayList<ArrayList<Vertex>>> paths = new HashMap<>();
    private final HashMap<Vertex, ArrayList<Boolean>> visited = new HashMap<>();
    private final HashMap<Vertex, Vertex> last_nodes_visited_from_starting_nodes = new HashMap<>();
    private final HashMap<Vertex, PriorityQueue<VertexInQueue>> priorityQueues = new HashMap<>();

    public NRA(Graph graph) {
        this.graph = graph;
    }

    private  ArrayList<Vertex> cloneArraylist(ArrayList<Vertex> toCopy){
        ArrayList<Vertex> deepCopy = new ArrayList<>();
        for(Vertex v : toCopy) {
            deepCopy.add(v.deepCopy());
        }
        return deepCopy;
    }

    /**
     *  Returns  the vertex id which satisfies the condition:
     *  min(dist(n1, m1), dist(n2, m2), ..., dist(nν, mν)) = dist(ni, mi)
     *
     *  The starting nodes are n1, n2, ..., nν and the last nodes we have visited from them
     *  are m1, m2, ..., mν respectively.
     *
     * @return the (ni, mi) encapsulated in the MinimumDistanceVertex class, Dijkstra will continue from there.
     */
    private MinimumDistanceVertex getMinimumDistanceVertex() {
        double minimum_distance = Double.MAX_VALUE;
        int minimum_distance_node_id = -1;
        for (Vertex v : SPDs.keySet()) {
            Vertex last_visited_vertex = last_nodes_visited_from_starting_nodes.get(v);
            double distance = SPDs.get(v).get(last_visited_vertex.getIntegerId());
            if (distance < minimum_distance) {
                minimum_distance = distance;
                minimum_distance_node_id = v.getIntegerId();
            }
        }
        return new MinimumDistanceVertex(minimum_distance_node_id, minimum_distance);
    }

    /**
     * Calculates and prints the best meeting point given the users (Vertex_ids)
     *
     * @param users: the users who want to meet
     */
    public void findOptimalMeetingPoint(int[] users) {
        int meeting_node_id = Integer.MIN_VALUE;
        double meeting_node_distance = Double.MAX_VALUE;

        for (int v_id : users) {
            Vertex v = graph.getVertex(v_id);
            SPDs.put(v, new ArrayList<>());
            paths.put(v, new ArrayList<>());
            visited.put(v, new ArrayList<>());
            v.advanceVisits();
            last_nodes_visited_from_starting_nodes.put(v, v);
            priorityQueues.put(v, new PriorityQueue<>());

            for (Vertex graphVertex : graph.getVertices()) {
                SPDs.get(v).add(Double.MAX_VALUE);
                paths.get(v).add(new ArrayList<>());
                paths.get(v).get(paths.get(v).size() - 1).add(v);
                visited.get(v).add(false);
            }
            SPDs.get(v).set(v_id, 0.0);
            priorityQueues.get(v).add(new VertexInQueue(v, 0.0));
        }

        MinimumDistanceVertex minimumDistanceVertex = getMinimumDistanceVertex();
        while (!(meeting_node_id != Integer.MIN_VALUE && meeting_node_distance > minimumDistanceVertex.distance)) {

            int v_id = minimumDistanceVertex.vertexId;
            VertexInQueue viq = priorityQueues.get(graph.getVertex(v_id)).poll();

            visited.get(graph.getVertex(v_id)).set(viq.getV().getIntegerId(), true);
            graph.getVertex(viq.getV().getIntegerId()).advanceVisits();
            last_nodes_visited_from_starting_nodes.put(graph.getVertex(v_id), viq.getV());

            if (viq.getV().getVisits() == users.length) {
                double distance = Double.MIN_VALUE;
                for (Vertex v : SPDs.keySet()) {
                    if (SPDs.get(v).get(viq.getV().getIntegerId()) > distance)
                        distance = SPDs.get(v).get(viq.getV().getIntegerId());
                }
                if (distance < meeting_node_distance) {
                    meeting_node_distance = distance;
                    meeting_node_id = viq.getV().getIntegerId();
                }
            }

            for (Edge edge : viq.getV().getAdj()) {
                Vertex neighbor = edge.getDest();
                if (!visited.get(graph.getVertex(v_id)).get(neighbor.getIntegerId())) {
                    if (SPDs.get(graph.getVertex(v_id)).get(neighbor.getIntegerId()) >
                            SPDs.get(graph.getVertex(v_id)).get(viq.getV().getIntegerId()) + edge.getDistance()) {
                        SPDs.get(graph.getVertex(v_id)).set(neighbor.getIntegerId(), SPDs.get(graph.getVertex(v_id)).get(viq.getV().getIntegerId()) + edge.getDistance());

                        VertexInQueue new_V = new VertexInQueue(neighbor, SPDs.get(graph.getVertex(v_id)).get(neighbor.getIntegerId()));

                        ArrayList<Vertex> temp = paths.get(graph.getVertex(v_id)).get(viq.getV().getIntegerId());
                        ArrayList<Vertex> n = cloneArraylist(temp);
                        paths.get(graph.getVertex(v_id)).set(neighbor.getIntegerId(), n);
                        paths.get(graph.getVertex(v_id)).get(neighbor.getIntegerId()).add(neighbor);
                        priorityQueues.get(graph.getVertex(v_id)).remove(new_V);
                        priorityQueues.get(graph.getVertex(v_id)).add(new_V);
                    }
                }
            }
            minimumDistanceVertex = getMinimumDistanceVertex();
        }

        System.out.println("Best meeting point: " + meeting_node_id);
        System.out.println("Shortest path distance = " + meeting_node_distance);
        System.out.println("Paths:");
        for (int user : users) {
            List<Integer> path = paths.get(graph.getVertex(user)).get(meeting_node_id)
                    .stream()
                    .map(Vertex::getIntegerId)
                    .collect(Collectors.toList());
            System.out.println("[" + SPDs.get(graph.getVertex(user)).get(meeting_node_id) + ", " +  path + "]");
        }
    }

    public static void main(String[] args) {
        // Create the graph
        Graph graph = new Graph();
        graph.initialize();
        int[] users = Stream.of(args).mapToInt(Integer::parseInt).toArray();
        NRA nra = new NRA(graph);
        nra.findOptimalMeetingPoint(users);
    }
}
