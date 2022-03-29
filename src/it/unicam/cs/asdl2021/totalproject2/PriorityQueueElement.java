/**
 * 
 */
package it.unicam.cs.asdl2021.totalproject2;

/**
 * Interface to enable generic objects to be inserted into a "dynamic" priority
 * queue, i.e., a priority queue in which the priority may be updated while the
 * element is already present in the queue. Typically, a <i>handle</i> has to be
 * associated with the element, which links it directly to its representation in
 * the data structure supporting the priority queue operations. This interface
 * assumes that the handle is an <code>int</code> being the index of an array
 * representing a binary (or d-ary in general) heap implementing the operations
 * of the priority queue.
 * 
 * @author Luca Tesei
 *
 */
public interface PriorityQueueElement {
    /**
     * @return the current priority associated with the element
     */
    public double getPriority();

    /**
     * Set the priority of an element to a new value.
     * 
     *                        the new value of the priority
     */
    public void setPriority(double newPriority);

    /**
     * @return the current handle of the element
     */
    public int getHandle();

    /**
     * Set the handle of an element to a new value.
     * 
     * @param newHandle
     *                      the new value of the handle
     */
    public void setHandle(int newHandle);
}
