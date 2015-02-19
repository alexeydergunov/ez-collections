package ez.collections;

/**
 * The interface for priority queues.
 * <p>
 * Priority queue in EZ Collections is an analogue of {@link java.util.PriorityQueue} (but is an interface and can have
 * different implementations). Priority queue allows you to add elements and get/remove the minimal element.
 * @author Alexey Dergunov
 * @since 0.1.0
 * @see ez.collections.heap._Ez_Int_Heap
 * @see ez.collections.heap._Ez_Int_MaxHeap
 * @see ez.collections.heap._Ez_Int_CustomHeap
 */
public interface _Ez_Int_PriorityQueue extends _Ez_Int_Collection {
    /**
     * Returns the size of the priority queue, i.e. the number of elements in it.
     * @return the size of the priority queue
     */
    @Override
    int size();

    /**
     * Checks if the priority queue contains any elements.
     * @return {@code true} if the priority queue doesn't contain any elements, {@code false} otherwise
     */
    @Override
    boolean isEmpty();

    /**
     * Checks if the priority queue contains the specified element.
     * @param element the element to be checked
     * @return {@code true} if the priority queue contains the specified element, {@code false} otherwise
     */
    @Override
    boolean contains(/*C*/int/*C*/ element);

    /**
     * Returns the iterator which can be used to go through the elements of this priority queue. It is not guaranteed
     * that the iterator will return the elements in any particular order.
     * @return the iterator to go through the elements of this priority queue
     */
    @Override
    _Ez_Int_Iterator iterator();

    /**
     * Returns the array which contains all elements in this priority queue. It is not guaranteed that the array will
     * be ordered in any particular order (you still can sort it by yourself). This method always allocates new array,
     * so you can modify it and the original priority queue won't be changed, and vice versa.
     * @return the array which contains all elements in this priority queue
     */
    @Override
    /*C*/int/*C*/[] toArray();

    /**
     * Adds the element into this priority queue.
     * @param element the element to be added
     * @return always returns {@code true} (for compatibility with the parent interface {@link _Ez_Int_Collection})
     */
    @Override
    boolean add(/*C*/int/*C*/ element);

    /**
     * Removes the element from this priority queue. If this priority queue contains many instances of this element,
     * only one of them will be removed.
     * @param element the element to be removed
     * @return {@code true} if the element was successfully removed (or, in other words, if the priority queue was
     * changed), {@code false} otherwise
     */
    @Override
    boolean remove(/*C*/int/*C*/ element);

    /**
     * Removes all elements from this priority queue.
     */
    @Override
    void clear();

    /**
     * Checks if this priority queue is equal to other object. For equality the passed object should be of the same
     * class as this priority queue and contain the same elements as this priority queue.
     * @param object the object to be checked for equality
     * @return {@code true} if this priority queue is equal to the specified object, {@code false} otherwise
     */
    @Override
    boolean equals(Object object);

    /**
     * Returns the hashcode of the priority queue. If two priority queues are equal, their hashcodes are also equal.
     * If the hashcodes of two priority queues are different, these priority queues are also different. Please note
     * that different priority queues can have equal hashcodes, though the probability of this fact is low.
     * @return the hashcode of the priority queue
     */
    @Override
    int hashCode();

    /**
     * Returns the human-readable string representation of the priority queue.
     * @return the string representation of the priority queue
     */
    @Override
    String toString();

    /**
     * Returns the minimal element in the priority queue.
     * @return the minimal element in the priority queue
     * @throws java.util.NoSuchElementException if the priority queue is empty
     */
    /*C*/int/*C*/ getFirst();

    /**
     * Removes the minimal element in the priority queue.
     * @return the removed element
     * @throws java.util.NoSuchElementException if the priority queue is empty
     */
    /*C*/int/*C*/ removeFirst();
}
