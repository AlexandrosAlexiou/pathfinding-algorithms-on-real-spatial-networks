import java.util.*;

public class Astar {

    private final Graph graph;
    private final Set<Integer> visited;
    private final PriorityQueue<Vertex> priorityQueue;

    public Astar(Graph graph) {
        this.graph = graph;
        this.visited = new HashSet<Integer>();
        this.priorityQueue= new PriorityQueue<>();
    }

    public void computeShortestPaths(int source_id, int target_id) {
        Vertex sourceVertex = graph.getVertices().get(source_id);
        Vertex targetVertex = graph.getVertices().get(target_id);

        priorityQueue.add(sourceVertex);
        sourceVertex.setDistance(0);
        visited.add(Integer.parseInt(sourceVertex.getId()));

        while (!priorityQueue.isEmpty()) {
            Vertex actualVertex = priorityQueue.poll(); // Get the minimum distance vertex from priority queue
            visited.add(Integer.parseInt(actualVertex.getId()));

            if (actualVertex == targetVertex) break; //reached target and found the min dist

            for (Edge edge : actualVertex.getAdj()) {
                Vertex neighbor = edge.getDest();
                if (!visited.contains(Integer.parseInt(neighbor.getId()))) {
                    neighbor.setPrediction(neighbor.distance(targetVertex));

                    double newDistance = actualVertex.getDistance() + edge.getDistance() + neighbor.getPrediction();

                    if (newDistance < neighbor.getDistance() + neighbor.getPrediction()) {
                        priorityQueue.remove(neighbor);
                        neighbor.setDistance(actualVertex.getDistance() + edge.getDistance());
                        neighbor.setPrevious(actualVertex);
                        priorityQueue.add(neighbor);
                    }
                }
            }
        }
        List<String> shortestPath = getShortestPathTo(targetVertex);
        System.out.println("Astar:");
        System.out.println("Shortest Path length: " + shortestPath.size());
        System.out.println("Minimum path distance from "+ source_id + " to " + target_id + " = " + targetVertex.getDistance());
        System.out.println("Shortest Path from "+ source_id + " to " + target_id + " = " + shortestPath);
        System.out.println("Number of visited nodes = " + visited.size());
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
