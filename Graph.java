import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

class Graph {

    private final ArrayList<GraphEntry> g = new ArrayList<>();

    private void addEdge(int start_node, int end_node, float distance) {
        Edge to = new Edge(g.get(end_node).getNode(), distance);
        Edge from  = new Edge(g.get(start_node).getNode(), distance);
        g.get(start_node).getNeighbors().add(to);
        g.get(end_node).getNeighbors().add(from);
    }

    private Scanner open(String path) throws FileNotFoundException {
        File nodes = new File(path);
        return new Scanner(nodes);
    }
    private String[] getData(String line) {
        return line.split(" ");
    }

    public void initialize() {
        try {
            Scanner nodes_reader = open(Constants.NODES_FILE);
            Scanner edges_reader = open(Constants.EDGES_FILE);
            while (nodes_reader.hasNextLine()) {
                String[] nodes_line = getData(nodes_reader.nextLine());
                Vertex vertex = new Vertex(nodes_line[0], Float.parseFloat(nodes_line[1]), Float.parseFloat(nodes_line[2]));
                g.add(new GraphEntry(vertex));
            }

            while (edges_reader.hasNextLine()) {
                String[] edges_line = getData(edges_reader.nextLine());
                int start_node = Integer.parseInt(edges_line[1]);
                int end_node = Integer.parseInt(edges_line[2]);
                float distance = Float.parseFloat(edges_line[3]);
                addEdge(start_node, end_node, distance);
            }
            nodes_reader.close();
            edges_reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void dump(String path) throws FileNotFoundException {
        PrintStream fileStream = new PrintStream(path);
        System.setOut(fileStream);
        for (GraphEntry entry : g) {
            System.out.println(entry);
        }
        fileStream.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        // Create the graph
        Graph graph = new Graph();
        graph.initialize();
        // dump the graph on disk
        graph.dump(Constants.OUT);
    }
}
