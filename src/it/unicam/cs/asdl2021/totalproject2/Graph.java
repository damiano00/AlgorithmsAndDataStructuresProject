package it.unicam.cs.asdl2021.totalproject2;

import java.util.Set;

/**
 * Classe astratta per un generico grafo i cui nodi sono etichettati con
 * elementi della classe {@code L}. Le classi {@code GraphNode<L>} e
 * {@code GraphEdge<L>} definiscono le operazioni generiche sui nodi e sugli
 * archi.
 * 
 * Il grafo può essere orientato o non orientato, la sottoclasse che estende
 * questa classe astratta specifica questo aspetto. Tale informazione è
 * disponibile tramite il metodo {@code isDirected()}.
 * 
 * Le etichette dei nodi sono obbligatorie e uniche, cioè un nodo non può avere
 * etichetta nulla e due nodi con la stessa etichetta sono lo stesso nodo.
 * 
 * 
 * @author Luca Tesei
 * 
 * @param <L>
 *                etichette dei nodi
 *
 */
public abstract class Graph<L> {

    /**
     * Restituisce il numero di nodi in questo grafo.
     * 
     * @return il numero di nodi in questo grafo.
     */
    public abstract int nodeCount();

    /**
     * Restituisce il numero di archi in questo grafo.
     * 
     * @return il numero di archi in questo grafo.
     */
    public abstract int edgeCount();

    /**
     * Restituisce la dimensione di questo grafo definita come il numero di nodi
     * più il numero di archi.
     * 
     * @return la dimensione di questo grafo definita come il numero dei nodi
     *         più il numero degli archi.
     */
    public int size() {
        return this.nodeCount() + this.edgeCount();
    }

    /**
     * Determina se questo grafo è vuoto, cioè senza nodi e senza archi.
     * 
     * @return se questo grafo è vuoto, false altrimenti.
     */
    public boolean isEmpty() {
        // se non ci sono nodi non ci possono essere neanche archi
        return this.nodeCount() == 0;
    }

    /**
     * Cancella tutti i nodi e gli archi di questo grafo portandolo ad essere un
     * grafo vuoto.
     */
    public abstract void clear();

    /**
     * Determina se questo grafo è orientato oppure no.
     * 
     * @return true se questo grafo è orientato, false altrimenti.
     */
    public abstract boolean isDirected();

    /**
     * Restituisce l' insieme dei nodi di questo grafo.
     * 
     * @return l'insieme dei nodi di questo grafo, possibilmente vuoto.
     */
    public abstract Set<GraphNode<L>> getNodes();

    /**
     * Aggiunge un nodo a questo grafo.
     * 
     * @param node
     *                 il nuovo nodo da aggiungere
     * @return true se il nodo è stato aggiunto, false altrimenti cioè se il
     *         nodo è già presente
     * @throws NullPointerException
     *                                  se il nodo passato è null
     */
    public abstract boolean addNode(GraphNode<L> node);

    /**
     * Rimuove un nodo da questo grafo. Tutti gli archi collegati al nodo
     * vengono anch' essi eliminati.
     * 
     * Questa operazione è opzionale.
     * 
     * @param node
     *                 il nodo da rimuovere
     * @return true se il nodo è stato rimosso, false se il nodo non era
     *         presente
     * @throws NullPointerException
     *                                           se il nodo passato è null
     * 
     * @throws UnsupportedOperationException
     *                                           se l' implementazione del grafo
     *                                           non supporta questa operazione
     */
    public abstract boolean removeNode(GraphNode<L> node);

    /**
     * Determina se c'è un certo nodo in questo grafo.
     * 
     * @param node
     *                 il nodo cercato
     * @return true se il nodo {@code node} esiste in questo grafo
     * 
     * @throws NullPointerException
     *                                  se il nodo passato è null
     */
    public abstract boolean containsNode(GraphNode<L> node);

    /**
     * Restituisce il nodo di questo grafo avente l' etichetta passata.
     * 
     * @param label
     *                  l' etichetta del nodo da restituire
     * 
     * @return il nodo di questo grafo che ha l' etichetta uguale a
     *         {@code label}, null se tale nodo non esiste in questo grafo
     * 
     * @throws NullPointerException
     *                                  se l' etichetta è nulla
     * 
     */
    public abstract GraphNode<L> getNodeOf(L label);

    /**
     * Restituisce un indice unico attualmente associato a un certo nodo
     * nell' intervallo <code>[0, this.nodeCount() - 1]</code>. Questa
     * funzionalità è tipicamente disponibile se il grafo è rappresentato con
     * una matrice di adiacenza. Tale indice può anche essere usato per
     * identificare i nodi in strutture dati esterne come array o liste che
     * contengono informazioni aggiuntive calcolate, ad esempio, da un algoritmo
     * sul grafo.
     * 
     * Questa operazione è opzionale.
     * 
     * @param label
     *                  l' etichetta del nodo di cui restituire l' indice
     * 
     * @return un indice unico nell' intervallo
     *         <code>[0, this.nodeCount() - 1]</code> attualmente associato al
     *         nodo con etichetta {@code label}. L' indice può cambiare se il
     *         grafo viene modificato togliendo dei nodi
     * 
     * @throws NullPointerException
     *                                           se l' etichetta passata è null
     * @throws IllegalArgumentException
     *                                           se un nodo con l' etichetta
     *                                           {@code label} non esiste in
     *                                           questo grafo
     * 
     * @throws UnsupportedOperationException
     *                                           se questa operazione non è
     *                                           supportata dall' implementazione
     *                                           di questo grafo
     */
    public abstract int getNodeIndexOf(L label);

    /**
     * Restituisce il nodo attualmente associato a un certo indice
     * nell' intervallo <code>[0, this.nodeCount() - 1]</code>. Questa
     * funzionalità è tipicamente disponibile se il grafo è rappresentato con
     * una matrice di adiacenza.
     * 
     * Questa operazione è opzionale.
     * 
     * @param i
     *              l' indice del nodo.
     * @return il nodo correntemente associato all' indice i
     * 
     * @throws IndexOutOfBoundsException
     *                                           se l' indice passato non
     *                                           corrisponde a nessun nodo o è
     *                                           fuori dai limiti
     *                                           dell' intervallo
     *                                           <code>[0, this.nodeCount() - 1]</code>
     * @throws UnsupportedOperationException
     *                                           se questa operazione non è
     *                                           supportata dall' implementazione
     *                                           di questo grafo
     */
    public abstract GraphNode<L> getNodeAtIndex(int i);

    /**
     * Restituisce, se esiste, l' arco che connette due nodi. In caso di grafo
     * non orientato l' ordine dei due nodi è ininfluente, in caso di grafo
     * orientato viene ricercato l' arco che connette il primo nodo specificato
     * al secondo.
     * 
     * @param node1
     *                  il primo nodo
     * @param node2
     *                  il secondo nodo
     * @return un arco che connette node1 e node2 nel grafo oppure null se tale
     *         arco non esiste nel grafo. Se il grafo è orientato l' arco
     *         ricercato e restituito è quello che ha sorgente in node 1 e
     *         destinazione in node2. Se il grafo non è orientato allora l' arco
     *         restituito può contenere node1 e node 2 in qualsiasi ordine
     * @throws NullPointerException
     *                                      se almeno uno dei due nodi passati è
     *                                      null
     * @throws IllegalArgumentException
     *                                      se almeno uno dei due nodi passati
     *                                      non esiste nel grafo
     */
    public abstract GraphEdge<L> getEdge(GraphNode<L> node1,
            GraphNode<L> node2);

    /**
     * Restituisce, se esiste, l' arco che connette due nodi a indici
     * specificati. In caso di grafo non orientato l' ordine dei due nodi è
     * ininfluente, in caso di grafo orientato viene ricercato l' arco che
     * connette il primo nodo specificato al secondo.
     * 
     * @param i
     *              l' indice del primo nodo
     * @param j
     *              l' indice del secondo nodo
     * @return un arco che connette il nodo all' indice i e il nodo all' indice j
     *         nel grafo oppure null se tale arco non esiste nel grafo. Se il
     *         grafo è orientato l' arco ricercato e restituito è quello che ha
     *         sorgente nel nodo di indice i e destinazione nel nodo di indice
     *         j. Se il grafo non è orientato allora l' arco restituito può
     *         contenere il nodo di indice i e il nodo di indice j in qualsiasi
     *         ordine
     * @throws IndexOutOfBoundsException
     *                                           se almeno uno degli indici
     *                                           passati non corrisponde a
     *                                           nessun nodo o è fuori dai
     *                                           limiti dell' intervallo
     *                                           <code>[0, this.nodeCount() - 1]</code>
     * @throws UnsupportedOperationException
     *                                           se questa operazione non è
     *                                           supportata dall' implementazione
     *                                           di questo grafo
     */
    public abstract GraphEdge<L> getEdgeAtNodeIndexes(int i, int j);

    /**
     * Restituisce l' insieme di tutti i nodi adiacenti a un certo nodo. Se il
     * grafo è orientato, i nodi restituiti sono solo quelli collegati da un
     * arco uscente dal nodo passato.
     * 
     * @param node
     *                 il nodo di cui cercare i nodi adiacenti
     * @return l'insieme di tutti i nodi adiacenti al nodo specificato
     * 
     * @throws IllegalArgumentException
     *                                      se il nodo passato non esiste
     * @throws NullPointerException
     *                                      se il nodo passato è nullo
     */
    public abstract Set<GraphNode<L>> getAdjacentNodesOf(GraphNode<L> node);

    /**
     * Restituisce l' insieme di tutti i nodi collegati tramite un arco entrante
     * in un certo nodo in un grafo orientato.
     * 
     * @param node
     *                 il nodo di cui cercare i nodi successori
     * @return l'insieme di tutti i nodi collegati tramite un arco entrante al
     *         nodo specificato
     * @throws UnsupportedOperationException
     *                                           se il grafo su cui il metodo è
     *                                           chiamato non è orientato
     * @throws IllegalArgumentException
     *                                           se il nodo passato non esiste
     * @throws NullPointerException
     *                                           se il nodo passato è nullo
     */
    public abstract Set<GraphNode<L>> getPredecessorNodesOf(GraphNode<L> node);

    /**
     * Restituisce l' insieme di tutti gli archi in questo grafo.
     * 
     * @return un insieme, possibilmente vuoto, contenente tutti gli archi di
     *         questo grafo
     */
    public abstract Set<GraphEdge<L>> getEdges();

    /**
     * Aggiunge un arco a questo grafo.
     * 
     * @param edge
     *                 l' arco da inserire
     * @return true se l' arco è stato inserito, false se un arco esattamente
     *         uguale già esiste
     * @throws NullPointerException
     *                                      se l' arco passato è nullo
     * @throws IllegalArgumentException
     *                                      se almeno uno dei due nodi
     *                                      specificati nell' arco non esiste
     * @throws IllegalArgumentException
     *                                      se l' arco è orientato e questo grafo
     *                                      non è orientato o viceversa
     */
    public abstract boolean addEdge(GraphEdge<L> edge);

    /**
     * Rimuove un arco da questo grafo.
     * 
     * @param edge
     *                 l' arco da rimuovere
     * 
     * @return true se l' arco è stato rimosso, false se l' arco non era presente
     * @throws IllegalArgumentException
     *                                           se almeno uno dei due nodi
     *                                           specificati nell' arco non
     *                                           esiste
     * @throws NullPointerException
     *                                           se l' arco passato è nullo
     * @throws UnsupportedOperationException
     *                                           se l' implementazione del grafo
     *                                           non supporta questa operazione
     */
    public abstract boolean removeEdge(GraphEdge<L> edge);

    /**
     * Cerca se c'è un certo arco in questo grafo.
     * 
     * @param edge
     *                 l' arco da cercare
     * 
     * @return true se in questo grafo c'è l' arco specificato, false altrimenti
     * 
     * @throws NullPointerException
     *                                      se l' arco passato è nullo
     * @throws IllegalArgumentException
     *                                      se almeno uno dei due nodi
     *                                      specificati nell' arco non esiste
     * 
     */
    public abstract boolean containsEdge(GraphEdge<L> edge);

    /**
     * Restituisce l' insieme di tutti gli archi connessi a un certo nodo in un
     * grafo. Nel caso di grafo orientato vengono restituiti solo gli archi
     * uscenti.
     * 
     * @param node
     *                 il nodo di cui sono richiesti gli archi connessi
     * 
     * @return un insieme contenente tutti gli archi connessi al nodo
     *         specificato in questo grafo (solo gli archi uscenti in caso di
     *         grafo orientato)
     * 
     * @throws IllegalArgumentException
     *                                      se il nodo passato non esiste
     * @throws NullPointerException
     *                                      se il nodo passato è nullo
     */
    public abstract Set<GraphEdge<L>> getEdgesOf(GraphNode<L> node);

    /**
     * Restituisce l' insieme di tutti gli archi entranti in un certo nodo in un
     * grafo orientato.
     * 
     * @param node
     *                 il nodo di cui determinare tutti gli archi entranti
     * 
     * @return un insieme contenente tutti gli archi entranti nel nodo con
     *         etichetta label in questo grafo orientato.
     * 
     * @throws UnsupportedOperationException
     *                                           se il grafo su cui il metodo è
     *                                           chiamato non è orientato
     * @throws IllegalArgumentException
     *                                           se il nodo passato non esiste
     * @throws NullPointerException
     *                                           se il nodo passato è nullo
     */
    public abstract Set<GraphEdge<L>> getIngoingEdgesOf(GraphNode<L> node);

    /**
     * Restituisce il grado di un nodo, cioè il numero di archi connessi al
     * nodo. Nel caso di grafo orientato è la somma del numero di archi in
     * entrata e del numero di archi in uscita.
     * 
     * @param node
     *                 il nodo di cui calcolare il grado
     * @return il grado del nodo passato
     * 
     * @throws IllegalArgumentException
     *                                      se il nodo passato non esiste
     * @throws NullPointerException
     *                                      se il nodo passato è nullo
     */
    public int getDegreeOf(GraphNode<L> node) {
        if (!this.isDirected())
            return this.getEdgesOf(node).size();
        else
            return this.getEdgesOf(node).size()
                    + this.getIngoingEdgesOf(node).size();
    }

}
