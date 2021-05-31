// ALEXANDROS ALEXIOU 2929

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

class Graph {

    private final ArrayList<Vertex> vertices = new ArrayList<>();
    private final ArrayList<Edge> edges = new ArrayList<>();

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public Vertex getVertex(int id) {
        return vertices.get(id);
    }

    public void initialize() {
        try {
            Scanner nodes_reader = open(Constants.NODES_FILE);
            Scanner edges_reader = open(Constants.EDGES_FILE);
            while (nodes_reader.hasNextLine()) {
                String[] nodes_line = getData(nodes_reader.nextLine());
                Vertex vertex = new Vertex(nodes_line[0], Double.parseDouble(nodes_line[1]), Double.parseDouble(nodes_line[2]));
                vertices.add(vertex);
            }

            while (edges_reader.hasNextLine()) {
                String[] edges_line = getData(edges_reader.nextLine());
                int start_node = Integer.parseInt(edges_line[1]);
                int end_node = Integer.parseInt(edges_line[2]);
                Double distance = Double.parseDouble(edges_line[3]);
                addEdge(start_node, end_node, distance);
            }
            nodes_reader.close();
            edges_reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addEdge(int start_node, int end_node, Double distance) {
        Edge to = new Edge(vertices.get(start_node), vertices.get(end_node), distance);
        Edge from  = new Edge(vertices.get(end_node), vertices.get(start_node), distance);
        edges.add(to);
        edges.add(from);
        vertices.get(start_node).getAdj().add(to);
        vertices.get(end_node).getAdj().add(from);
    }

    private Scanner open(String path) throws FileNotFoundException {
        File nodes = new File(path);
        return new Scanner(nodes);
    }

    private String[] getData(String line) {
        return line.split(" ");
    }

    public void dump(String path) throws IOException {
        FileWriter fw = new FileWriter(path);
        for (Vertex entry : vertices)
            fw.write(entry.toString() + System.getProperty("line.separator"));
        fw.close();
    }

    public void reset() {
        for (Vertex vertex : vertices) {
            vertex.setPrediction(0.0);
            vertex.setDistance(Double.MAX_VALUE);
            vertex.setPrevious(null);
        }
    }

    public static void main(String[] args) throws IOException {
        int source = Integer.parseInt(args[0]);
        int target = Integer.parseInt(args[1]);

        // Create the graph
        Graph graph = new Graph();
        graph.initialize();
        // dump the graph on disk
        graph.dump(Constants.OUT);

        System.out.println();
        // Run Dijkstra
        Dijkstra dijkstra = new Dijkstra(graph);
        dijkstra.computeShortestPaths(source, target);
        graph.reset();

        System.out.println();
        // Run Astar
        Astar astar = new Astar(graph);
        astar.computeShortestPaths(source, target);
        graph.reset();
    }
}
