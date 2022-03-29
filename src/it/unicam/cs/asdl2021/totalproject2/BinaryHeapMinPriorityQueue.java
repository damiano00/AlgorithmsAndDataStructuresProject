
package it.unicam.cs.asdl2021.totalproject2;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Implementazione di una coda con priorità tramite heap binario. Gli oggetti
 * inseriti in coda implementano l' interface PriorityQueueElement che permette
 * di gestire la priorità e una handle dell' elemento. La handle è fondamentale
 * per realizzare in tempo logaritmico l' operazione di decreasePriority che,
 * senza la handle, dovrebbe cercare l' elemento all' interno dello heap e poi
 * aggiornare la sua posizione. Nel caso di heap binario rappresentato con una
 * ArrayList la handle è semplicemente l' indice dove si trova l' elemento
 * nell' ArrayList. Tale campo naturalmente va tenuto aggiornato se l' elemento
 * viene spostato in un' altra posizione.
 * 
 * @author Template: Luca Tesei
 * @author Implementation: Damiano Pasquini
 * 
 * @param <E>
 *                il tipo degli elementi che vengono inseriti in coda.
 *
 */
public class BinaryHeapMinPriorityQueue<E> {

    /*
     * ArrayList per la rappresentazione dello heap. Vengono usate tutte le
     * posizioni (la radice dello heap è quindi in posizione 0).
     */
    private ArrayList<PriorityQueueElement> heap;

    /**
     * Crea una coda con priorità vuota.
     *
     */
    public BinaryHeapMinPriorityQueue() {
        this.heap = new ArrayList<>();
    }

    /**
     * Add an element to this min-priority queue. The current priority
     * associated with the element will be used to place it in the correct
     * position in the heap. The handle of the element will also be set
     * accordingly.
     * 
     * @param element
     *                    the new element to add
     * @throws NullPointerException
     *                                  if the element passed is null
     */
    public void insert(PriorityQueueElement element) {
        // controlla che l'elemento passato non sia null
        if(element == null)
            throw new NullPointerException("Priority queue element can't be null");

        // aggiunge l'elemento in coda all'heap e imposta il suo indice come size()-1
        this.heap.add(element);
        this.heap.get(this.heap.size()-1).setHandle(this.size()-1);
        // salva i due elementi in variabili temporanee
        PriorityQueueElement currElement = element;
        PriorityQueueElement currElementParent = this.getParent(element.getHandle());
        // finché la priorità dell'elemento è minore della priorità del suo parent
        while ((this.heap.size() > 1) && (currElement.getPriority() < currElementParent.getPriority())) {
            this.swap(element.getHandle(), this.getParent(element.getHandle()).getHandle());
            // aggiornare variabili temporanee per ciclo while successivo
            currElement = this.heap.get(element.getHandle());
            currElementParent = this.getParent(currElement.getHandle());
        }
    }

    /**
     * Returns the current minimum element of this min-priority queue without
     * extracting it. This operation does not affect the heap.
     * 
     * @return the current minimum element of this min-priority queue
     * 
     * @throws NoSuchElementException
     *                                    if this min-priority queue is empty
     */
    public PriorityQueueElement minimum() {
        // controllo che l'heap non sia vuoto
        if(heap.isEmpty())
            throw new NoSuchElementException("This priority queue must contain minimum an element");
        // se non è vuoto restituisco l'elemento con priorità minima, ovvero quello alla radice
        return heap.get(0);
    }

    /**
     * Extract the current minimum element from this min-priority queue. The
     * ternary heap will be updated accordingly.
     * 
     * @return the current minimum element
     * @throws NoSuchElementException
     *                                    if this min-priority queue is empty
     */
    public PriorityQueueElement extractMinimum() {
        // controllo che l'heap non sia vuoto
        if(heap.isEmpty())
            throw new NoSuchElementException("This priority queue must contain at least an element");

        // salva l'elemento posizionato nella radice per poi restituirlo in output
        PriorityQueueElement elementToReturn = this.heap.get(0);
        // posiziona l'ultimo elemento della coda nella radice assegnandogli l'handle corretto (0)
        this.heap.set(0, this.heap.get(this.size()-1));
        // per poi cancellarlo dall'ultima posizione (la size dell'heap diminuisce di 1)
        this.heap.remove(this.size()-1);
        // imposta l'indice dell'elemento estratto a -1
        elementToReturn.setHandle(-1);
        // se la coda è vuota restituisci l'elemento estratto
        if(this.heap.isEmpty()) return elementToReturn;
        // imposta l'indice della radice a 0
        this.heap.get(0).setHandle(0);
        // esegue il metodo minHeapify per aggiornare i nodi sotto alla radice basandosi sulle priorità
        this.minHeapify(0);
        // restituisce l'elemento precedentemente nella radice
        return elementToReturn;
    }

    /**
     * Decrease the priority associated to an element of this min-priority
     * queue. The position of the element in the heap must be changed
     * accordingly. The changed element may become the minimum element. The
     * handle of the element will also be changed accordingly.
     * 
     * @param element
     *                        the element whose priority will be decreased, it
     *                        must currently be inside this min-priority queue
     * @param newPriority
     *                        the new priority to assign to the element
     * 
     * @throws NoSuchElementException
     *                                      if the element is not currently
     *                                      present in this min-priority queue
     * @throws IllegalArgumentException
     *                                      if the specified newPriority is not
     *                                      strictly less than the current
     *                                      priority of the element
     */
    public void decreasePriority(PriorityQueueElement element, double newPriority) {
        if(!this.heap.contains(element))
            throw new NoSuchElementException("This element is not contained in this heap");
        if(newPriority >= element.getPriority())
            throw new IllegalArgumentException("The specified newPriority is not strictly less than the current priority of the element");

        element.setPriority(newPriority);
        // finché l'elemento non diventa la radice o finché la sua priorità non è minore della priorità del suo genitore
            // scambia l'elemento con il suo genitore
        while((element.getHandle() != 0) && (element.getPriority() < this.getParent(element.getHandle()).getPriority())){
            this.swap(element.getHandle(), this.getParent(element.getHandle()).getHandle());
        }
    }

    /**
     * Determines if this priority queue is empty.
     * 
     * @return true if this priority queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.heap.size() == 0;
    }

    /**
     * Return the current size of this queue.
     * 
     * @return the number of elements currently in this queue.
     */
    public int size() {
        return this.heap.size();
    }

    /**
     * Erase all the elements from this min-priority queue. After this operation
     * this min-priority queue is empty.
     */
    public void clear() {
        this.heap.clear();
    }

    private PriorityQueueElement getParent(int index){
        return this.heap.get((index-1)/2);
    }

    private void swap(int firstElement, int secondElement){
        PriorityQueueElement priorityQueueElement = this.heap.get(firstElement);

        this.heap.get(firstElement).setHandle(secondElement);
        this.heap.get(secondElement).setHandle(firstElement);

        this.heap.set(firstElement, this.heap.get(secondElement));
        this.heap.set(secondElement, priorityQueueElement);
    }

    private void minHeapify(int index) {
        int smallestChildIndex = index;
        int leftChildIndex = (index*2)+1;
        int rightChildIndex = (index*2)+2;

        if(leftChildIndex < this.size())
            if(this.heap.get(leftChildIndex).getPriority() < this.heap.get(index).getPriority())
                smallestChildIndex = leftChildIndex;
        if(rightChildIndex < this.size())
            if(this.heap.get(rightChildIndex).getPriority() < this.heap.get(smallestChildIndex).getPriority())
                smallestChildIndex = rightChildIndex;
        if(smallestChildIndex != index){
            swap(index, smallestChildIndex);
            minHeapify(smallestChildIndex);
        }
    }
}
