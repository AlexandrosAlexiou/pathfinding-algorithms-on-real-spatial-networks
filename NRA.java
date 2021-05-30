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

    private final Graph graph;

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
    private double getMinimumMaximumDistance(HashMap<Vertex, ArrayList<Double>> SPDs, HashMap<Vertex, Vertex> last_visited) {
        double min_distance = Double.MAX_VALUE;

        for (Vertex v : last_visited.keySet()) {
            double max_distance = Double.MIN_VALUE;
            if (v.getVisits() < SPDs.keySet().size()) {
                for (Vertex start : SPDs.keySet()) {
                    if (SPDs.get(start).get(v.getIntegerId()) > max_distance && SPDs.get(start).get(v.getIntegerId()) != Double.MAX_VALUE) {
                        max_distance = SPDs.get(start).get(v.getIntegerId());
                    }
                }
            }
            if (max_distance < min_distance) {
                min_distance = max_distance;
            }
        }
        return min_distance;
    }

    private int getMinimumDistanceNodeId(HashMap<Vertex, ArrayList<Double>> SPDs, HashMap<Vertex, Vertex> last_visited) {
        double minimum_distance = Double.MAX_VALUE;
        int minimum_distance_node_id = -1;
        for (Vertex v : SPDs.keySet()) {
            Vertex last_visited_vertex = last_visited.get(v);
            double distance = SPDs.get(v).get(last_visited_vertex.getIntegerId());
            if (distance < minimum_distance) {
                minimum_distance = distance;
                minimum_distance_node_id = v.getIntegerId();
            }
        }
        return minimum_distance_node_id;
    }

    public void findOptimalMeetingPoint(int[] users) {
        int meeting_node_id = Integer.MIN_VALUE;
        double meeting_node_distance = Double.MAX_VALUE;

        HashMap<Vertex, ArrayList<Double>> SPDs = new HashMap<>();
        HashMap<Vertex, ArrayList<ArrayList<Vertex>>> paths = new HashMap<>();
        HashMap<Vertex, ArrayList<Boolean>> visited = new HashMap<>();
        HashMap<Vertex, Vertex> last_visited = new HashMap<>();
        HashMap<Vertex, PriorityQueue<VertexInQueue>> priorityQueues = new HashMap<>();

        for (int v_id : users) {
            Vertex v = graph.getVertex(v_id);
            SPDs.put(v, new ArrayList<>());
            paths.put(v, new ArrayList<>());
            visited.put(v, new ArrayList<>());
            v.advanceVisits();
            last_visited.put(v, v);
            priorityQueues.put(v, new PriorityQueue<>());

            for (Vertex g_vertex : graph.getVertices()) {
                SPDs.get(v).add(Double.MAX_VALUE);
                paths.get(v).add(new ArrayList<Vertex>());
                paths.get(v).get(paths.get(v).size() - 1).add(v);
                visited.get(v).add(false);
            }
            SPDs.get(v).set(v_id, 0.0);
            priorityQueues.get(v).add(new VertexInQueue(v, 0.0));
        }

        while (!(meeting_node_id != Integer.MIN_VALUE && meeting_node_distance > getMinimumMaximumDistance(SPDs, last_visited))) {

            int v_id = getMinimumDistanceNodeId(SPDs, last_visited);
            VertexInQueue viq = priorityQueues.get(graph.getVertex(v_id)).poll();

            visited.get(graph.getVertex(v_id)).set(viq.getV().getIntegerId(), true);
            graph.getVertex(viq.getV().getIntegerId()).advanceVisits();
            last_visited.put(graph.getVertex(v_id), viq.getV());

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
        }

        System.out.println("Best meeting point: " + meeting_node_id);
        System.out.println("Shortest path distance = " + meeting_node_distance);
        for (int user : users) {
            ArrayList<Vertex> temp = paths.get(graph.getVertex(user)).get(meeting_node_id);
            List<Integer> path = temp.stream().map(Vertex::getIntegerId).collect(Collectors.toList());
            System.out.println("[" + SPDs.get(graph.getVertex(user)).get(meeting_node_id) + "," +  path + "]");
            System.out.println();
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
