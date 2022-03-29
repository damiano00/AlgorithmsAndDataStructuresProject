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
class AdjacencyMatrixDirectedGraphTest {

    Graph<String> graph = new AdjacencyMatrixDirectedGraph<>();
    GraphNode<String> node1 = new GraphNode<>("firstNode");
    GraphNode<String> node2 = new GraphNode<>("secondNode");
    GraphEdge<String> edge = new GraphEdge<>(node1, node2, true, 10);

    private void repeatedCode(){
        graph.clear();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addEdge(edge);
    }

    @Test
    final void testNodeCount() {
        this.graph.clear();
        assertEquals(0, graph.nodeCount());
        graph.addNode(new GraphNode<>("firstNode"));
        assertEquals(1, graph.nodeCount());
        graph.addNode(new GraphNode<>("secondNode"));
        assertEquals(2, graph.nodeCount());
    }

    @Test
    final void testEdgeCount() {
        this.repeatedCode();
        assertEquals(1, graph.edgeCount());
        graph.addNode(new GraphNode<>("thirdNode"));
        GraphEdge<String> secondEdgeToAdd = new GraphEdge<>(node2 , graph.getNodeOf("thirdNode"), true, 20);
        graph.addEdge(secondEdgeToAdd);
        assertEquals(2, graph.edgeCount());
    }

    @Test
    final void testClear() {
        graph.addNode(node1);
        graph.addNode(node2);
        GraphEdge<String> edge = new GraphEdge<>(node1 ,node2, true, 10);
        graph.addEdge(edge);
        graph.clear();
        assertTrue(graph.isEmpty());
    }

    @Test
    final void testIsDirected() {
        assertTrue(graph.isDirected());
    }

    @Test
    final void testGetNodes() {
        this.repeatedCode();
        HashSet<GraphNode<String>> tmpHashSet = new HashSet<>();
        tmpHashSet.add(node1);
        tmpHashSet.add(node2);
        assertEquals(tmpHashSet, graph.getNodes());
    }

    @Test
    final void testAddNode() {
        // controllo eccezioni
        this.graph.clear();
        assertThrows(NullPointerException.class, () -> this.graph.addNode(null));
        // controllo valori di ritorno
        this.graph.clear();
        assertEquals(0, graph.nodeCount());
        this.graph.addNode(node1);
        assertEquals(1, graph.nodeCount());
        assertEquals(graph.getNodeAtIndex(0), node1);
        this.graph.addNode(node2);
        assertEquals(2, graph.nodeCount());
        assertEquals(graph.getNodeAtIndex(1), node2);
    }

    @Test
    final void testRemoveNode() {
        // controllo lancio eccezione
        assertThrows(UnsupportedOperationException.class, () -> this.graph.removeNode(node1));
    }

    @Test
    final void testContainsNode() {
        // controllo eccezioni
        this.repeatedCode();
        assertThrows(NullPointerException.class, () -> this.graph.containsNode(null));
        // controllo valori di ritorno
        graph.clear();
        graph.addNode(node1);
        assertTrue(graph.containsNode(node1));
    }

    @Test
    final void testGetNodeOf() {
        // controllo eccezioni
        this.repeatedCode();
        assertThrows(NullPointerException.class, () -> this.graph.getNodeOf(null));
        // controllo valori di ritorno
        graph.clear();
        graph.addNode(node1);
        assertSame(node1, graph.getNodeOf(node1.getLabel()));
    }

    @Test
    final void testGetNodeIndexOf() {
        // controllo eccezioni
        this.repeatedCode();
        assertThrows(NullPointerException.class, () -> this.graph.getNodeIndexOf(null));
        assertThrows(IllegalArgumentException.class, () -> this.graph.getNodeIndexOf("notExistingLabel"));
        Graph<String> graph2 = new MapAdjacentListUndirectedGraph<>();
        GraphNode<String> undirectedGraphNode = new GraphNode<>("nodeToUseInUndirectedGraph");
        graph2.addNode(undirectedGraphNode);
        assertThrows(UnsupportedOperationException.class, () -> graph2.getNodeIndexOf(undirectedGraphNode.getLabel()));
        // controllo valori di ritorno
        graph.addNode(node1);
        assertEquals(0,graph.getNodeIndexOf(node1.getLabel()));
        graph.addNode(node2);
        assertEquals(1,graph.getNodeIndexOf(node2.getLabel()));
        graph.addNode(new GraphNode<>("thirdNode"));
        assertEquals(2, graph.getNodeIndexOf("thirdNode"));
    }

    @Test
    final void testGetNodeAtIndex() {
        // controllo eccezioni
        this.repeatedCode();
        assertThrows(IndexOutOfBoundsException.class, () -> this.graph.getNodeAtIndex(2));
        // controllo valori di ritorno
        this.graph.clear();
        graph.addNode(node1);
        graph.addNode(node2);
        assertSame(node2, graph.getNodeAtIndex(1));
    }

    @Test
    final void testGetEdge() {
        // controllo eccezioni
        this.repeatedCode();
        assertThrows(NullPointerException.class, () -> this.graph.getEdge(node1, null));
        assertThrows(IllegalArgumentException.class, () -> this.graph.getEdge(node1, new GraphNode<>("notExistingNode")));
        // controllo valori di ritorno
        graph.clear();
        this.repeatedCode();
        assertSame(this.edge, this.graph.getEdge(this.node1, this.node2));
    }

    @Test
    final void testGetEdgeAtNodeIndexes() {
        // controllo eccezioni
        this.repeatedCode();
        assertThrows(IndexOutOfBoundsException.class, () -> this.graph.getEdgeAtNodeIndexes(0,2));
        // controllo valori di ritorno
        this.repeatedCode();
        assertEquals(0, graph.getNodeIndexOf("firstNode"));
        assertEquals(1, graph.getNodeIndexOf("secondNode"));
        assertEquals(edge, graph.getEdgeAtNodeIndexes(graph.getNodeIndexOf(node1.getLabel()), graph.getNodeIndexOf(node2.getLabel())));
    }

    @Test
    final void testGetAdjacentNodesOf() {
        // controllo eccezioni
        this.repeatedCode();
        assertThrows(IllegalArgumentException.class, () -> this.graph.getAdjacentNodesOf(new GraphNode<>("notExistingNode")));
        assertThrows(NullPointerException.class, () -> this.graph.getAdjacentNodesOf(null));
        // controllo valori di ritorno
        this.repeatedCode();
        Set<GraphNode<String>> set = new HashSet<>();
        set.add(node2);
        assertEquals(set, this.graph.getAdjacentNodesOf(node1));
    }

    @Test
    final void testGetPredecessorNodesOf() {
        // controllo eccezioni
        this.repeatedCode();
        assertThrows(IllegalArgumentException.class, () -> this.graph.getPredecessorNodesOf(new GraphNode<>("notExistingNode")));
        assertThrows(NullPointerException.class, () -> this.graph.getPredecessorNodesOf(null));
        // controllo valori di ritorno
        this.repeatedCode();
        Set<GraphNode<String>> set = new HashSet<>();
        set.add(node1);
        assertEquals(set, this.graph.getPredecessorNodesOf(node2));
    }

    @Test
    final void testGetEdges() {
        this.repeatedCode();
        HashSet<GraphEdge<String>> setToReturn = new HashSet<>();
        setToReturn.add(this.edge);
        assertTrue(setToReturn.contains(this.edge));
    }

    @Test
    final void testAddEdge() {
        // controllo eccezioni
        this.repeatedCode();
        GraphNode<String> notExistingNode = new GraphNode<>("notExistingNode");
        GraphEdge<String> edgeWithNotExistingNode = new GraphEdge<>(node1,notExistingNode, true, 10);
        GraphEdge<String> notDirectedEdge = new GraphEdge<>(node1,node2, false, 10);
        assertThrows(IllegalArgumentException.class, () -> this.graph.addEdge(edgeWithNotExistingNode));
        assertThrows(IllegalArgumentException.class, () -> this.graph.addEdge(notDirectedEdge));
        assertThrows(NullPointerException.class, () -> this.graph.getPredecessorNodesOf(null));
        // controllo valori di ritorno
        this.repeatedCode();
        assertEquals(1, this.graph.edgeCount());
        assertTrue(this.graph.containsEdge(edge));
    }

    @Test
    final void testRemoveEdge() {
        // controllo eccezioni
        this.repeatedCode();
        GraphNode<String> notExistingNode = new GraphNode<>("notExistingNode");
        GraphEdge<String> edgeWithNotExistingNode = new GraphEdge<>(node1,notExistingNode, true, 10);
        assertThrows(IllegalArgumentException.class, () -> this.graph.removeEdge(edgeWithNotExistingNode));
        assertThrows(NullPointerException.class, () -> this.graph.removeEdge(null));
        // controllo valori di ritorno
        this.repeatedCode();
        assertTrue(this.graph.removeEdge(this.edge));
        assertNull(this.graph.getEdgeAtNodeIndexes(graph.getNodeIndexOf(node1.getLabel()), graph.getNodeIndexOf(node2.getLabel())));
    }

    @Test
    final void testContainsEdge() {
        // controllo eccezioni
        this.repeatedCode();
        GraphNode<String> notExistingNode = new GraphNode<>("notExistingNode");
        GraphEdge<String> edgeWithNotExistingNode = new GraphEdge<>(node1,notExistingNode, true, 10);
        assertThrows(IllegalArgumentException.class, () -> this.graph.containsEdge(edgeWithNotExistingNode));
        assertThrows(NullPointerException.class, () -> this.graph.containsEdge(null));
        // controllo valori di ritorno
        this.repeatedCode();
        assertTrue(this.graph.containsEdge(this.edge));
    }

    @Test
    final void testGetEdgesOf() {
        // controllo eccezioni
        this.repeatedCode();
        GraphNode<String> notExistingNode = new GraphNode<>("notExistingNode");
        assertThrows(IllegalArgumentException.class, () -> this.graph.getEdgesOf(notExistingNode));
        assertThrows(NullPointerException.class, () -> this.graph.getEdgesOf(null));
        // controllo valori di ritorno
        this.repeatedCode();
        Set<GraphEdge<String>> setToCheck = new HashSet<>();
        setToCheck.add(edge);
        assertEquals(setToCheck, graph.getEdgesOf(node1));
    }

    @Test
    final void testGetIngoingEdgesOf() {
        // controllo eccezioni
        this.repeatedCode();
        GraphNode<String> notExistingNode = new GraphNode<>("notExistingNode");
        Graph<String> graph2 = new MapAdjacentListUndirectedGraph<>();
        GraphNode<String> undirectedGraphNode = new GraphNode<>("nodeToUseInUndirectedGraph");
        graph2.addNode(undirectedGraphNode);
        assertThrows(IllegalArgumentException.class, () -> this.graph.getIngoingEdgesOf(notExistingNode));
        assertThrows(UnsupportedOperationException.class, () -> graph2.getIngoingEdgesOf(undirectedGraphNode));
        assertThrows(NullPointerException.class, () -> this.graph.getIngoingEdgesOf(null));
        // controllo valori di ritorno
        this.repeatedCode();
        assertTrue(this.graph.getIngoingEdgesOf(node2).contains(edge));
    }

    @Test
    final void testAdjacencyMatrixDirectedGraph() {
        Graph<String> graph2 = new AdjacencyMatrixDirectedGraph<>();
        assertTrue(graph2.isEmpty() && graph2.isDirected());
    }

    @Test
    final void testSize() {
        this.repeatedCode();
        assertEquals(3, this.graph.size());
    }

    @Test
    final void testIsEmpty() {
        this.repeatedCode();
        assertFalse(this.graph.isEmpty());
        this.graph.clear();
        assertTrue(this.graph.isEmpty());
    }

    @Test
    final void testGetDegreeOf() {
        // controllo eccezioni
        this.repeatedCode();
        GraphNode<String> notExistingNode = new GraphNode<>("notExistingNode");
        assertThrows(IllegalArgumentException.class, () -> this.graph.getDegreeOf(notExistingNode));
        assertThrows(NullPointerException.class, () -> this.graph.getDegreeOf(null));
        // controllo valori di ritorno
        this.repeatedCode();
        assertEquals(1, this.graph.getDegreeOf(node2));
    }

}
