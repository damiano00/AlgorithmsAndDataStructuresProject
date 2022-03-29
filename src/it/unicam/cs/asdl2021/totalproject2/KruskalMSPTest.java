package it.unicam.cs.asdl2021.totalproject2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author Template: Luca Tesei
 * @author Implementation: Damiano Pasquini
 *
 */
class KruskalMSPTest {

    @Test
    final void testEccezioni(){
        //inizializzazione
        KruskalMSP<String> calculator = new KruskalMSP<>();
        Graph<String> directedGraph = new AdjacencyMatrixDirectedGraph<>();
        Graph<String> unWeightedGraph = new MapAdjacentListUndirectedGraph<>();
        Graph<String> negativeWeightedGraph = new MapAdjacentListUndirectedGraph<>();
        GraphNode<String> node1 = new GraphNode<>("node1");
        GraphNode<String> node2 = new GraphNode<>("node2");
        GraphNode<String> node3 = new GraphNode<>("node3");
        GraphNode<String> node4 = new GraphNode<>("node4");
        GraphEdge<String> unWeightedEdge = new GraphEdge<>(node1, node2, false, Double.NaN);
        GraphEdge<String> negativeWeightedEdge = new GraphEdge<>(node3, node4, false, -1.0);
        // controllo eccezioni
        assertThrows(NullPointerException.class, () -> calculator.computeMSP(null));
        assertThrows(IllegalArgumentException.class, () -> calculator.computeMSP(directedGraph));
        unWeightedGraph.addNode(node1);
        unWeightedGraph.addNode(node2);
        unWeightedGraph.addNode(node3);
        unWeightedGraph.addNode(node4);
        unWeightedGraph.addEdge(unWeightedEdge);
        negativeWeightedGraph.addNode(node3);
        negativeWeightedGraph.addNode(node4);
        negativeWeightedGraph.addEdge(negativeWeightedEdge);
        assertThrows(IllegalArgumentException.class, () -> calculator.computeMSP(directedGraph));
        assertThrows(IllegalArgumentException.class, () -> calculator.computeMSP(unWeightedGraph));
        assertThrows(IllegalArgumentException.class, () -> calculator.computeMSP(negativeWeightedGraph));
    }

    @Test
    final void testComputeMSP() {
        Graph<String> gr = new MapAdjacentListUndirectedGraph<String>();
        GraphNode<String> a = new GraphNode<String>("a");
        gr.addNode(a);
        GraphNode<String> b = new GraphNode<String>("b");
        gr.addNode(b);
        GraphNode<String> c = new GraphNode<String>("c");
        gr.addNode(c);
        GraphNode<String> d = new GraphNode<String>("d");
        gr.addNode(d);
        GraphNode<String> e = new GraphNode<String>("e");
        gr.addNode(e);
        GraphNode<String> f = new GraphNode<String>("f");
        gr.addNode(f);
        GraphNode<String> g = new GraphNode<String>("g");
        gr.addNode(g);
        GraphNode<String> h = new GraphNode<String>("h");
        gr.addNode(h);
        GraphNode<String> i = new GraphNode<String>("i");
        gr.addNode(i);
        gr.addEdge(new GraphEdge<String>(a, b, false, 4));
        gr.addEdge(new GraphEdge<String>(a, h, false, 8.5));
        gr.addEdge(new GraphEdge<String>(b, h, false, 11));
        gr.addEdge(new GraphEdge<String>(b, c, false, 8));
        gr.addEdge(new GraphEdge<String>(c, i, false, 2));
        gr.addEdge(new GraphEdge<String>(c, d, false, 7));
        gr.addEdge(new GraphEdge<String>(c, f, false, 4));
        gr.addEdge(new GraphEdge<String>(d, f, false, 14));
        gr.addEdge(new GraphEdge<String>(d, e, false, 9));
        gr.addEdge(new GraphEdge<String>(e, f, false, 10));
        gr.addEdge(new GraphEdge<String>(f, g, false, 2));
        gr.addEdge(new GraphEdge<String>(g, i, false, 6));
        gr.addEdge(new GraphEdge<String>(g, h, false, 1));
        gr.addEdge(new GraphEdge<String>(h, i, false, 7));
        KruskalMSP<String> alg = new KruskalMSP<String>();
        Set<GraphEdge<String>> result = new HashSet<GraphEdge<String>>();
        result.add(new GraphEdge<String>(a, b, false, 4));
        result.add(new GraphEdge<String>(b, c, false, 8));
        result.add(new GraphEdge<String>(c, i, false, 2));
        result.add(new GraphEdge<String>(c, d, false, 7));
        result.add(new GraphEdge<String>(c, f, false, 4));
        result.add(new GraphEdge<String>(d, e, false, 9));
        result.add(new GraphEdge<String>(f, g, false, 2));
        result.add(new GraphEdge<String>(g, h, false, 1));
        assertTrue(alg.computeMSP(gr).equals(result));
    }

}
