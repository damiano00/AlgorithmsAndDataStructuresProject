/**
 * 
 */
package it.unicam.cs.asdl2021.totalproject2;

/**
 * Questa classe raggruppa le caratteristiche di un arco, possibilmente pesato
 * ed etichettato, facente parte di un grafo. I nodi del grafo sono etichettati
 * con oggetti della classe {@code L}. Le classi {@code GraphNode<L>} e
 * {@code Graph<L>} definiscono le operazioni generiche sui nodi e sul grafo,
 * rispettivamente.
 * 
 * Un arco può essere orientato o non orientato, tale informazione è immutabile
 * e disponibile tramite il metodo {@code isDirected()}. I due nodi collegati
 * dall' arco sono immutabili e non possono essere nulli.
 * 
 * All' arco può essere associato un peso tramite i metodi
 * {@code setWeight(double} e {@code getWeight()}. Il peso, se non specificato
 * nel costruttore, è inizializzato automaticamente a {@code Double.NaN}. In tal
 * caso l' arco è considerato non pesato fino a quando non gli viene assegnato un
 * valore diverso da Double.NaN.
 * 
 * Due archi sono uguali se e solo se collegano gli stessi nodi e sono entrambi
 * orientati o entrambi non orientati. Nel caso di archi non orientati l' ordine
 * dei nodi non conta, cioè un arco tra {@code n1} ed {@code n2} e un arco tra
 * {@code n2} ed {@code n1} sono considerati lo stesso arco.
 * 
 * @author Luca Tesei
 * 
 * @param <L>
 *                etichette dei nodi del grafo
 *
 */
public class GraphEdge<L> {

    private final GraphNode<L> node1;

    private final GraphNode<L> node2;

    private final boolean directed;

    private double weight;

    /**
     * Costruisce un arco pesato di un grafo.
     * 
     * @param node1
     *                     primo nodo (nodo sorgente in caso di grafo orientato)
     * @param node2
     *                     secondo nodo (nodo destinazione in caso di grafo
     *                     orientato)
     * @param directed
     *                     true se l' arco è orientato, false altrimenti
     * @param weight
     *                     peso associato all' arco
     * 
     * @throws NullPointerException
     *                                  se almeno uno dei due nodi è nullo
     */
    public GraphEdge(GraphNode<L> node1, GraphNode<L> node2, boolean directed,
            double weight) {
        if (node1 == null)
            throw new NullPointerException("Nodo 1 dell' arco nullo");
        if (node2 == null)
            throw new NullPointerException("Nodo 2 dell' arco nullo");
        this.node1 = node1;
        this.node2 = node2;
        this.directed = directed;
        this.weight = weight;
    }

    /**
     * Costruisce un arco di un grafo.
     * 
     * @param node1
     *                     primo nodo (nodo sorgente in caso di grafo orientato)
     * @param node2
     *                     secondo nodo (nodo destinazione in caso di grafo
     *                     orientato)
     * @param directed
     *                     true se l' arco è orientato, false altrimenti
     * 
     * @throws NullPointerException
     *                                  se almeno uno dei due nodi è nullo
     */
    public GraphEdge(GraphNode<L> node1, GraphNode<L> node2, boolean directed) {
        if (node1 == null)
            throw new NullPointerException("Nodo 1 dell' arco nullo");
        if (node2 == null)
            throw new NullPointerException("Nodo 2 dell' arco nullo");
        this.node1 = node1;
        this.node2 = node2;
        this.directed = directed;
        this.weight = Double.NaN;
    }

    /**
     * Determina se questo arco è attualmente pesato.
     * 
     * @return true se questo arco ha attualmente associato un peso diverso da
     *         Double.NaN
     */
    public boolean hasWeight() {
        return !Double.isNaN(this.weight);
    }

    /**
     * Restituisce il primo nodo di questo arco, la sorgente in caso di arco
     * orientato.
     * 
     * @return il primo nodo di questo arco, la sorgente in caso di arco
     *         orientato.
     */
    public GraphNode<L> getNode1() {
        return this.node1;
    }

    /**
     * Restituisce il secondo nodo di questo arco, la destinazione in caso di
     * arco orientato.
     * 
     * @return il secondo nodo di questo arco, la destinazione in caso di arco
     *         orientato.
     */
    public GraphNode<L> getNode2() {
        return this.node2;
    }

    /**
     * Indica se questo arco è orientato o no.
     * 
     * @return true se questo arco è orientato, false altrimenti.
     */
    public boolean isDirected() {
        return this.directed;
    }

    /**
     * Restituisce il peso assegnato all' arco. Nel caso in cui il peso è uguale
     * a Double.Nan l' arco è da considerarsi attualmente non pesato.
     * 
     * @return il peso associato all' arco
     */
    public double getWeight() {
        return this.weight;
    }

    /**
     * Assegna un certo peso a questo arco.
     * 
     * @param weight
     *                   il peso da assegnare a questo arco
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /*
     * Basato sul flag che definisce se l' arco è orientato o no e sugli hashCode
     * dei due nodi collegati.
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (directed ? 1231 : 1237);
        // Modificata l' implementazione standard per rispettare la proprietà
        // dell' hashCode anche nel caso di archi non orientati con ordine
        // diverso
        result = prime * result + (node1.hashCode() + node2.hashCode());
        return result;
    }

    /*
     * Due archi sono uguali se sono entrambi orientati o non orientati e se
     * collegano nodi uguali. Nel caso in cui l' arco non sia orientato l' ordine
     * dei nodi non conta.
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof GraphEdge))
            return false;
        GraphEdge<?> other = (GraphEdge<?>) obj;
        if (directed != other.directed)
            return false;
        // C'è differenza tra caso non orientato e caso orientato
        if (directed) {
            if (!node1.equals(other.node1))
                return false;
            if (!node2.equals(other.node2))
                return false;
            return true;
        } else { // caso speciale per grafi non orientati
            // ci deve essere una uguaglianza diretta o incrociata
            if (node1.equals(other.node1) && node2.equals(other.node2))
                return true;
            if (node1.equals(other.node2) && node2.equals(other.node1))
                return true;
            // Altrimenti non sono uguali
            return false;
        }
    }

    @Override
    public String toString() {
        if (this.directed)
            return "Edge [ " + this.node1.toString() + " --> "
                    + this.node2.toString() + " ]";
        else
            return "Edge [ " + this.node1.toString() + " -- "
                    + this.node2.toString() + " ]";
    }
}
