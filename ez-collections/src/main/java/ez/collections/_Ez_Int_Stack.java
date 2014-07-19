package ez.collections;

/**
 * The interface for stacks.
 * <p>
 * Stack in EZ Collections is a sequence of elements that allows you to add elements to the end and get/remove elements
 * from the end. Java Collections doesn't have the similar interface, though you can still use {@link java.util.Deque},
 * which provides the full functionality of stack.
 * @author Alexey Dergunov
 * @since 0.0.1
 * @see ez.collections.arraylist._Ez_Int_ArrayList
 * @see ez.collections.arraydeque._Ez_Int_ArrayDeque
 */
public interface _Ez_Int_Stack extends _Ez_Int_Collection {
    /**
     * Returns the size of the stack, i.e. the number of elements in it.
     * @return the size of the stack
     */
    @Override
    int size();

    /**
     * Checks if the stack contains any elements.
     * @return {@code true} if the stack doesn't contain any elements, {@code false} otherwise
     */
    @Override
    boolean isEmpty();

    /**
     * Checks if the stack contains the specified element.
     * @param element the element to be checked
     * @return {@code true} if the stack contains the specified element, {@code false} otherwise
     */
    @Override
    boolean contains(/*T*/int/*T*/ element);

    /**
     * Returns the iterator which can be used to go through the elements of this stack, from first to last.
     * @return the iterator to go through the elements of this stack
     */
    @Override
    _Ez_Int_Iterator iterator();

    /**
     * Returns the array which contains all elements in this stack, from first to last. This method always allocates
     * new array, so you can modify it and the original stack won't be changed, and vice versa.
     * @return the array which contains all elements in this stack
     */
    @Override
    /*T*/int/*T*/[] toArray();

    /**
     * Adds the element to the end of this stack (the same as {@link #addLast}).
     * @param element the element to be added
     * @return always returns {@code true} (for compatibility with the interface {@link _Ez_Int_Collection})
     */
    @Override
    boolean add(/*T*/int/*T*/ element);

    /**
     * Removes the element from this stack. If this stack contains many instances of this element, the first of them
     * will be removed.
     * @param element the element to be removed
     * @return {@code true} if the element was successfully removed (or, in other words, if the stack was changed),
     * {@code false} otherwise
     */
    @Override
    boolean remove(/*T*/int/*T*/ element);

    /**
     * Removes all elements from this stack.
     */
    @Override
    void clear();

    /**
     * Checks if this stack is equal to other object. For equality the passed object should be of the same class as
     * this stack and contain the same elements in the same order as this stack.
     * @param object the object to be checked for equality
     * @return {@code true} if this stack is equal to the specified object, {@code false} otherwise
     */
    @Override
    boolean equals(Object object);

    /**
     * Returns the hashcode of the stack. If two stacks are equal, their hashcodes are also equal. If the hashcodes of
     * two stacks are different, these stacks are also different. Please note that different stacks can have equal
     * hashcodes, though the probability of this fact is low.
     * @return the hashcode of the stack
     */
    @Override
    int hashCode();

    /**
     * Returns the human-readable string representation of the stack.
     * @return the string representation of the stack
     */
    @Override
    String toString();

    /**
     * Adds the element to the end of the stack.
     * @param element the element to be added
     */
    void addLast(/*T*/int/*T*/ element);

    /**
     * Returns the last element in the stack.
     * @return the last element in the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    /*T*/int/*T*/ getLast();

    /**
     * Removes the last element in the stack.
     * @return the removed element
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    /*T*/int/*T*/ removeLast();
}
