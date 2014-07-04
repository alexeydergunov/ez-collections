package ez.collections.list;

import ez.collections.Ez$Int$Collection;
import ez.collections.Ez$Int$Iterator;

/**
 * The root interface for lists.
 * <p>
 * List in EZ Collections is an analogue of {@link java.util.List}. List is the ordered collection, i.e. you can access
 * its elements by their indices (positions in the list), e.g. set or insert the specified element to the specified
 * position, get the element on the specified position, or remove the element on the specified position.
 * <p>
 * Lists are zero-indexed, i.e. the first element has the index 0, and the last element has the index
 * {@code size() - 1}.
 * @author Alexey Dergunov
 * @since 0.0.1
 */
public interface Ez$Int$List extends Ez$Int$Collection {
    /**
     * Returns the size of the list, i.e. the number of elements in it.
     * @return the size of the list
     */
    @Override
    int size();

    /**
     * Checks if the list contains any elements.
     * @return {@code true} if the list doesn't contain any elements, {@code false} otherwise
     */
    @Override
    boolean isEmpty();

    /**
     * Checks if the list contains the specified element.
     * @param element the element to be checked
     * @return {@code true} if the list contains the specified element, {@code false} otherwise
     */
    @Override
    boolean contains(/*T*/int/*T*/ element);

    /**
     * Returns the iterator which can be used to go through the elements of this list (in the ascending order of
     * indices).
     * @return the iterator to go through the elements of this list
     */
    @Override
    Ez$Int$Iterator iterator();

    /**
     * Returns the array which contains all elements in this list, in the same order as they were contained in the
     * list. This method always allocates new array, so you can modify it and the original list won't be changed, and
     * vice versa.
     * @return the array which contains all elements in this list
     */
    @Override
    /*T*/int/*T*/[] toArray();

    /**
     * Adds the element into this list.
     * @param element the element to be added
     * @return always returns {@code true} (for compatibility with the parent interface {@link Ez$Int$Collection})
     */
    @Override
    boolean add(/*T*/int/*T*/ element);

    /**
     * Removes the element from this list. If this list contains many instances of this element, the first of them
     * will be removed.
     * @param element the element to be removed
     * @return {@code true} if the element was successfully removed (or, in other words, if the list was changed),
     * {@code false} otherwise
     */
    @Override
    boolean remove(/*T*/int/*T*/ element);

    /**
     * Removes all elements from this list.
     */
    @Override
    void clear();

    /**
     * Checks if this list is equal to other object. For equality the passed object should be of the same class as
     * this list and contain the same elements in the same order as this list.
     * @param object the object to be checked for equality
     * @return {@code true} if this list is equal to the specified object, {@code false} otherwise
     */
    @Override
    boolean equals(Object object);

    /**
     * Returns the hashcode of the list. If two lists are equal, their hashcodes are also equal. If the hashcodes of
     * two lists are different, these lists are also different. Please note that different lists can have equal
     * hashcodes, though the probability of this fact is low.
     * @return the hashcode of the list
     */
    @Override
    int hashCode();

    /**
     * Returns the human-readable string representation of the list.
     * @return the string representation of the list
     */
    @Override
    String toString();

    /**
     * Returns the element at the specified position in the list.
     * @param index index of the element
     * @return the element at the specified position in the list
     * @throws IndexOutOfBoundsException if the index is out of range (if {@code index < 0 || index >= size()})
     */
    /*T*/int/*T*/ get(int index);

    /**
     * Sets the specified element to the specified position in the list.
     * @param index index of the element to be set
     * @param element the element to be set
     * @return the element that was at this position before
     * @throws IndexOutOfBoundsException if the index is out of range (if {@code index < 0 || index >= size()})
     */
    /*T*/int/*T*/ set(int index, /*T*/int/*T*/ element);

    /**
     * Inserts the specified element into the list so that its position will be equal to the specified position.
     * @param index index of the newly added element
     * @param element the element to be added
     * @throws IndexOutOfBoundsException if the index is out of range (if {@code index < 0 || index > size()})
     */
    void insert(int index, /*T*/int/*T*/ element);

    /**
     * Removes the element at the specified position from the list.
     * @param index index of the element to be removed
     * @return the removed element
     * @throws IndexOutOfBoundsException if the index is out of range (if {@code index < 0 || index >= size()})
     */
    /*T*/int/*T*/ removeAt(int index);

    /**
     * Returns the index of the first element in the list which is equal to the specified element.
     * @param element the element to find
     * @return the index of the first element which is equal to the specified element, or -1 if such element was not
     * found
     */
    int indexOf(/*T*/int/*T*/ element);

    /**
     * Returns the index of the last element in the list which is equal to the specified element.
     * @param element the element to find
     * @return the index of the last element which is equal to the specified element, or -1 if such element was not
     * found
     */
    int lastIndexOf(/*T*/int/*T*/ element);
}
