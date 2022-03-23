package ez.collections;

/**
 * The interface for deques.
 * <p>
 * Deque in EZ Collections is an analogue of {@link java.util.Deque}. Deque (double-ended queue) is a sequence of
 * elements that gives you access to the first and last its elements. You can get their values, add new element to the
 * beginning or to the end of the deque or remove elements from the beginning or from the end.
 * @author Alexey Dergunov
 * @since 0.1.0
 * @see ez.collections.arraydeque._Ez_Int_ArrayDeque
 */
public interface _Ez_Int_Deque extends _Ez_Int_Queue, _Ez_Int_Stack {
    /**
     * Returns the size of the deque, i.e. the number of elements in it.
     * @return the size of the deque
     */
    @Override
    int size();

    /**
     * Checks if the deque contains any elements.
     * @return {@code true} if the deque doesn't contain any elements, {@code false} otherwise
     */
    @Override
    boolean isEmpty();

    /**
     * Checks if the deque contains the specified element.
     * @param element the element to be checked
     * @return {@code true} if the deque contains the specified element, {@code false} otherwise
     */
    @Override
    boolean contains(/*T*/int/*T*/ element);

    /**
     * Returns the iterator which can be used to go through the elements of this deque, from first to last.
     * @return the iterator to go through the elements of this deque
     */
    @Override
    _Ez_Int_Iterator iterator();

    /**
     * Returns the array which contains all elements in this deque, from first to last. This method always allocates
     * new array, so you can modify it and the original deque won't be changed, and vice versa.
     * @return the array which contains all elements in this deque
     */
    @Override
    /*T*/int/*T*/[] toArray();

    /**
     * Adds the element to the end of this deque (the same as {@link #addLast}).
     * @param element the element to be added
     * @return always returns {@code true} (for compatibility with the interface {@link _Ez_Int_Collection})
     */
    @Override
    boolean add(/*T*/int/*T*/ element);

    /**
     * Removes the element from this deque. If this deque contains many instances of this element, the first of them
     * will be removed.
     * @param element the element to be removed
     * @return {@code true} if the element was successfully removed (or, in other words, if the deque was changed),
     * {@code false} otherwise
     */
    @Override
    boolean remove(/*T*/int/*T*/ element);

    /**
     * Removes all elements from this deque.
     */
    @Override
    void clear();

    /**
     * Checks if this deque is equal to other object. For equality the passed object should be of the same class as
     * this deque and contain the same elements in the same order as this deque.
     * @param object the object to be checked for equality
     * @return {@code true} if this deque is equal to the specified object, {@code false} otherwise
     */
    @Override
    boolean equals(Object object);

    /**
     * Returns the hashcode of the deque. If two deques are equal, their hashcodes are also equal. If the hashcodes of
     * two deques are different, these deques are also different. Please note that different deques can have equal
     * hashcodes, though the probability of this fact is low.
     * @return the hashcode of the deque
     */
    @Override
    int hashCode();

    /**
     * Adds the element to the beginning of the deque.
     * @param element the element to be added
     */
    void addFirst(/*T*/int/*T*/ element);

    /**
     * Adds the element to the end of the deque.
     * @param element the element to be added
     */
    void addLast(/*T*/int/*T*/ element);

    /**
     * Returns the first element in the deque.
     * @return the first element in the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    /*T*/int/*T*/ getFirst();

    /**
     * Returns the last element in the deque.
     * @return the last element in the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    /*T*/int/*T*/ getLast();

    /**
     * Removes the first element in the deque.
     * @return the removed element
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    /*T*/int/*T*/ removeFirst();

    /**
     * Removes the last element in the deque.
     * @return the removed element
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    /*T*/int/*T*/ removeLast();
}
