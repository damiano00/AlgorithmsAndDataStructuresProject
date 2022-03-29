package it.unicam.cs.asdl2021.totalproject2;

/**
 *
 * Classe singoletto che implementa l' algoritmo di Prim per trovare un Minimum
 * Spanning Tree di un grafo non orientato, pesato e con pesi non negativi.
 *
 * L' algoritmo usa una coda di min priorità tra i nodi implementata dalla classe
 * TernaryHeapMinPriorityQueue. I nodi vengono visti come PriorityQueueElement
 * poiché la classe GraphNode<L> implementa questa interfaccia. Si noti che
 * nell' esecuzione dell' algoritmo è necessario utilizzare l' operazione di
 * decreasePriority.
 *
 * @author Template: Luca Tesei
 * @author Implementation: Damiano Pasquini
 *
 * @param <L>
 *                etichette dei nodi del grafo
 *
 */
public class PrimMSP<L> {

    /*
     * Coda di priorità che va usata dall' algoritmo. La variabile istanza è
     * protected solo per scopi di testing JUnit.
     */
    protected BinaryHeapMinPriorityQueue queue;

    /**
     * Crea un nuovo algoritmo e inizializza la coda di priorità con una coda
     * vuota.
     */
    public PrimMSP() {
        this.queue = new BinaryHeapMinPriorityQueue();
    }

    /**
     * Utilizza l' algoritmo goloso di Prim per trovare un albero di copertura
     * minimo in un grafo non orientato e pesato, con pesi degli archi non negativi.
     * Dopo l' esecuzione del metodo nei nodi del grafo il campo previous deve
     * contenere un puntatore a un nodo in accordo all' albero di copertura
     * minimo calcolato, la cui radice è il nodo sorgente passato.
     *
     * @param g
     *              un grafo non orientato, pesato, con pesi non negativi
     * @param s
     *              il nodo del grafo g sorgente, cioè da cui parte il calcolo
     *              dell' albero di copertura minimo. Tale nodo sarà la radice
     *              dell' albero di copertura trovato
     *
     * @throw NullPointerException se il grafo g o il nodo sorgente s sono nulli
     * @throw IllegalArgumentException se il nodo sorgente s non esiste in g
     * @throw IllegalArgumentException se il grafo g è orientato, non pesato o
     *        con pesi negativi
     */
    public void computeMSP(Graph<L> g, GraphNode<L> s) {
        // lancio eccezioni
        if(g == null) throw new NullPointerException("Graph can't be null");
        if(s == null) throw new NullPointerException("Source Node can't be null");
        if(!g.containsNode(s)) throw new IllegalArgumentException("Source node s doesn't exist in Graph g");
        if(g.isDirected()) throw new IllegalArgumentException("This graph must be undirected");
        for(GraphEdge<L> edge : g.getEdges()) {
            if (edge.hasWeight()) {
                if (edge.getWeight() < 0)
                    throw new IllegalArgumentException("This graph can't be negative weighted");
            }
            else throw new IllegalArgumentException("This graph must be weighted");
        }

        // inizializzazione
            // per ogni nodo imposta priorità a infinito, precedente nullo e colore bianco
            // (inserito nella coda di priorità)
        for (GraphNode<L> node : g.getNodes()) {
            node.setPriority(Double.POSITIVE_INFINITY);
            node.setPrevious(null);
            node.setColor(GraphNode.COLOR_WHITE);
        } // imposta la priorità del nodo sorgente a 0 e inserisce tutti i nodi nella coda con priorità
        s.setPriority(0.0);
        for (GraphNode<L> node : g.getNodes())
            this.queue.insert(node);

        // Prim
            // finché la coda non è vuota
        while(!queue.isEmpty()){
            // estrae il nodo con priorità minima e imposta il colore nero (fuori dalla coda con priorità)
            GraphNode<L> u = (GraphNode<L>) queue.extractMinimum();
            u.setColor(GraphNode.COLOR_BLACK);
            // per ogni nodo, se si trova nella coda con priorità (bianco) e se il peso dell'arco che collega
            // il nodo minimo estratto e il nodo corrente ha peso minore della priorità del nodo corrente
            for (GraphNode<L> v : g.getAdjacentNodesOf(u)) {
                if(v.getColor() == GraphNode.COLOR_WHITE && g.getEdge(u,v).getWeight() < v.getPriority()){
                    // allora imposta il nodo minimo estratto come precedente del nodo corrente e decrementa
                    // la priorità del nodo corrente con il peso dell'arco che collega nodo minimo a nodo corrente
                    v.setPrevious(u);
                    this.queue.decreasePriority(v,g.getEdge(u,v).getWeight());
                }
            }
        }
    }

}
