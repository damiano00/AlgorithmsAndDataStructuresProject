package it.unicam.cs.asdl2021.totalproject2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Template: Luca Tesei
 * @author Implementation: Damiano Pasquini
 *
 */
class FloydWarshallAllPairsShortestPathComputerTest {

    private AdjacencyMatrixDirectedGraph<String> graph;
    private FloydWarshallAllPairsShortestPathComputer<String> computer;
    private GraphNode<String> node1;
    private GraphNode<String> node2;
    private GraphNode<String> node3;
    private GraphNode<String> node4;
    private GraphEdge<String> edge1;
    private GraphEdge<String> edge2;
    private GraphEdge<String> edge3;
    private GraphEdge<String> edge4;

    private void repeatedCode(){
        // grafo composto da due nodi e un arco
        this.graph = new AdjacencyMatrixDirectedGraph<>();
        this.node1 = new GraphNode<>("firstNode");
        this.node2 = new GraphNode<>("secondNode");
        this.graph.addNode(this.node1);
        this.graph.addNode(this.node2);
        this.edge1 = new GraphEdge<>(this.node1, this.node2, true, 10.0);
        this.graph.addEdge(this.edge1);
    }

    @Test
    final void testFloydWarshallAllPairsShortestPathComputer() {
        // controllo eccezioni
        assertThrows(NullPointerException.class, () -> new FloydWarshallAllPairsShortestPathComputer<String>(null));
        assertThrows(IllegalArgumentException.class, () -> new FloydWarshallAllPairsShortestPathComputer<String>(new AdjacencyMatrixDirectedGraph<>()));
        assertThrows(IllegalArgumentException.class, () -> new FloydWarshallAllPairsShortestPathComputer<String>(new MapAdjacentListUndirectedGraph<>()));
        AdjacencyMatrixDirectedGraph<String> graphWithEdgeNaN = new AdjacencyMatrixDirectedGraph<>();
        GraphNode<String> node1 = new GraphNode<>("firstNode");
        GraphNode<String> node2 = new GraphNode<>("secondNode");
        GraphEdge<String> edge1 = new GraphEdge<>(node1, node2, true, Double.NaN);
        graphWithEdgeNaN.addNode(node1);
        graphWithEdgeNaN.addNode(node2);
        graphWithEdgeNaN.addEdge(edge1);
        assertThrows(IllegalArgumentException.class, () -> new FloydWarshallAllPairsShortestPathComputer<>(graphWithEdgeNaN));

        // controllo valori di ritorno
        this.repeatedCode();
        this.computer = new FloydWarshallAllPairsShortestPathComputer<>(this.graph);
        assertEquals(this.graph, this.computer.getGraph());
        assertFalse(this.computer.isComputed());
    }

    @Test
    final void testComputeShortestPaths() {
        // controllo eccezione cicli di peso negativo
        this.repeatedCode();
        this.node3 = new GraphNode<>("thirdNode");
        this.graph.addNode(node3);
        GraphEdge<String> edge1 = new GraphEdge<>(this.node1, this.node2, true, 10.0);
        GraphEdge<String> edge2 = new GraphEdge<>(this.node2, this.node3, true, -5.0);
        GraphEdge<String> edge3 = new GraphEdge<>(this.node3, this.node1, true, -6.0);
        this.graph.addEdge(edge1);
        this.graph.addEdge(edge2);
        this.graph.addEdge(edge3);
        FloydWarshallAllPairsShortestPathComputer<String> computer = new FloydWarshallAllPairsShortestPathComputer<>(graph);
        assertThrows(IllegalStateException.class, () -> computer.computeShortestPaths());
    }

    @Test
    final void testIsComputed() {
        this.repeatedCode();
        this.computer = new FloydWarshallAllPairsShortestPathComputer<>(graph);
        assertFalse(computer.isComputed());
        this.computer.computeShortestPaths();
        assertTrue(this.computer.isComputed());
    }

    @Test
    final void testGetGraph() {
        this.repeatedCode();
        this.computer = new FloydWarshallAllPairsShortestPathComputer<>(this.graph);
        assertNotNull(this.computer.getGraph());
    }

    @Test
    final void testGetShortestPath() {
        // controllo eccezioni
        this.repeatedCode();
        this.computer = new FloydWarshallAllPairsShortestPathComputer<>(this.graph);
        assertThrows(NullPointerException.class, () -> this.computer.getShortestPath(null, null));
        this.node3 = new GraphNode<>("thirdNode");
        this.node4 = new GraphNode<>("fourthNode");
        this.graph.addNode(this.node3);
        assertThrows(IllegalArgumentException.class, () -> this.computer.getShortestPath(this.node3, this.node4));
        assertThrows(IllegalStateException.class, () -> this.computer.getShortestPath(this.node1, this.node2));

        // controllo valori di ritorno
        this.graph.addNode(this.node4);
        this.edge2 = new GraphEdge<>(this.node1, node3, true, 5.0);
        this.edge3 = new GraphEdge<>(this.node2, node4, true, 5.0);
        this.edge4 = new GraphEdge<>(this.node3, this.node4, true, 5.0);
        this.graph.addEdge(this.edge2);
        this.graph.addEdge(this.edge3);
        this.graph.addEdge(this.edge4);
        this.computer = new FloydWarshallAllPairsShortestPathComputer<>(this.graph);
        this.computer.computeShortestPaths();
        List<GraphEdge<String>> path = new ArrayList<>();
        path.add(this.edge2);
        path.add(this.edge4);
        assertEquals(path, this.computer.getShortestPath(this.node1, this.node4));
    }

    @Test
    final void testGetShortestPathCost() {
        // controllo eccezioni
        this.repeatedCode();
        this.node3 = new GraphNode<>("thirdNode");
        this.node4 = new GraphNode<>("fourthNode");
        this.graph.addNode(this.node3);
        this.graph.addNode(this.node4);
        this.edge2 = new GraphEdge<>(this.node1, node3, true, 5.0);
        this.edge3 = new GraphEdge<>(this.node2, node4, true, 5.0);
        this.edge4 = new GraphEdge<>(node3, node4, true, 5.0);
        this.graph.addEdge(this.edge2);
        this.graph.addEdge(this.edge3);
        this.graph.addEdge(this.edge4);
        this.computer = new FloydWarshallAllPairsShortestPathComputer<>(this.graph);
        assertThrows(IllegalStateException.class, () -> this.computer.getShortestPathCost(this.node1, this.node4));
        this.computer.computeShortestPaths();
        assertThrows(NullPointerException.class, () -> this.computer.getShortestPathCost(null, null));
        GraphNode<String> node5 = new GraphNode<>("Node5");
        assertThrows(IllegalArgumentException.class, () -> this.computer.getShortestPathCost(this.node1, node5));

        // controllo valori di ritorno
        assertEquals(10.0, this.computer.getShortestPathCost(this.node1, this.node4));
    }

    @Test
    final void testPrintPath() {
        this.repeatedCode();
        this.node3 = new GraphNode<>("thirdNode");
        this.node4 = new GraphNode<>("fourthNode");
        this.graph.addNode(this.node3);
        this.graph.addNode(this.node4);
        this.edge2 = new GraphEdge<>(this.node1, node3, true, 5.0);
        this.edge3 = new GraphEdge<>(this.node2, node4, true, 5.0);
        this.edge4 = new GraphEdge<>(node3, node4, true, 5.0);
        this.graph.addEdge(this.edge2);
        this.graph.addEdge(this.edge3);
        this.graph.addEdge(this.edge4);
        this.computer = new FloydWarshallAllPairsShortestPathComputer<>(this.graph);
        this.computer.computeShortestPaths();

        // controllo eccezioni
        assertThrows(NullPointerException.class, () -> this.computer.printPath(null));
        ArrayList<GraphEdge<String>> path = new ArrayList<>();
        assertEquals("[ ]", this.computer.printPath(path));

        // controllo esecuzione
        List<GraphEdge<String>> path2 = new ArrayList<>();
        path.add(this.edge2);
        path.add(this.edge4);
        assertNotNull(this.computer.printPath(path2));
    }

    @Test
    final void testGetCostMatrix() {
        this.repeatedCode();
        this.node3 = new GraphNode<>("thirdNode");
        this.node4 = new GraphNode<>("fourthNode");
        this.graph.addNode(this.node3);
        this.graph.addNode(this.node4);
        this.edge2 = new GraphEdge<>(this.node1, node3, true, 5.0);
        this.edge3 = new GraphEdge<>(this.node2, node4, true, 5.0);
        this.edge4 = new GraphEdge<>(node3, node4, true, 5.0);
        this.graph.addEdge(this.edge2);
        this.graph.addEdge(this.edge3);
        this.graph.addEdge(this.edge4);
        this.computer = new FloydWarshallAllPairsShortestPathComputer<>(this.graph);
        this.computer.computeShortestPaths();
        assertNotNull(this.computer.getCostMatrix());
    }

    @Test
    final void testGetPredecessorMatrix() {
        this.repeatedCode();
        this.node3 = new GraphNode<>("thirdNode");
        this.node4 = new GraphNode<>("fourthNode");
        this.graph.addNode(this.node3);
        this.graph.addNode(this.node4);
        this.edge2 = new GraphEdge<>(this.node1, node3, true, 5.0);
        this.edge3 = new GraphEdge<>(this.node2, node4, true, 5.0);
        this.edge4 = new GraphEdge<>(node3, node4, true, 5.0);
        this.graph.addEdge(this.edge2);
        this.graph.addEdge(this.edge3);
        this.graph.addEdge(this.edge4);
        this.computer = new FloydWarshallAllPairsShortestPathComputer<>(this.graph);
        this.computer.computeShortestPaths();
        assertNotNull(this.computer.getPredecessorMatrix());
    }

}
