import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Graph {

    // Add edge
    static void addEdge(ArrayList<GraphEntry> g, int start_node, int end_node, float distance) {
        Edge to = new Edge(g.get(end_node).getNode(), distance);
        Edge from  = new Edge(g.get(start_node).getNode(), distance);
        g.get(start_node).getNeighbors().add(to);
        g.get(end_node).getNeighbors().add(from);
    }

    static Scanner open(String path) throws FileNotFoundException {
        File nodes = new File(path);
        return new Scanner(nodes);
    }
    static String[] getData(String line) {
        return line.split(" ");
    }

    static void initializeGraph(ArrayList<GraphEntry> g) {
        try {
            Scanner nodes_reader = open(Constants.NODES_FILE);
            Scanner edges_reader = open(Constants.EDGES_FILE);
            while (nodes_reader.hasNextLine()) {
                String[] nodes_line = getData(nodes_reader.nextLine());
                Node current_node = new Node(
                        Integer.parseInt(nodes_line[0]),
                        Float.parseFloat(nodes_line[1]),
                        Float.parseFloat(nodes_line[2])
                );
                g.add(new GraphEntry(current_node));
            }

            while (edges_reader.hasNextLine()) {
                String[] edges_line = getData(edges_reader.nextLine());
                int start_node = Integer.parseInt(edges_line[1]);
                int end_node = Integer.parseInt(edges_line[2]);
                float distance = Float.parseFloat(edges_line[3]);
                addEdge(g, start_node, end_node, distance);
            }
            nodes_reader.close();
            edges_reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Print the graph
    static void printGraph(ArrayList<GraphEntry> g) {
        for (GraphEntry entry : g) {
            System.out.println(entry);
        }
    }

    public static void main(String[] args) {

        // Create the graph
        //int V = 21048;
        ArrayList<GraphEntry> g = new ArrayList<>();

        initializeGraph(g);
        printGraph(g);
    }
}
