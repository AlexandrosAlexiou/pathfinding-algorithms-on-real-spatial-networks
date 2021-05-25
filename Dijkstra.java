import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {

    private final Graph graph;

    public Dijkstra(Graph graph) {
        this.graph = graph;
    }

    public void computeShortestPaths(int source_id, int target_id) {
        Vertex sourceVertex = graph.getVertices().get(source_id);
        Vertex targetVertex = graph.getVertices().get(target_id);
        sourceVertex.setDistance(0);
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(sourceVertex);
        sourceVertex.setVisited(true);
        int nodes_visited = 0;
        while (!priorityQueue.isEmpty()) {
            Vertex actualVertex = priorityQueue.poll(); // Get the minimum distance vertex from priority queue
            actualVertex.setVisited(true);
            nodes_visited++;

            if (actualVertex == targetVertex) break; //reached target and found the min dist

            for (Edge edge : actualVertex.getAdj()) {
                Vertex v = edge.getDest();
                if (!v.isVisited()) {
                    double newDistance = actualVertex.getDistance() + edge.getDistance();

                    if (newDistance < v.getDistance()) {
                        priorityQueue.remove(v);
                        v.setDistance(newDistance);
                        v.setPrevious(actualVertex);
                        priorityQueue.add(v);
                    }
                }
            }
        }
        List<String> shortestPath = getShortestPathTo(targetVertex);
        System.out.println("Dijkstra:");
        System.out.println("Shortest Path length: " + shortestPath.size());
        System.out.println("Minimum path distance from "+ source_id + " to " + target_id + " = " + targetVertex.getDistance());
        System.out.println("Shortest Path from "+ source_id + " to " + target_id + " = " + shortestPath);
        System.out.println("Number of visited nodes = " + nodes_visited);
    }

    public List<String> getShortestPathTo(Vertex targetVertex) {
        List<String> path = new ArrayList<>();

        for (Vertex vertex = targetVertex; vertex != null; vertex = vertex.getPrevious()) {
            path.add(vertex.getId());
        }
        Collections.reverse(path);
        return path;
    }
}