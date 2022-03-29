package it.unicam.cs.asdl2021.totalproject2;

import java.util.*;

/**
 * Implementazione della classe astratta {@code Graph<L>} che realizza un grafo
 * non orientato. Non sono accettate etichette dei nodi null e non sono
 * accettate etichette duplicate nei nodi (che in quel caso sono lo stesso
 * nodo).
 * 
 * Per la rappresentazione viene usata una variante della rappresentazione con
 * liste di adiacenza. A differenza della rappresentazione standard si usano
 * strutture dati più efficienti per quanto riguarda la complessità in tempo
 * della ricerca se un nodo è presente (pseudo-costante, con tabella hash) e se
 * un arco è presente (pseudo-costante, con tabella hash). Lo spazio occupato per
 * la rappresentazione risulta tuttavia più grande di quello che servirebbe con
 * la rappresentazione standard.
 * 
 * Le liste di adiacenza sono rappresentate con una mappa (implementata con
 * tabelle hash) che associa ad ogni nodo del grafo i nodi adiacenti. In questo
 * modo il dominio delle chiavi della mappa è l' insieme dei nodi, su cui è
 * possibile chiamare il metodo contains per testare la presenza o meno di un
 * nodo. Ad ogni chiave della mappa, cioè ad ogni nodo del grafo, non è
 * associata una lista concatenata dei nodi collegati, ma un set di oggetti
 * della classe GraphEdge<L> che rappresentano gli archi collegati al nodo: in
 * questo modo la rappresentazione riesce a contenere anche l' eventuale peso
 * dell' arco (memorizzato nell' oggetto della classe GraphEdge<L>). Per
 * controllare se un arco è presente basta richiamare il metodo contains in
 * questo set. I test di presenza si basano sui metodi equals ridefiniti per
 * nodi e archi nelle classi GraphNode<L> e GraphEdge<L>.
 * 
 * Questa classe non supporta le operazioni indicizzate di ricerca di nodi e
 * archi.
 * 
 * @author Template: Luca Tesei
 * @author Implementation: Damiano Pasquini
 *
 * @param <L>
 *                etichette dei nodi del grafo
 */
public class MapAdjacentListUndirectedGraph<L> extends Graph<L> {

    /*
     * Le liste di adiacenza sono rappresentate con una mappa. Ogni nodo viene
     * associato con l' insieme degli archi collegati. Nel caso in cui un nodo
     * non abbia archi collegati è associato con un insieme vuoto. La variabile
     * istanza è protected solo per scopi di test JUnit.
     */
    protected final Map<GraphNode<L>, Set<GraphEdge<L>>> adjacentLists;

    /*
     * NOTA: per tutti i metodi che ritornano un set utilizzare la classe
     * HashSet<E> per creare l' insieme risultato. Questo garantisce un buon
     * funzionamento dei test JUnit che controllano l' uguaglianza tra insiemi
     */

    /**
     * Crea un grafo vuoto.
     */
    public MapAdjacentListUndirectedGraph() {
        // Inizializza la mappa con la mappa vuota
        this.adjacentLists = new HashMap<>();
    }

    @Override
    public int nodeCount() {
        // restituisce il numero di nodi del grafo in base a quante key ci sono nella Map
        return this.adjacentLists.size();
    }

    @Override
    public int edgeCount() {
        return getEdges().size();
    }

    @Override
    public void clear() {
        this.adjacentLists.clear();
    }

    @Override
    public boolean isDirected() {
        // Questa classe implementa grafi non orientati
        return false;
    }

    @Override
    public Set<GraphNode<L>> getNodes() {
        // restituisce un HashSet contenente tutti i nodi contenuti nel keySet della Map
        return new HashSet<>(this.adjacentLists.keySet());
    }

    @Override
    public boolean addNode(GraphNode<L> node) {
        // controllo che il nodo non sia null
        if(node == null) throw new NullPointerException("Node can't be null");

        // creo il Set da associare al nodo, e con il metodo .put aggiungo alla lista il
        // nodo con il relativo set di archi
        Set<GraphEdge<L>> graphEdgeSet = new HashSet<>();
        this.adjacentLists.put(node, graphEdgeSet);
        return true;
    }

    @Override
    public boolean removeNode(GraphNode<L> node) {
        // controlla che il nodo non sia null
        if(node == null) throw new NullPointerException("Node can't be null");

        // con un for viene cercato e cancellato il nodo nella lista di adiacenza
        for(GraphNode<L> graphNode : this.adjacentLists.keySet()){
            if(node.equals(graphNode)){
                this.adjacentLists.remove(node, getEdgesOf(node));
                break;
            }
        }
        return true;
    }

    @Override
    public boolean containsNode(GraphNode<L> node) {
        if(node == null) throw new NullPointerException("Node can't be null");

        return this.adjacentLists.containsKey(node);
    }

    @Override
    public GraphNode<L> getNodeOf(L label) {
        // controlla che l'etichetta non sia null
        if(label == null) throw new NullPointerException("Label can't be null");

        for (GraphNode<L> node : adjacentLists.keySet())
            if(node.getLabel().equals(label))
                return node;
        return null;
    }

    @Override
    public int getNodeIndexOf(L label) {
        throw new UnsupportedOperationException("Ricerca dei nodi con indice non supportata");
    }

    @Override
    public GraphNode<L> getNodeAtIndex(int i) {
        throw new UnsupportedOperationException("Ricerca dei nodi con indice non supportata");
    }

    @Override
    public Set<GraphNode<L>> getAdjacentNodesOf(GraphNode<L> node) {
        // se la lista contiene il nodo e il nodo non è nullo allora
        if(!this.containsNode(node)) throw new IllegalArgumentException( "Input node does not exist in this adjacency list" );
        if(node == null) throw new NullPointerException( "Input node can't be null" );

        // scorre tutti gli archi riferiti al nodo in input aggiungendo tutti i nodi riferiti
        // all'arco corrente, infine elimina il nodo dato in input e viene restituito il set di nodi adiacenti
        Set<GraphNode<L>> setToReturn = new HashSet<>();
        for (GraphEdge<L> edge : this.adjacentLists.get(node)) {
            setToReturn.add(edge.getNode1());
            setToReturn.add(edge.getNode2());
        }
        setToReturn.remove(node);
        return setToReturn;
    }

    @Override
    public Set<GraphNode<L>> getPredecessorNodesOf(GraphNode<L> node) {
        throw new UnsupportedOperationException(
                "Ricerca dei nodi predecessori non supportata in un grafo non orientato");
    }

    @Override
    public Set<GraphEdge<L>> getEdges() {
        // per ogni key nella Map aggiunge tutti gli archi che non sono ancora presenti nel Set
        Set<GraphEdge<L>> setToReturn = new HashSet<>();
        for (GraphNode<L> node : adjacentLists.keySet())
            setToReturn.addAll(this.adjacentLists.get(node));
        return setToReturn;
    }

    @Override
    public boolean addEdge(GraphEdge<L> edge) {
        // controlla che l'arco non sia null, che i nodi associati a edge esistano e che edge non sia orientato.
        if(edge == null) throw new NullPointerException("Edge can't be null");
        if(!this.containsNode(edge.getNode1()) || !this.containsNode(edge.getNode2())) throw new IllegalArgumentException("Linked nodes to this edge must exist");
        if(edge.isDirected()) throw new IllegalArgumentException("Edge must be undirected");
        if(this.containsEdge(edge)) return false;

        // aggiunge l'arco nel set riferito a node1 e poi in quello a di node2, poiché il grafo non è orientato
        this.adjacentLists.get(edge.getNode1()).add(edge);
        this.adjacentLists.get(edge.getNode2()).add(edge);
        return true;
    }

    @Override
    public boolean removeEdge(GraphEdge<L> edge) {
        // controlla che l'arco non sia null, che i nodi a esso associati esistano nel grafo e che
        // l'arco sia contenuto in entrambi i Set dei due nodi
        if(edge == null) throw new NullPointerException("Edge can't be null");
        if(!this.containsNode(edge.getNode1()) || !this.containsNode(edge.getNode2()))
            throw new IllegalArgumentException("Linked nodes to this edge must exist");
        if(edge.isDirected()) throw new UnsupportedOperationException("Edge must be undirected");
        if( !this.adjacentLists.get(edge.getNode1()).contains(edge)
                || !this.adjacentLists.get(edge.getNode2()).contains(edge))
            return false;

        // con due for divisi, controlla entrambi i Set riferiti ai due nodi,
        // eliminando l'arco quando incontrato
        for (GraphEdge<L> graphEdge : getEdgesOf(edge.getNode1()))
            if(edge.equals(graphEdge))
                this.adjacentLists.get(edge.getNode1()).remove(edge);
        for (GraphEdge<L> graphEdge2 : getEdgesOf(edge.getNode2()))
            if(edge.equals(graphEdge2))
                this.adjacentLists.get(edge.getNode2()).remove(edge);
        return true;
    }

    @Override
    public boolean containsEdge(GraphEdge<L> edge) {
        // controlla se l'arco è null e se i nodi collegati all'arco sono null
        if(edge == null) throw new NullPointerException("Edge can't be null");
        if(!this.containsNode(edge.getNode1()) || !this.containsNode(edge.getNode2()))
            throw new IllegalArgumentException("Linked nodes to this edge must exist");

        // altrimenti per ogni nodo della lista, se a quel nodo corrisponde l' arco cercato
        // restituisce true, altrimenti finita la lista viene restituito false.
        for (GraphNode<L> node : adjacentLists.keySet() ) {
            if(getEdgesOf(node).contains(edge))
                return true;
        }
        return false;
    }

    @Override
    public Set<GraphEdge<L>> getEdgesOf(GraphNode<L> node) {
        // controlla se il nodo è nullo o se non esiste
        if(node == null) throw new NullPointerException("Node can't be null");
        if(!adjacentLists.containsKey(node)) throw new IllegalArgumentException("Node doesn't exist in this adjacency list");

        // restituisco in output gli archi collegati al nodo in input tramite la lista di adiacenza
        return new HashSet<>(this.adjacentLists.get(node));
    }

    @Override
    public Set<GraphEdge<L>> getIngoingEdgesOf(GraphNode<L> node) {
        throw new UnsupportedOperationException(
                "Ricerca degli archi entranti non supportata in un grafo non orientato");
    }

    @Override
    public GraphEdge<L> getEdge(GraphNode<L> node1, GraphNode<L> node2) {
        // controllo se i due nodi sono nulli e se non sono presenti nella lista di adiacenza
        if(node1 == null || node2 == null) throw new NullPointerException("Nodes can't be null");
        if (!adjacentLists.containsKey(node1) || !adjacentLists.containsKey(node2))
            throw new IllegalArgumentException("Both nodes must exist in this adjacency list");

        // Con due for concatenati scorro gli archi riferiti ad ognuno dei due nodi.
        // Quando trovo due archi uguali, ne restituisco uno ovvero l' arco cercato.
        for(GraphEdge<L> node1Edge : getEdgesOf(node1))
            for(GraphEdge<L> node2Edge : getEdgesOf(node2))
                if(node1Edge.equals(node2Edge))
                    return node1Edge;
        return null;
    }

    @Override
    public GraphEdge<L> getEdgeAtNodeIndexes(int i, int j) {
        throw new UnsupportedOperationException(
                "Operazioni con indici non supportate");
    }

}
