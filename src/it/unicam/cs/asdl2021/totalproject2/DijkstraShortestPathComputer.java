package it.unicam.cs.asdl2021.totalproject2;

import java.util.*;

/**
 * Gli oggetti di questa classe sono calcolatori di cammini minimi con sorgente
 * singola su un certo grafo orientato e pesato dato. Il grafo su cui lavorare
 * deve essere passato quando l' oggetto calcolatore viene costruito e non può
 * contenere archi con pesi negativi. Il calcolatore implementa il classico
 * algoritmo di Dijkstra per i cammini minimi con sorgente singola utilizzando
 * una coda con priorità che estrae l' elemento con priorità minima e aggiorna le
 * priorità con l' operazione decreasePriority in tempo logaritmico (coda
 * realizzata con uno heap binario). In questo caso il tempo di esecuzione
 * dell' algoritmo di Dijkstra è {@code O(n log m)} dove {@code n} è il numero di
 * nodi del grafo e {@code m} è il numero di archi.
 * 
 * @author Template: Luca Tesei
 * @author Implementation: Damiano Pasquini
 *
 * @param <L>
 *                il tipo delle etichette dei nodi del grafo
 */
public class DijkstraShortestPathComputer<L> implements SingleSourceShortestPathComputer<L> {

    private final Graph<L> graph;
    private GraphNode<L> lastSourceNode;
    private boolean isComputed = false;


    /**
     * Crea un calcolatore di cammini minimi a sorgente singola per un grafo
     * diretto e pesato privo di pesi negativi.
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
     *                                      se il grafo passato non è orientato
     * 
     * @throws IllegalArgumentException
     *                                      se il grafo passato non è pesato,
     *                                      cioè esiste almeno un arco il cui
     *                                      peso è {@code Double.NaN}
     * @throws IllegalArgumentException
     *                                      se il grafo passato contiene almeno
     *                                      un peso negativo
     */
    public DijkstraShortestPathComputer(Graph<L> graph) {
        if(graph == null) throw new NullPointerException("Graph can't be null");
        if(graph.isEmpty()) throw new IllegalArgumentException("Graph can't be empty");
        if(!graph.isDirected()) throw new IllegalArgumentException("Graph can't be undirected");
        for (GraphEdge<L> edge : graph.getEdges())
            if(edge.getWeight() < 0.0)
                throw new IllegalArgumentException("This graph contains almost one negative weighted edge");
        for(GraphEdge<L> edge : graph.getEdges())
            if (Double.isNaN(edge.getWeight()))
                throw new IllegalArgumentException("Graph contains almost one not-weighted edge");

        this.graph = graph;
    }


    @Override
    public void computeShortestPathsFrom(GraphNode<L> sourceNode) {
        // controllo che il nodo sorgente non sia nullo e che sia contenuto in questo grafo
        if(sourceNode == null) throw new NullPointerException("sourceNode can't be null");
        if(!this.graph.containsNode(sourceNode)) throw new IllegalArgumentException("sourceNode must be contained in this graph");

        // inizializzo la coda con priorità
        BinaryHeapMinPriorityQueue<GraphNode<L>> queue = new BinaryHeapMinPriorityQueue<>();
        // imposto tutti i nodi previous a null e tutte le distanze dai nodi al nodo sorgente ad infinito,
        // tranne per il nodo sorgente che ha distanza 0 da se stesso
        for (GraphNode<L> node : this.graph.getNodes()) {
            node.setPrevious(null);
            if(!node.equals(sourceNode))
                node.setFloatingPointDistance(Double.POSITIVE_INFINITY);
            else{
                lastSourceNode = node;
                node.setFloatingPointDistance(0.0);
            }
            queue.insert(node);
        }

        // Eseguo algoritmo Dijkstra
        while(!queue.isEmpty()){
            GraphNode<L> minimum = (GraphNode<L>) queue.extractMinimum();
            for (GraphNode<L> node : this.graph.getAdjacentNodesOf(minimum)) {
                double dist = minimum.getFloatingPointDistance() + this.graph.getEdge(minimum,node).getWeight();
                if(dist < node.getFloatingPointDistance()) {
                    node.setPrevious(minimum);
                    queue.decreasePriority(node, dist);
                }
            }
        }
        this.isComputed = true;
    }

    @Override
    public boolean isComputed() {
        return this.isComputed;
    }

    @Override
    public GraphNode<L> getLastSource() {
        if (!this.isComputed) throw new IllegalStateException("This graph isn't yet computed");
        return this.lastSourceNode;
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
        // finché il predecessore del nodo corrente non è nullo aggiungo l'arco che collega
        // il nodo corrente al suo predecessore alla lista e continuo il ciclo for
        while(currNode.getPrevious() != null) {
            listToReturn.add(this.graph.getEdge(currNode.getPrevious(), currNode));
            currNode = currNode.getPrevious();
        }
        // quando il nodo corrente corrisponde all'ultimo nodo sorgente ho ottenuto il percorso minimo dal nodo target
        // al nodo sorgente, perciò inverto l'ordine della lista
        Collections.reverse(listToReturn);
        // se ho calcolato il cammino minimo con nodo sorgente che non ha archi entranti, allora la lista vuota sarà null
        if(graph.getIngoingEdgesOf(currNode).size() == 0)
            if(listToReturn.isEmpty())
                listToReturn = null;
        return listToReturn;
    }

}
