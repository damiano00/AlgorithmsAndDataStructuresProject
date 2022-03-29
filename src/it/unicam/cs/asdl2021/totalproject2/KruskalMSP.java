package it.unicam.cs.asdl2021.totalproject2;

import java.util.*;

/**
 * 
 * Classe singoletto che implementa l' algoritmo di Kruskal per trovare un
 * Minimum Spanning Tree di un grafo non orientato, pesato e con pesi non
 * negativi.
 * 
 * @author Template: Luca Tesei
 * @author Implementation: Damiano Pasquini
 * 
 * @param <L>
 *                etichette dei nodi del grafo
 *
 */
public class KruskalMSP<L> {

    /*
     * Struttura dati per rappresentare gli insiemi disgiunti utilizzata
     * dall' algoritmo di Kruskal.
     */
    private final ArrayList<HashSet<GraphNode<L>>> disjointSets;
    private final Set<GraphEdge<L>> mspEdgeSet;


    /**
     * Costruisce un calcolatore di un albero di copertura minimo che usa
     * l' algoritmo di Kruskal su un grafo non orientato e pesato.
     */
    public KruskalMSP() {
        this.disjointSets = new ArrayList<>();
        this.mspEdgeSet = new HashSet<>();
    }

    /**
     * Utilizza l' algoritmo goloso di Kruskal per trovare un albero di copertura
     * minimo in un grafo non orientato e pesato, con pesi degli archi non
     * negativi. L' albero restituito non è radicato, quindi è rappresentato
     * semplicemente con un sottoinsieme degli archi del grafo.
     * 
     * @param g
     *              un grafo non orientato, pesato, con pesi non negativi
     * @return l'insieme degli archi del grafo g che costituiscono l' albero di
     *         copertura minimo trovato
     * @throw NullPointerException se il grafo g è null
     * @throw IllegalArgumentException se il grafo g è orientato, non pesato o
     *        con pesi negativi
     */
    public Set<GraphEdge<L>> computeMSP(Graph<L> g) {
        // controlli
        if(g==null) throw new NullPointerException("This graph can't be null");
        if(g.isDirected()) throw new IllegalArgumentException("This graph can't be directed");
        for (GraphEdge<L> edge : g.getEdges())
            if(!edge.hasWeight()) throw new IllegalArgumentException("This graph can't be unweighted");
            else if(edge.getWeight()<0) throw new IllegalArgumentException("This graph can't contains negative weights");

        // inizializzazione del set di archi dell'albero minimo di copertura e
        // dell' arrayList di hashSet di nodi non uniti
        mspEdgeSet.clear();
        for (GraphNode<L> node : g.getNodes()) {
            HashSet<GraphNode<L>> hashSet = new HashSet<>();
            hashSet.add(node);
            disjointSets.add(hashSet);
        }

        // algoritmo di Kruskal
        for (GraphEdge<L> edge : getOrderedEdges(g)) {
            int i = getIndexOf(edge.getNode1());
            int j = getIndexOf(edge.getNode2());
            if (i != j){
                mspEdgeSet.add(edge);
                this.union(i,j);
            }
        }
        return mspEdgeSet;
    }

    private ArrayList<GraphEdge<L>> getOrderedEdges (Graph<L> g){
        if(g == null) throw new NullPointerException("Graph can't be null");

        ArrayList<GraphEdge<L>> arrayListToReturn = new ArrayList<>(g.getEdges());
        EdgesComparator comparator = new EdgesComparator();
        arrayListToReturn.sort(comparator);
        return arrayListToReturn;
    }


    private int getIndexOf(GraphNode<L> node){
        for (int i = 0; i < this.disjointSets.size(); i++) {
            if(disjointSets.get(i).contains(node)) return i;
        }
        return -1;
    }

    private void union(int i, int j) {
        disjointSets.get(i).addAll(disjointSets.get(j));
        disjointSets.remove(disjointSets.get(j));
    }

    private class EdgesComparator implements Comparator<GraphEdge<L>>{
        @Override
        public int compare(GraphEdge<L> o1, GraphEdge<L> o2) {
            return Double.compare(o1.getWeight(), o2.getWeight());
        }
    }

}
