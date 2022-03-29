package it.unicam.cs.asdl2021.totalproject2;

import java.util.*;

/**
 * Classe che implementa un grafo orientato tramite matrice di adiacenza. Non
 * sono accettate etichette dei nodi null e non sono accettate etichette
 * duplicate nei nodi (che in quel caso sono lo stesso nodo).
 * 
 * I nodi sono indicizzati da 0 a nodeCount() - 1 seguendo l' ordine del loro
 * inserimento (0 è l' indice del primo nodo inserito, 1 del secondo e così via)
 * e quindi in ogni istante la matrice di adiacenza ha dimensione nodeCount() *
 * nodeCount(). La matrice, sempre quadrata, deve quindi aumentare di dimensione
 * ad ogni inserimento di un nodo. Per questo non è rappresentata tramite array
 * ma tramite ArrayList.
 * 
 * Gli oggetti GraphNode<L>, cioè i nodi, sono memorizzati in una mappa che
 * associa ad ogni nodo l' indice assegnato in fase di inserimento. Il dominio
 * della mappa rappresenta quindi l' insieme dei nodi.
 * 
 * Gli archi sono memorizzati nella matrice di adiacenza. A differenza della
 * rappresentazione standard con matrice di adiacenza, la posizione i,j della
 * matrice non contiene un flag di presenza, ma è null se i nodi i e j non sono
 * collegati da un arco orientato e contiene un oggetto della classe
 * GraphEdge<L> se lo sono. Tale oggetto rappresenta l' arco.
 * 
 * Questa classe non supporta la cancellazione di nodi, ma supporta la
 * cancellazione di archi e tutti i metodi che usano indici, utilizzando
 * l' indice assegnato a ogni nodo in fase di inserimento.
 * 
 * @author Template: Luca Tesei
 * @author Implementation: Damiano Pasquini
 *
 */
public class AdjacencyMatrixDirectedGraph<L> extends Graph<L> {
    /*
     * Le seguenti variabili istanza sono protected al solo scopo di agevolare
     * il JUnit testing
     */

    // Insieme dei nodi e associazione di ogni nodo con il proprio indice nella
    // matrice di adiacenza
    protected Map<GraphNode<L>, Integer> nodesIndex;

    // Matrice di adiacenza, gli elementi sono null o oggetti della classe
    // GraphEdge<L>. L' uso di ArrayList permette alla matrice di aumentare di
    // dimensione gradualmente ad ogni inserimento di un nuovo nodo.
    protected ArrayList<ArrayList<GraphEdge<L>>> matrix;

    /*
     * NOTA: per tutti i metodi che ritornano un set utilizzare la classe
     * HashSet<E> per creare l' insieme risultato. Questo garantisce un buon
     * funzionamento dei test JUnit che controllano l' uguaglianza tra insiemi
     */

    /**
     * Crea un grafo vuoto.
     */
    public AdjacencyMatrixDirectedGraph() {
        this.matrix = new ArrayList<>();
        this.nodesIndex = new HashMap<>();
    }

    @Override
    public int nodeCount() {
        return this.nodesIndex.size();
    }

    @Override
    public int edgeCount() {
        int edgesInThisGraph = 0;
        for (ArrayList<GraphEdge<L>> arrayListEdge : this.matrix)
            for (Object object : arrayListEdge) {
                if(object != null) edgesInThisGraph++;
            }
        return edgesInThisGraph;
    }

    @Override
    public void clear() {
        this.matrix = new ArrayList<>();
        this.nodesIndex = new HashMap<>();
    }

    @Override
    public boolean isDirected() {
        //Questa classe implementa un grafo orientato
        return true;
    }

    @Override
    public Set<GraphNode<L>> getNodes() {
        return new HashSet<>(this.nodesIndex.keySet());
    }

    @Override
    public boolean addNode(GraphNode<L> node) {
        // controllo che il nodo non sia nullo e che non sia gia contenuto nel grafo (in tal caso restituisco "false")
        if (node == null) throw new NullPointerException("Null element");
        if (this.containsNode(node)) return false;
        // altrimenti aggiungo il nodo in nodesIndex e aggiungo un'ArrayList in matrix
        this.nodesIndex.put(node, this.nodeCount());
        this.matrix.add(new ArrayList<>());
        // inizializza l'ultimo arrayList con tutti elementi "null"
        for (int i = 0; i < this.nodeCount(); i++)
            this.matrix.get(this.nodeCount()-1).add(null);
        // aggiunge una casella con valore "null" a tutti gli arrayList tranne l'ultimo poi restituisce "true"
        for (int i = 0; i < this.nodeCount()-1; i++)
            this.matrix.get(i).add(null);
        return true;
    }

    @Override
    public boolean removeNode(GraphNode<L> node) {
        throw new UnsupportedOperationException("Remove di nodi non supportata");
    }

    @Override
    public boolean containsNode(GraphNode<L> node) {
        // controlla se il node inserito e' null. Se e' null lancia l' eccezione
        if(node == null)
            throw new NullPointerException("Node can't be null");

        // se node e' contenuto (.containsKey) in nodesIndex restituisce true, false altrimenti
        return this.nodesIndex.containsKey(node);
    }

    @Override
    public GraphNode<L> getNodeOf(L label) {
        if(label == null)
            throw new NullPointerException("Label can't be null");

        // per ogni elemento di tipo GraphNode<L> (nella Map nodesIndex) controlla con il metodo
        // .equals se la label di tipo L corrispondente e' uguale a quella data in input.
        for (GraphNode<L> node : this.nodesIndex.keySet()){
            if (node.getLabel().equals(label)){
                return node;
            }
        }
        return null;
    }

    @Override
    public int getNodeIndexOf(L label) {
        if(label == null)
            throw new NullPointerException("Label can't be null");
        GraphNode<L> nodeToCheck = getNodeOf(label);
        if(nodeToCheck == null)
            throw new IllegalArgumentException("This label is not contained in this graph");

        for (int i = 0; i < this.nodeCount(); i++) {
            if(getNodeAtIndex(i).getLabel().equals(label)) {
                return nodesIndex.get(getNodeAtIndex(i));
            }
        }
        return -1;
    }

    @Override
    public GraphNode<L> getNodeAtIndex(int i) {
        // controllo se l'indice in input è compreso tra 0 e nodesIndex.size()-1
        if(i<0 || i>=nodesIndex.size())
            throw new IndexOutOfBoundsException("Input must be between 0 and nodesIndex.size()-1");

        // creo il nodo da restituire e scorro la Map nodesIndex per trovare il nodo che ha indice i
        GraphNode<L> nodeToReturn = null;
        for (Map.Entry<GraphNode<L>, Integer> node : this.nodesIndex.entrySet()) {
            if(node.getValue() == i) {
                nodeToReturn = node.getKey();
                break;
            }
        }
        return nodeToReturn;
    }

    @Override
    public Set<GraphNode<L>> getAdjacentNodesOf(GraphNode<L> node) {
        // controlla che il nodo non sia null e che sia contenuto in questo grafo
        if(node == null) throw new NullPointerException("Node can't be null");
        if(!this.containsNode(node)) throw new IllegalArgumentException("This node doesn't exist in this graph");

        // aggiunge al set da restituire tutti i nodi uscenti che si trovano nell' i-esimo arrayList corrispondente al nodo in input
        Set<GraphNode<L>> setToReturn = new HashSet<>();
        for(GraphEdge<L> edge : this.matrix.get(getNodeIndexOf(node.getLabel()))) {
            if ((edge != null) && (edge.getNode2() != null))
                setToReturn.add(edge.getNode2());
        }
        return setToReturn;
    }

    @Override
    public Set<GraphNode<L>> getPredecessorNodesOf(GraphNode<L> node) {
        // controlla che il grafo sia orientato, che il nodo sia contenuto nel grafo e che il nodo non sia nullo
        if(!this.isDirected())
            throw new UnsupportedOperationException("This graph is not directed");
        if(!this.containsNode(node))
            throw new IllegalArgumentException("This node doesn't exist in this graph");
        if(node == null)
            throw new NullPointerException("Node can not be null");

        Set<GraphNode<L>> setToReturn = new HashSet<>();
        int index = getNodeIndexOf(node.getLabel());
        // controlla la colonna corrispondente a "node", inserendo nel set tutti i nodi predecessori a "node"
        for (int i = 0; i < this.nodeCount(); i++) {
            if(matrix.get(i).get(index) != null)
                setToReturn.add(matrix.get(i).get(index).getNode1());
        }
        return setToReturn;
    }

    @Override
    public Set<GraphEdge<L>> getEdges() {
        // creo un Set come HashSet di archi tra nodi (GraphEdge)
        Set<GraphEdge<L>> edgeSetToReturn = new HashSet<>();

        // scorro gli archi nella matrice e aggiungo gli archi non nulli al set da restituire
        for(ArrayList<GraphEdge<L>> arrayList : this.matrix){
            for (GraphEdge<L> edgeInArrayList : arrayList) {
                if(edgeInArrayList != null)
                    edgeSetToReturn.add(edgeInArrayList);
            }
        }
        return edgeSetToReturn;
    }

    @Override
    public boolean addEdge(GraphEdge<L> edge) {
        // controlla se è nullo, se è gia contenuto nella matrice, se è orientato e se i due nodi a esso riferiti esistono
        if(edge == null)
            throw new NullPointerException("Edge can't be null");
        if(!edge.isDirected())
            throw new IllegalArgumentException("Edge must be directed in order to being add");
        if(!nodesIndex.containsKey(edge.getNode1()) || !nodesIndex.containsKey(edge.getNode2()))
            throw new IllegalArgumentException("Both nodes must be contained in this graph");
        if(matrix.get(nodesIndex.get(edge.getNode1())).contains(edge))
            return false;

        int indexOfNode1 = getNodeIndexOf(edge.getNode1().getLabel());
        int indexOfNode2 = getNodeIndexOf(edge.getNode2().getLabel());
        this.matrix.get(indexOfNode1).add(indexOfNode2, edge);
        return true;
    }

    @Override
    public boolean removeEdge(GraphEdge<L> edge) {
        if(edge == null)
            throw new NullPointerException("Edge can't be null");
        if(!this.nodesIndex.containsKey(edge.getNode1()) || !this.nodesIndex.containsKey(edge.getNode2()))
            throw new IllegalArgumentException("Nodes linked to this edge aren't contained in this graph");
        if(!this.isDirected())
            throw new UnsupportedOperationException("This graph doesn't support this operation");

        int indexOfNode1 = getNodeIndexOf(edge.getNode1().getLabel());
        int indexOfNode2 = getNodeIndexOf(edge.getNode2().getLabel());
        matrix.get(indexOfNode1).set(indexOfNode2, null);
        return true;
    }

    @Override
    public boolean containsEdge(GraphEdge<L> edge) {
        if(edge == null)
            throw new NullPointerException("Edge can't be null");
        if(!this.containsNode(edge.getNode1()) || !this.containsNode(edge.getNode2()))
            throw new IllegalArgumentException("Referring nodes aren't contained in this graph");

        int nodeIndexOfNode1 = getNodeIndexOf(edge.getNode1().getLabel());
        int nodeIndexOfNode2 = getNodeIndexOf(edge.getNode2().getLabel());
        return matrix.get(nodeIndexOfNode1).get(nodeIndexOfNode2).equals(edge);
    }

    @Override
    public Set<GraphEdge<L>> getEdgesOf(GraphNode<L> node) {
        if(!this.containsNode(node))
            throw new IllegalArgumentException("This node not exists in this graph");
        if(node == null)
            throw new NullPointerException("Node can't be null");

        Set<GraphEdge<L>> setToReturn = new HashSet<>();
        // se l'arco è orientato allora aggiunge tutti gli elementi non null in uscita da "node"
        if(this.isDirected()){
            for(GraphEdge<L> edge : this.matrix.get(getNodeIndexOf(node.getLabel()))){
                if(edge!=null)
                    setToReturn.add(edge);
            }
        } else {
            // altrimenti scorre le due colonne con indice di "node" e aggiunge tutti gli elementi non null
            setToReturn.addAll(this.matrix.get(getNodeIndexOf(node.getLabel())));
            for (int i = 0; i < nodeCount(); i++) {
                if(matrix.get(i).get(getNodeIndexOf(node.getLabel())) != null)
                    setToReturn.add(matrix.get(i).get(getNodeIndexOf(node.getLabel())));
            }
        }
        return setToReturn;
    }

    @Override
    public Set<GraphEdge<L>> getIngoingEdgesOf(GraphNode<L> node) {
        // controlla che il grafo sia orientato, che il nodo sia contenuto nel grafo e che il nodo non sia null
        if(!this.isDirected()) throw new UnsupportedOperationException("This graph is not directed");
        if(!this.containsNode(node)) throw new IllegalArgumentException("This graph doesn't contain this node");
        if(node==null) throw new NullPointerException("Node can't be null");

        // scorre tutti gli arrayList controllando la colonna con indice del nodo in input, e se la cella non è null
        // allora aggiunge l'arco al set da restituire
        Set<GraphEdge<L>> setToReturn = new HashSet<>();
        for (int i = 0; i < this.nodeCount(); i++) {
            if(this.matrix.get(i).get(getNodeIndexOf(node.getLabel())) != null)
                setToReturn.add(this.matrix.get(i).get(getNodeIndexOf(node.getLabel())));
        }
        return setToReturn;
    }

    @Override
    public GraphEdge<L> getEdge(GraphNode<L> node1, GraphNode<L> node2) {
        // controlla se i nodi sono nulli e se sono contenuti nella mappa di nodi lanciando le relative eccezioni
        if(node1 == null || node2 == null)
            throw new NullPointerException("nodes can't be null");
        if(!this.containsNode(node1) || !this.containsNode(node2))
            throw new IllegalArgumentException("nodes aren't in this graph");

        for (GraphEdge<L> edges : getEdgesOf(node1)) {
            if(edges.getNode2().equals(node2))
                return edges;
        }
        return null;
    }

    @Override
    public GraphEdge<L> getEdgeAtNodeIndexes(int i, int j) {
        if((i<0 || i>=this.nodeCount())
                || (j<0 || j>=this.nodeCount())
                || (this.getNodeAtIndex(i))==null
                || (this.getNodeAtIndex(j))==null)
            throw new IndexOutOfBoundsException("These indexes must be valid");

        if(this.matrix.get(i).get(j) != null)
            return (this.matrix.get(i).get(j));
        else return null;
    }
}
