package ez.collections;

/**
 * The interface for queues.
 * <p>
 * Queue in EZ Collections is an analogue of {@link java.util.Queue}. Queue is a sequence of elements that allows you
 * to add elements to the end and get/remove elements from the beginning.
 * @author Alexey Dergunov
 * @since 0.0.1
 * @see ez.collections.arraydeque._Ez_Int_ArrayDeque
 */
public interface _Ez_Int_Queue extends _Ez_Int_Collection {
    /**
     * Returns the size of the queue, i.e. the number of elements in it.
     * @return the size of the queue
     */
    @Override
    int size();

    /**
     * Checks if the queue contains any elements.
     * @return {@code true} if the queue doesn't contain any elements, {@code false} otherwise
     */
    @Override
    boolean isEmpty();

    /**
     * Checks if the queue contains the specified element.
     * @param element the element to be checked
     * @return {@code true} if the queue contains the specified element, {@code false} otherwise
     */
    @Override
    boolean contains(/*T*/int/*T*/ element);

    /**
     * Returns the iterator which can be used to go through the elements of this queue, from first to last.
     * @return the iterator to go through the elements of this queue
     */
    @Override
    _Ez_Int_Iterator iterator();

    /**
     * Returns the array which contains all elements in this queue, from first to last. This method always allocates
     * new array, so you can modify it and the original queue won't be changed, and vice versa.
     * @return the array which contains all elements in this queue
     */
    @Override
    /*T*/int/*T*/[] toArray();

    /**
     * Adds the element to the end of this queue (the same as {@link #addLast}).
     * @param element the element to be added
     * @return always returns {@code true} (for compatibility with the interface {@link _Ez_Int_Collection})
     */
    @Override
    boolean add(/*T*/int/*T*/ element);

    /**
     * Removes the element from this queue. If this queue contains many instances of this element, the first of them
     * will be removed.
     * @param element the element to be removed
     * @return {@code true} if the element was successfully removed (or, in other words, if the queue was changed),
     * {@code false} otherwise
     */
    @Override
    boolean remove(/*T*/int/*T*/ element);

    /**
     * Removes all elements from this queue.
     */
    @Override
    void clear();

    /**
     * Checks if this queue is equal to other object. For equality the passed object should be of the same class as
     * this queue and contain the same elements in the same order as this queue.
     * @param object the object to be checked for equality
     * @return {@code true} if this queue is equal to the specified object, {@code false} otherwise
     */
    @Override
    boolean equals(Object object);

    /**
     * Returns the hashcode of the queue. If two queues are equal, their hashcodes are also equal. If the hashcodes of
     * two queues are different, these queues are also different. Please note that different queues can have equal
     * hashcodes, though the probability of this fact is low.
     * @return the hashcode of the queue
     */
    @Override
    int hashCode();

    /**
     * Returns the human-readable string representation of the queue.
     * @return the string representation of the queue
     */
    @Override
    String toString();

    /**
     * Adds the element to the end of the queue.
     * @param element the element to be added
     */
    void addLast(/*T*/int/*T*/ element);

    /**
     * Returns the first element in the queue.
     * @return the first element in the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    /*T*/int/*T*/ getFirst();

    /**
     * Removes the first element in the queue.
     * @return the removed element
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    /*T*/int/*T*/ removeFirst();
}
