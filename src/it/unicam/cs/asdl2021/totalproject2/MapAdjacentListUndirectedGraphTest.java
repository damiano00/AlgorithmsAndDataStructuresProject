package it.unicam.cs.asdl2021.totalproject2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Template: Luca Tesei
 * @author Implementation: Damiano Pasquini
 *
 */
class MapAdjacentListUndirectedGraphTest {

    Graph<String> graph = new MapAdjacentListUndirectedGraph<>();
    GraphNode<String> node1 = new GraphNode<>("firstNode");
    GraphNode<String> node2 = new GraphNode<>("secondNode");
    GraphEdge<String> edge = new GraphEdge<>(node1, node2, false, 10);

    public void repeatedCode(){
        this.graph.clear();
        this.graph.addNode(node1);
        this.graph.addNode(node2);
        this.graph.addEdge(edge);
    }

    @Test
    final void testNodeCount() {
        assertEquals(0,this.graph.nodeCount());
        this.graph.addNode(node1);
        assertEquals(1,this.graph.nodeCount());
        this.graph.addNode(node2);
        assertEquals(2,this.graph.nodeCount());
    }

    @Test
    final void testEdgeCount() {
        assertEquals(0,this.graph.edgeCount());
        this.repeatedCode();
        assertEquals(1,this.graph.edgeCount());
    }

    @Test
    final void testClear() {
        this.repeatedCode();
        this.graph.clear();
        assertFalse(this.graph.containsNode(node1) && this.graph.containsNode(node2));
    }

    @Test
    final void testIsDirected() {
        assertFalse(this.graph.isDirected());
    }

    @Test
    final void testGetNodes() {
        this.repeatedCode();
        Set<GraphNode<String>> set = new HashSet<>();
        set.add(node1);
        set.add(node2);
        assertEquals(set, this.graph.getNodes());
    }

    @Test
    final void testAddNode() {
        // controllo eccezioni
        assertThrows(NullPointerException.class, () -> this.graph.addNode(null));
        // controllo valori di ritorno
        assertEquals(0, this.graph.nodeCount());
        this.graph.addNode(node1);
        assertEquals(1, this.graph.nodeCount());
        this.graph.addNode(node2);
        assertEquals(2, this.graph.nodeCount());
    }

    @Test
    final void testRemoveNode() {
        // controllo eccezioni
        assertThrows(NullPointerException.class, () -> this.graph.removeNode(null));
        // controllo valori di ritorno
        this.graph.addNode(node1);
        assertEquals(1, this.graph.nodeCount());
        assertTrue(this.graph.containsNode(node1));
        this.graph.removeNode(node1);
        assertEquals(0, this.graph.nodeCount());
        assertFalse(this.graph.containsNode(node1));
    }

    @Test
    final void testContainsNode() {
        // controllo eccezioni
        assertThrows(NullPointerException.class, () -> this.graph.containsNode(null));
        // controllo valori di ritorno
        this.repeatedCode();
        assertTrue(this.graph.containsNode(node1));
        assertTrue(this.graph.containsNode(node2));
    }

    @Test
    final void testGetNodeOf() {
        // controllo eccezioni
        assertThrows(NullPointerException.class, () -> this.graph.getNodeOf(null));
        // controllo valori di ritorno
        this.repeatedCode();
        assertEquals(node1, this.graph.getNodeOf("firstNode"));
    }

    @Test
    final void testGetNodeIndexOf() {
        // controllo eccezioni
        assertThrows(UnsupportedOperationException.class, () -> this.graph.getNodeIndexOf(node1.getLabel()));
    }

    @Test
    final void testGetNodeAtIndex() {
        // controllo eccezioni
        assertThrows(UnsupportedOperationException.class, () -> this.graph.getNodeAtIndex(0));
    }

    @Test
    final void testGetEdge() {
        // controllo eccezioni
        assertThrows(NullPointerException.class, () -> this.graph.getEdge(node1, null));
        GraphNode<String> notExistingNode = new GraphNode<>("notExistingNode");
        assertThrows(IllegalArgumentException.class, () -> this.graph.getEdge(node1, notExistingNode));
        // controllo valori di ritorno
        this.repeatedCode();
        assertEquals(edge, this.graph.getEdge(node1, node2));
    }

    @Test
    final void testGetEdgeAtNodeIndexes() {
        assertThrows(UnsupportedOperationException.class, () -> this.graph.getEdgeAtNodeIndexes(0,1));
    }

    @Test
    final void testGetAdjacentNodesOf() {
        // controllo eccezioni
        assertThrows(NullPointerException.class, () -> this.graph.getAdjacentNodesOf(null));
        GraphNode<String> notExistingNode = new GraphNode<>("notExistingNode");
        assertThrows(IllegalArgumentException.class, () -> this.graph.getAdjacentNodesOf(notExistingNode));
        // controllo valori di ritorno
        this.repeatedCode();
        Set<GraphNode<String>> set = new HashSet<>();
        set.add(node2);
        assertEquals(set, this.graph.getAdjacentNodesOf(node1));
    }

    @Test
    final void testGetPredecessorNodesOf() {
        // controllo eccezioni
        assertThrows(UnsupportedOperationException.class, () -> this.graph.getPredecessorNodesOf(node2));
    }

    @Test
    final void testGetEdges() {
        this.repeatedCode();
        Set<GraphEdge<String>> set = new HashSet<>();
        set.add(edge);
        assertEquals(set, this.graph.getEdges());
    }

    @Test
    final void testAddEdge() {
        // controllo eccezioni
        this.repeatedCode();
        assertThrows(NullPointerException.class, () -> this.graph.addEdge(null));
        GraphNode<String> notExistingNode = new GraphNode<>("notExistingNode");
        GraphEdge<String> wrongEdge = new GraphEdge<>(node1, notExistingNode, false, 10);
        assertThrows(IllegalArgumentException.class, () -> this.graph.addEdge(wrongEdge));
        GraphEdge<String> wrongEdge2 = new GraphEdge<>(node1, node2, true, 10);
        assertThrows(IllegalArgumentException.class, () -> this.graph.addEdge(wrongEdge2));
        // controllo valori di ritorno
        this.repeatedCode();
        assertTrue(this.graph.containsEdge(edge));
        assertEquals(1,this.graph.edgeCount());
    }

    @Test
    final void testRemoveEdge() {
        // controllo eccezioni
        this.repeatedCode();
        GraphNode<String> notExistingNode = new GraphNode<>("notExistingNode");
        GraphEdge<String> wrongEdge = new GraphEdge<>(node1, notExistingNode, false, 10);
        GraphEdge<String> directedGraphEdge = new GraphEdge<>(node1 ,node2, true, 20);
        assertThrows(IllegalArgumentException.class, () -> this.graph.removeEdge(wrongEdge));
        assertThrows(NullPointerException.class, () -> this.graph.removeEdge(null));
        assertThrows(UnsupportedOperationException.class, () -> this.graph.removeEdge(directedGraphEdge));
        // controllo valori di ritorno
        this.repeatedCode();
        this.graph.removeEdge(edge);
        assertFalse(this.graph.containsEdge(edge));
    }

    @Test
    final void testContainsEdge() {
        // controllo eccezioni
        this.repeatedCode();
        GraphNode<String> notExistingNode = new GraphNode<>("notExistingNode");
        GraphEdge<String> wrongEdge = new GraphEdge<>(node1, notExistingNode, false, 10);
        assertThrows(NullPointerException.class, () -> this.graph.containsEdge(null));
        assertThrows(IllegalArgumentException.class, () -> this.graph.containsEdge(wrongEdge));
        // controllo valori di ritorno
        this.repeatedCode();
        assertTrue(this.graph.containsEdge(edge));
        this.graph.removeEdge(edge);
        assertFalse(this.graph.containsEdge(edge));
    }

    @Test
    final void testGetEdgesOf() {
        // controllo eccezioni
        this.graph.clear();
        GraphNode<String> notExistingNode = new GraphNode<>("notExistingNode");
        assertThrows(NullPointerException.class, () -> this.graph.getEdgesOf(null));
        assertThrows(IllegalArgumentException.class, () -> this.graph.getEdgesOf(notExistingNode));
        // controllo valori di ritorno
        this.repeatedCode();
        Set<GraphEdge<String>> set = new HashSet<>();
        set.add(edge);
        assertEquals(set, this.graph.getEdgesOf(node1));
        assertEquals(set, this.graph.getEdgesOf(node2));
    }

    @Test
    final void testGetIngoingEdgesOf() {
        assertThrows(UnsupportedOperationException.class, () -> this.graph.getIngoingEdgesOf(node1));
    }

    @Test
    final void testMapAdjacentListUndirectedGraph() {
        Graph<String> graph2 = new MapAdjacentListUndirectedGraph<>();
        assertTrue(graph2.isEmpty() && !graph2.isDirected());
    }

}
