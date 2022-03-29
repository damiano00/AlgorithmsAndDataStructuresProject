/**
 * 
 */
package it.unicam.cs.asdl2021.totalproject2;

/**
 * Questa classe raggruppa le operazioni tipicamente associate a un nodo facente
 * parte di un grafo. I nodi del grafo sono etichettati con oggetti della classe
 * {@code L}. L' etichetta non puà essere null. Le classi {@code GraphEdge<L>} e
 * {@code Graph<L>} definiscono le operazioni tipiche sugli archi e sul grafo,
 * rispettivamente.
 * 
 * Le operazioni presenti sono quelle che sono usate dagli algoritmi su grafi
 * più comuni: attribuzione e modifica di un colore, di una distanza, di un
 * puntatore a un nodo predecessore e di tempi di ingresso/uscita durante una
 * visita. L' etichetta è immutabile, le altre informazioni possono cambiare e
 * non definiscono l' identità del nodo, che è data esclusivamente
 * dall' etichetta. In altre parole, due nodi sono uguali se e solo se hanno
 * etichetta uguale.
 * 
 * In molti algoritmi sui grafi i nodi vengono inseriti in una coda di priorità.
 * Questa classe implementa l' interfaccia PriorityQueueElement utilizzando il
 * campo floatingPointDistance come priorità e il campo integerDistance come
 * handle intero in una coda con priorità realizzata con uno heap rappresentato
 * in un array.
 * 
 * @author Luca Tesei
 * 
 * @param <L>
 *                etichette dei nodi
 *
 */
public class GraphNode<L> implements PriorityQueueElement {

    /**
     * Colore bianco associato al nodo.
     */
    public static int COLOR_WHITE = 0;

    /**
     * Colore grigio associato al nodo.
     */
    public static int COLOR_GREY = 1;

    /**
     * Colore nero associato al nodo.
     */
    public static int COLOR_BLACK = 2;

    private final L label;

    private int color;

    private double floatingPointDistance;

    private int integerDistance;

    private int enteringTime;

    private int exitingTime;

    private GraphNode<L> previous;

    /**
     * Costruisce un nodo assegnando tutti i valori associati ai valori di
     * default.
     * 
     * @param label
     *                  l' etichetta da associare al nodo
     * 
     * @throws NullPointerException
     *                                  se l' etichetta è null.
     */
    public GraphNode(L label) {
        if (label == null)
            throw new NullPointerException("Etichetta nel nodo nulla");
        this.label = label;
    }

    /**
     * Restituisce l' etichetta associata al nodo che lo identifica univocamente
     * nel grafo.
     * 
     * @return the label
     */
    public L getLabel() {
        return this.label;
    }

    /**
     * Restituisce il colore corrente del nodo.
     * 
     * @return the color
     */
    public int getColor() {
        return this.color;
    }

    /**
     * 
     * Assegna al nodo un certo colore.
     * 
     * @param color
     *                  the color to set
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Restituisce il valore corrente di una distanza intera associata al nodo.
     * 
     * @return the distance
     */
    public int getIntegerDistance() {
        return this.integerDistance;
    }

    /**
     * Assegna al nodo un valore di una distanza intera ad esso associata.
     * 
     * @param distance
     *                     the distance to set
     */
    public void setIntegerDistance(int distance) {
        this.integerDistance = distance;
    }

    /**
     * Restituisce il valore corrente di una distanza associata al nodo.
     * 
     * @return the distance
     */
    public double getFloatingPointDistance() {
        return this.floatingPointDistance;
    }

    /**
     * Assegna al nodo un valore di una distanza ad esso associata.
     * 
     * @param distance
     *                     the distance to set
     */
    public void setFloatingPointDistance(double distance) {
        this.floatingPointDistance = distance;
    }

    /**
     * Restituisce il nodo del grafo che correntemente è assegnato come
     * predecessore di questo nodo. Ad esempio può essere usato da un algoritmo
     * che costruisce un albero di copertura.
     * 
     * @return the previous
     */
    public GraphNode<L> getPrevious() {
        return this.previous;
    }

    /**
     * 
     * Assegna a questo nodo un nodo predecessore.
     * 
     * @param previous
     *                     the previous to set
     */
    public void setPrevious(GraphNode<L> previous) {
        this.previous = previous;
    }

    /**
     * Restituisce il tempo di ingresso in questo nodo durante una visita in
     * profondità.
     * 
     * @return il tempo di ingresso in questo nodo durante una visita in
     *         profondità
     */
    public int getEnteringTime() {
        return this.enteringTime;
    }

    /**
     * Assegna un tempo di ingresso in questo nodo durante una visita in
     * profondità.
     * 
     * @param time
     *                 il tempo di ingresso da assegnare
     */
    public void setEnteringTime(int time) {
        this.enteringTime = time;
    }

    /**
     * Restituisce il tempo di uscita da questo nodo durante una visita in
     * profondità.
     * 
     * @return il tempo di uscita da questo nodo durante una visita in
     *         profondità
     */
    public int getExitingTime() {
        return this.exitingTime;
    }

    /**
     * Assegna un tempo di uscita da questo nodo durante una visita in
     * profondità.
     * 
     * @param time
     *                 il tempo di uscita da assegnare
     */
    public void setExitingTime(int time) {
        this.exitingTime = time;
    }

    /*
     * Basato sull' hashCode dell' etichetta.
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return this.label.hashCode();
    }

    /*
     * Basato sull' etichetta, che non può essere null.
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof GraphNode))
            return false;
        GraphNode<?> other = (GraphNode<?>) obj;
        if (this.label.equals(other.label))
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "Nodo[ " + label.toString() + " ]";
    }

    @Override
    public double getPriority() {
        return this.floatingPointDistance;
    }

    @Override
    public void setPriority(double newPriority) {
        this.floatingPointDistance = newPriority;

    }

    @Override
    public int getHandle() {
        return this.integerDistance;
    }

    @Override
    public void setHandle(int newHandle) {
        this.integerDistance = newHandle;
    }

}