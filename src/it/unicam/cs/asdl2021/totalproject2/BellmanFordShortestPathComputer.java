package it.unicam.cs.asdl2021.totalproject2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementazione dell' algoritmo di Bellman-Ford per il calcolo di cammini
 * minimi a sorgente singola in un grafo pesato che può contenere anche pesi
 * negativi, ma non cicli di peso negativo.
 * 
 * @author Template: Luca Tesei
 * @author Implementation: Damiano Pasquini
 *
 * @param <L>
 *                etichette dei nodi del grafo
 */
public class BellmanFordShortestPathComputer<L>
        implements SingleSourceShortestPathComputer<L> {

    private Graph<L> graph;
    private GraphNode<L> lastSourceNode;
    private boolean isComputed = false;

    /**
     * Crea un calcolatore di cammini minimi a sorgente singola per un grafo
     * orientato e pesato.
     * 
     * @param graph
     *                  il grafo su cui opera il calcolatore di cammini minimi
     * @throws NullPointerException
     *                                      se il grafo passato è nullo
     * 
     * @throws IllegalArgumentException
     *                                      se il grafo passato è vuoto
     * 
     * @throws IllegalArgumentException
     *                                      se il grafo passato non è diretto
     * 
     * @throws IllegalArgumentException
     *                                      se il grafo passato non è pesato,
     *                                      cioè esiste almeno un arco il cui
     *                                      peso è {@code Double.NaN}.
     */
    public BellmanFordShortestPathComputer(Graph<L> graph) {
        if(graph == null) throw new NullPointerException("Graph can't be null");
        if(graph.isEmpty()) throw new IllegalArgumentException("Graph can't be empty");
        if(!graph.isDirected()) throw new IllegalArgumentException("Graph must be directed");
        for(GraphEdge<L> edge : graph.getEdges())
            if (Double.isNaN(edge.getWeight()))
                throw new IllegalArgumentException("Graph contains almost one not-weighted edge");

        this.graph = graph;
    }

    @Override
    public void computeShortestPathsFrom(GraphNode<L> sourceNode) {
        // controllo che il nodo non sia nullo e che sia contenuto nel grafo
        if(sourceNode == null)
            throw new NullPointerException("Source node can't be null");
        if(!this.graph.containsNode(sourceNode))
            throw new IllegalArgumentException("This node isn't contained in this graph");

        // inizializzo la coda con priorità
        this.lastSourceNode = sourceNode;
        BinaryHeapMinPriorityQueue<GraphNode<L>> queue = new BinaryHeapMinPriorityQueue<>();
        // imposto per ogni vertice la distanza a infinito e il predecessore nullo
        for (GraphNode<L> node : this.graph.getNodes()) {
            node.setPrevious(null);
            if(!node.equals(lastSourceNode))
                node.setFloatingPointDistance(Double.POSITIVE_INFINITY);
            else
                node.setFloatingPointDistance(0.0);
            queue.insert(node);
        }
        // eseguo algoritmo BellmanFord
            // effettua rilassamento sugli archi ripetutamente
        for(int i = 0; i < this.graph.nodeCount()-1; i++){
            for (GraphEdge<L> edge : this.graph.getEdges()) {
                if((edge.getNode1().getFloatingPointDistance() + edge.getWeight()) < edge.getNode2().getFloatingPointDistance()){
                    edge.getNode2().setFloatingPointDistance(edge.getNode1().getFloatingPointDistance() + edge.getWeight());
                    edge.getNode2().setPrevious(edge.getNode1());
                }
            }
        }
            // controlla cicli di peso negativi
        for (GraphEdge<L> edge : this.graph.getEdges()) {
            if((edge.getNode1().getFloatingPointDistance() + edge.getWeight()) < edge.getNode2().getFloatingPointDistance())
                throw new IllegalStateException("This graph contains negative-weigh cycle");
        }
        this.isComputed = true;
    }

    @Override
    public boolean isComputed() {
        return this.isComputed;
    }

    @Override
    public GraphNode<L> getLastSource() {
        if (!this.isComputed)
            throw new IllegalStateException("This graph isn't yet computed");
        return lastSourceNode;
    }

    @Override
    public Graph<L> getGraph() {
        return this.graph;
    }

    @Override
    public List<GraphEdge<L>> getShortestPathTo(GraphNode<L> targetNode) {
        if(targetNode == null) throw new NullPointerException("targetNode can't be null");
        if(!this.graph.containsNode(targetNode)) throw new IllegalArgumentException("This graph doesn't contain this node");
        if(!this.isComputed) throw new IllegalStateException("This graph isn't yet computed");

        // creo un arrayList da restituire
        ArrayList<GraphEdge<L>> listToReturn = new ArrayList<>();
        // salvo in una variabile temporanea il nodo corrente (solamente se il nodo con lo stesso hashCode
        // è realmente contenuto nel set di nodi del grafo)
        GraphNode<L> currNode = null;
        for(GraphNode<L> node : this.graph.getNodes()){
            if(node.equals(targetNode)){
                currNode = node;
                break;
            }
        }
        // finché il nodo corrente non corrisponde all'ultimo nodo sorgente aggiungo l'arco che collega
        // il nodo corrente al suo predecessore
        while(!currNode.equals(lastSourceNode)) {
            listToReturn.add(this.graph.getEdge(currNode.getPrevious(), currNode));
            currNode = currNode.getPrevious();
        }
        // quando il nodo corrente corrisponde a lastSourceNode ho ottenuto tutto il percorso piu breve
        // inverto l'ordine della lista
        Collections.reverse(listToReturn);
        return listToReturn;
    }

}