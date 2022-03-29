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
class BellmanFordShortestPathComputerTest {


    AdjacencyMatrixDirectedGraph<String> directedGraph = new AdjacencyMatrixDirectedGraph<>();
    BellmanFordShortestPathComputer<String> shortestPathComputer;
    private final GraphNode<String> firstNode = new GraphNode<>("firstNode");
    private final GraphNode<String> secondNode = new GraphNode<>("secondNode");
    private final GraphNode<String> thirdNode = new GraphNode<>("thirdNode");
    private final GraphNode<String> fourthNode = new GraphNode<>("fourthNode");
    private final GraphNode<String> fifthNode = new GraphNode<>("fifthNode");
    private final GraphEdge<String> firstEdge = new GraphEdge<>(firstNode, secondNode, true);
    private final GraphEdge<String> secondEdge = new GraphEdge<>(firstNode, thirdNode, true);
    private final GraphEdge<String> thirdEdge = new GraphEdge<>(secondNode, fourthNode, true);
    private final GraphEdge<String> fourthEdge = new GraphEdge<>(secondNode, fifthNode, true);

    private void repeatedCode(){
        this.directedGraph.clear();
        this.directedGraph.addNode(firstNode);
        this.directedGraph.addNode(secondNode);
        this.directedGraph.addNode(thirdNode);
        this.directedGraph.addNode(fourthNode);
        this.directedGraph.addNode(fifthNode);
        this.directedGraph.addEdge(firstEdge);
        this.firstEdge.setWeight(1.0);
        this.directedGraph.addEdge(secondEdge);
        this.secondEdge.setWeight(2.0);
        this.directedGraph.addEdge(thirdEdge);
        this.thirdEdge.setWeight(3.0);
        this.directedGraph.addEdge(fourthEdge);
        this.fourthEdge.setWeight(4.0);
        this.shortestPathComputer = new BellmanFordShortestPathComputer<>(directedGraph);
    }

    @Test
    final void testBellmanFordShortestPathComputer() {
        //controllo lancio eccezioni
        // input grafo nullo
        assertThrows(NullPointerException.class, () -> new BellmanFordShortestPathComputer<String>(null));
        // input grafo vuoto
        Graph<String> emptyGraph = new AdjacencyMatrixDirectedGraph<>();
        assertThrows(IllegalArgumentException.class, () -> new BellmanFordShortestPathComputer<String>(emptyGraph));
        // input grafo non orientato (undirected)
        Graph<String> notDirectedGraph = new MapAdjacentListUndirectedGraph<>();
        assertThrows(IllegalArgumentException.class, () -> new BellmanFordShortestPathComputer<String>(notDirectedGraph));
        // input grafo non pesato (contenente almeno un arco non pesato)
        Graph<String> notWeightedGraph = new AdjacencyMatrixDirectedGraph<>();
        GraphNode<String> firstNode = new GraphNode<>("firstNode");
        GraphNode<String> secondNode = new GraphNode<>("secondNode");
        notWeightedGraph.addNode(firstNode);
        notWeightedGraph.addNode(secondNode);
        GraphEdge<String> firstEdge = new GraphEdge<>(firstNode, secondNode, true);
        notWeightedGraph.addEdge(firstEdge);
        assertThrows(IllegalArgumentException.class, () -> new BellmanFordShortestPathComputer<String>(notWeightedGraph));
    }

    @Test
    final void testComputeShortestPathsFrom() {
        // controllo lancio eccezioni
        this.repeatedCode();
        assertThrows(NullPointerException.class, () -> this.shortestPathComputer.computeShortestPathsFrom(null));
        GraphNode<String> notExistingNode = new GraphNode<String>("notExistingNode");
        assertThrows(IllegalArgumentException.class, () -> this.shortestPathComputer.computeShortestPathsFrom(notExistingNode));
    }

    @Test
    final void testIsComputed() {
        this.repeatedCode();
        // controllo valori di ritorno
        assertEquals(5, this.directedGraph.nodeCount());
        shortestPathComputer.computeShortestPathsFrom(firstNode);
        assertTrue(shortestPathComputer.isComputed());
    }

    @Test
    final void testGetLastSource() {
        this.repeatedCode();
        // controllo valori di ritorno
        shortestPathComputer.computeShortestPathsFrom(firstNode);
        assertEquals(firstNode, this.shortestPathComputer.getLastSource());
    }

    @Test
    final void testGetGraph() {
        this.repeatedCode();
        assertEquals(this.directedGraph, shortestPathComputer.getGraph());
    }

    @Test
    final void testGetShortestPathTo() {
        this.repeatedCode();
        // controllo lancio eccezioni
            // controllo lancio eccezione se inserito nodo null
        assertThrows(NullPointerException.class, () -> this.shortestPathComputer.getShortestPathTo(null));

            // controllo lancio eccezione se inserito nodo non esistente
        GraphNode<String> notExistingNode = new GraphNode<>("notExistingNode");
        assertThrows(IllegalArgumentException.class, () -> this.shortestPathComputer.getShortestPathTo(notExistingNode));

            // controllo lancio eccezione se inserito nodo per cui non è stato calcolato un cammino minimo
        AdjacencyMatrixDirectedGraph<String> notComputedDirectedGraph = new AdjacencyMatrixDirectedGraph<>();
        notComputedDirectedGraph.addNode(firstNode);
        notComputedDirectedGraph.addNode(secondNode);
        notComputedDirectedGraph.addNode(thirdNode);
        notComputedDirectedGraph.addNode(fourthNode);
        notComputedDirectedGraph.addNode(fifthNode);
        notComputedDirectedGraph.addEdge(firstEdge);
        notComputedDirectedGraph.addEdge(secondEdge);
        notComputedDirectedGraph.addEdge(thirdEdge);
        notComputedDirectedGraph.addEdge(fourthEdge);
        BellmanFordShortestPathComputer<String> shortestPathComputer2 = new BellmanFordShortestPathComputer<>(notComputedDirectedGraph);
        assertThrows(IllegalStateException.class, () -> shortestPathComputer2.getShortestPathTo(fifthNode));

        // controllo valori di ritorno
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> ns = new GraphNode<String>("s");
        g.addNode(ns);
        GraphNode<String> nu = new GraphNode<String>("u");
        g.addNode(nu);
        GraphEdge<String> esu = new GraphEdge<String>(ns, nu, true, 10.1);
        g.addEdge(esu);
        GraphNode<String> nx = new GraphNode<String>("x");
        g.addNode(nx);
        GraphEdge<String> esx = new GraphEdge<String>(ns, nx, true, 5.12);
        g.addEdge(esx);
        // modified with -2 to create a negative weight but not a negative weight cycle
        GraphEdge<String> eux = new GraphEdge<String>(nu, nx, true, -2.05);
        g.addEdge(eux);
        GraphEdge<String> exu = new GraphEdge<String>(nx, nu, true, 3.04);
        g.addEdge(exu);
        GraphNode<String> ny = new GraphNode<String>("y");
        g.addNode(ny);
        GraphEdge<String> exy = new GraphEdge<String>(nx, ny, true, 2.0);
        g.addEdge(exy);
        GraphEdge<String> eys = new GraphEdge<String>(ny, ns, true, 7.03);
        g.addEdge(eys);
        GraphNode<String> nv = new GraphNode<String>("v");
        g.addNode(nv);
        GraphEdge<String> euv = new GraphEdge<String>(nu, nv, true, 1.0);
        g.addEdge(euv);
        GraphEdge<String> exv = new GraphEdge<String>(nx, nv, true, 9.05);
        g.addEdge(exv);
        GraphEdge<String> eyv = new GraphEdge<String>(ny, nv, true, 6.0);
        g.addEdge(eyv);
        GraphEdge<String> evy = new GraphEdge<String>(nv, ny, true, 4.07);
        g.addEdge(evy);
        BellmanFordShortestPathComputer<String> c = new BellmanFordShortestPathComputer<>(g);
        GraphNode<String> nsTest = new GraphNode<String>("s");
        c.computeShortestPathsFrom(nsTest);
        List<GraphEdge<String>> pathTest = new ArrayList<GraphEdge<String>>();
        assertTrue(c.getShortestPathTo(nsTest).equals(pathTest));
        GraphNode<String> nuTest = new GraphNode<String>("u");
        GraphNode<String> nxTest = new GraphNode<String>("x");
        GraphEdge<String> esxTest = new GraphEdge<String>(nsTest, nxTest, true, 5.12);
        pathTest.add(esxTest);
        assertTrue(c.getShortestPathTo(nxTest).equals(pathTest));
        GraphEdge<String> exuTest = new GraphEdge<String>(nxTest, nuTest, true,3.04);
        pathTest.add(exuTest);
        assertTrue(c.getShortestPathTo(nuTest).equals(pathTest));
        GraphNode<String> nvTest = new GraphNode<String>("v");
        GraphEdge<String> euvTest = new GraphEdge<String>(nuTest, nvTest, true, 1.0);
        pathTest.add(euvTest);
        assertTrue(c.getShortestPathTo(nvTest).equals(pathTest));
        pathTest.clear();
        pathTest.add(esxTest);
        GraphNode<String> nyTest = new GraphNode<String>("y");
        GraphEdge<String> exyTest = new GraphEdge<String>(nxTest, nyTest, true, 2.0);
        pathTest.add(exyTest);
        assertTrue(c.getShortestPathTo(nyTest).equals(pathTest));
    }

}
