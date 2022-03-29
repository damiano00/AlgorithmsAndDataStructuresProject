package it.unicam.cs.asdl2021.totalproject2;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione dell' algoritmo di Floyd-Warshall per il calcolo di cammini
 * minimi tra tutte le coppie di nodi in un grafo pesato che può contenere anche
 * pesi negativi, ma non cicli di peso negativo.
 * 
 * @author Template: Luca Tesei
 * @author Implementation: Damiano Pasquini
 *
 * @param <L>
 *                etichette dei nodi del grafo
 */
public class FloydWarshallAllPairsShortestPathComputer<L> {

    /*
     * Il grafo su cui opera questo calcolatore.
     */
    private Graph<L> graph;

    /*
     * Matrice dei costi dei cammini minimi. L' elemento in posizione i, j
     * corrisponde al costo di un cammino minimo tra il nodo i e il nodo j, dove
     * i e j sono gli interi associati ai nodi nel grafo (si richiede quindi che
     * la classe che implementa il grafo supporti le operazioni con indici).
     */
    private double[][] costMatrix;

    /*
     * Matrice dei predecessori. L' elemento in posizione i, j è -1 se non esiste
     * nessun cammino tra i e j oppure corrisponde all' indice di un nodo che
     * precede il nodo j in un qualche cammino minimo da i a j. Si intende che i
     * e j sono gli indici associati ai nodi nel grafo (si richiede quindi che
     * la classe che implementa il grafo supporti le operazioni con indici).
     */
    private int[][] predecessorMatrix;

    private boolean isComputed;

    /**
     * Crea un calcolatore di cammini minimi fra tutte le coppie di nodi per un
     * grafo orientato e pesato. Non esegue il calcolo, che viene eseguito
     * invocando successivamente il metodo computeShortestPaths().
     * 
     * @param g
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
     */
    public FloydWarshallAllPairsShortestPathComputer(Graph<L> g) {
        if(g == null) throw new NullPointerException("Graph can't be null");
        if(g.isEmpty()) throw new IllegalArgumentException("Graph can't be empty");
        if(!g.isDirected()) throw new IllegalArgumentException("Graph must be directed");
        for(GraphEdge<L> edge : g.getEdges())
            if(Double.isNaN(edge.getWeight()))
                throw new IllegalArgumentException("This graph contains almost one not-weighted edge");

        this.graph = g;
        this.isComputed = false;
        this.costMatrix = new double[this.graph.nodeCount()][this.graph.nodeCount()];
        this.predecessorMatrix = new int[this.graph.nodeCount()][this.graph.nodeCount()];

        // imposta tutte le priorità dei nodi del grafo ad infinito e i precedenti a null
        // e inizializza tutti i valori di costMatrix con infinito e tutti i valori di predecessorMatrix con -1
        for (int i = 0; i < this.graph.nodeCount()-1; i++) {
            this.graph.getNodeAtIndex(i).setPriority(Double.POSITIVE_INFINITY);
            this.graph.getNodeAtIndex(i).setPrevious(null);
            for (int j = 0; j < this.graph.nodeCount()-1; j++) {
                this.costMatrix[i][j] = Double.POSITIVE_INFINITY;
                this.predecessorMatrix[i][j] = -1;
            }
        }
    }

    /**
     * Esegue il calcolo per la matrice dei costi dei cammini minimi e per la
     * matrice dei predecessori così come specificato dall' algoritmo di
     * Floyd-Warshall.
     * 
     * @throws IllegalStateException
     *                                   se il calcolo non può essere effettuato
     *                                   per via dei valori dei pesi del grafo,
     *                                   ad esempio se il grafo contiene cicli
     *                                   di peso negativo.
     */
    public void computeShortestPaths() {
        // Inizializzazione
        for (GraphEdge<L> edge : this.graph.getEdges()) {
            int u = edge.getNode1().getHandle();
            int v = edge.getNode2().getHandle();
            this.costMatrix[u][v] = edge.getWeight();
            this.predecessorMatrix[u][v] = u;
        }
        for (GraphNode<L> node : this.graph.getNodes()) {
            int v = node.getHandle();
            this.costMatrix[v][v] = 0;
            this.predecessorMatrix[v][v] = v;
        }

        // Implementazione standard Floyd-Warshall (calcolo bottom-up dei pesi di cammino minimo)
        for (int h = 0; h < this.graph.nodeCount(); h++)
            for (int i = 0; i < this.graph.nodeCount(); i++)
                for (int j = 0; j < this.graph.nodeCount(); j++)
                    if(this.costMatrix[i][j] > this.costMatrix[i][h] + this.costMatrix[h][j]) {
                        this.costMatrix[i][j] = this.costMatrix[i][h] + this.costMatrix[h][j];
                        this.predecessorMatrix[i][j] = this.predecessorMatrix[h][j];
                    }

        // Controllo cicli di peso negativo
        for (GraphEdge<L> edge : this.graph.getEdges()) {
            if((edge.getNode1().getFloatingPointDistance() + edge.getWeight()) < edge.getNode2().getFloatingPointDistance())
                throw new IllegalStateException("This graph contains negative-weigh cycle");
        }
        this.isComputed = true;
    }

    /**
     * Determina se è stata invocatala procedura di calcolo dei cammini minimi.
     * 
     * @return true se i cammini minimi sono stati calcolati, false altrimenti
     */
    public boolean isComputed() {
        return this.isComputed;
    }

    /**
     * Restituisce il grafo su cui opera questo calcolatore.
     * 
     * @return il grafo su cui opera questo calcolatore
     */
    public Graph<L> getGraph() {
        return this.graph;
    }

    /**
     * Restituisce una lista di archi da un nodo sorgente a un nodo target. Tale
     * lista corrisponde a un cammino minimo tra i due nodi nel grafo gestito da
     * questo calcolatore.
     * 
     * @param sourceNode
     *                       il nodo di partenza del cammino minimo da
     *                       restituire
     * @param targetNode
     *                       il nodo di arrivo del cammino minimo da restituire
     * @return la lista di archi corrispondente al cammino minimo; la lista è
     *         vuota se il nodo sorgente è il nodo target. Viene restituito
     *         {@code null} se il nodo target non è raggiungibile dal nodo
     *         sorgente
     * 
     * @throws NullPointerException
     *                                      se almeno uno dei nodi passati è
     *                                      nullo
     * 
     * @throws IllegalArgumentException
     *                                      se almeno uno dei nodi passati non
     *                                      esiste
     * 
     * @throws IllegalStateException
     *                                      se non è stato eseguito il calcolo
     *                                      dei cammini minimi
     * 
     * 
     */
    public List<GraphEdge<L>> getShortestPath(GraphNode<L> sourceNode, GraphNode<L> targetNode) {
        if(sourceNode == null || targetNode == null) throw new NullPointerException("Input nodes can't be null");
        if(!this.graph.containsNode(sourceNode)) throw new IllegalArgumentException("Source node doesn't exist in this graph");
        if(!this.graph.containsNode(targetNode)) throw new IllegalArgumentException("Target node doesn't exist in this graph");
        if(!isComputed()) throw new IllegalStateException("Shortest path calculation has never been performed");
        if(sourceNode.equals(targetNode)) return new ArrayList<>();
        if(predecessorMatrix[sourceNode.getHandle()][targetNode.getHandle()] == -1) return null;

        // Inizializzazione
        ArrayList<GraphNode<L>> nodePath = new ArrayList<>();
        int u = this.graph.getNodeIndexOf(sourceNode.getLabel());
        int v = this.graph.getNodeIndexOf(targetNode.getLabel());

        // Ricostruzione percorso
        while(u != v) {
            nodePath.add(this.graph.getNodeAtIndex(u));
            u = predecessorMatrix[u][v];
        }
        nodePath.add(this.graph.getNodeAtIndex(v));


        // Conversione percorso di nodi in percorso di archi
        ArrayList<GraphEdge<L>> edgePath = new ArrayList<>();
        for (int i = 0; i < nodePath.size()-2; i++)
            edgePath.add(this.graph.getEdge(nodePath.get(i), nodePath.get(i+1)));

        return edgePath;
    }

    /**
     * Restituisce il costo di un cammino minimo da un nodo sorgente a un nodo
     * target.
     * 
     * @param sourceNode
     *                       il nodo di partenza del cammino minimo
     * @param targetNode
     *                       il nodo di arrivo del cammino minimo
     * @return il costo di un cammino minimo tra il nodo sorgente e il nodo
     *         target. Viene restituito {@code Double.POSITIVE_INFINITY} se il
     *         nodo target non è raggiungibile dal nodo sorgente, mentre viene
     *         restituito zero se il nodo sorgente è il nodo target.
     * 
     * @throws NullPointerException
     *                                      se almeno uno dei nodi passati è
     *                                      nullo
     * 
     * @throws IllegalArgumentException
     *                                      se almeno uno dei nodi passati non
     *                                      esiste
     * 
     * @throws IllegalStateException
     *                                      se non è stato eseguito il calcolo
     *                                      dei cammini minimi
     * 
     * 
     */
    public double getShortestPathCost(GraphNode<L> sourceNode, GraphNode<L> targetNode) {
        if(sourceNode == null || targetNode == null) throw new NullPointerException("Source and target nodes can not be null");
        if(!this.graph.containsNode(sourceNode) || !this.graph.containsNode(targetNode)) throw new IllegalArgumentException("Nodes in input must be contained in this graph");
        if(!this.isComputed()) throw new IllegalStateException("The shortest path for this graph hasn't yet been calculated");

        double cost = 0.0;
        for (GraphEdge<L> edge : this.getShortestPath(sourceNode,targetNode))
            cost += edge.getWeight();
        return cost;
    }

    /**
     * Genera una stringa di descrizione di un path riportando i nodi
     * attraversati e i pesi degli archi. Nel caso di cammino vuoto genera solo
     * la stringa {@code "[ ]"}.
     * 
     * @param path
     *                 un cammino minimo
     * @return una stringa di descrizione del cammino minimo
     * @throws NullPointerException
     *                                  se il cammino passato è nullo
     */
    public String printPath(List<GraphEdge<L>> path) {
        if (path == null)
            throw new NullPointerException(
                    "Richiesta di stampare un path nullo");
        if (path.isEmpty())
            return "[ ]";
        // Costruisco la stringa
        StringBuffer s = new StringBuffer();
        s.append("[ " + path.get(0).getNode1().toString());
        for (int i = 0; i < path.size(); i++)
            s.append(" -- " + path.get(i).getWeight() + " --> "
                    + path.get(i).getNode2().toString());
        s.append(" ]");
        return s.toString();
    }

    /**
     * @return the costMatrix
     */
    public double[][] getCostMatrix() {
        return costMatrix;
    }

    /**
     * @return the predecessorMatrix
     */
    public int[][] getPredecessorMatrix() {
        return predecessorMatrix;
    }

}
