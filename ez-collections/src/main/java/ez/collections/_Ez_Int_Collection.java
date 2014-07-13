package ez.collections;

/**
 * The root interface in the collection hierarchy of EZ Collections.
 * <p>
 * EZ Collections are very similar to usual Java collections (see {@link java.util.Collection}). There are some
 * differences between them in the functionality, but they are not important. More important is that EZ Collections
 * have one huge advantage: they store elements not as objects, but as primitives. So in this library you can find an
 * implementation of every collection for every primitive type. It greatly increases the performance.
 * @author Alexey Dergunov
 * @since 0.0.1
 * @see _Ez_Int_List
 */
public interface _Ez_Int_Collection {
    /**
     * Returns the size of the collection, i.e. the number of elements in it.
     * @return the size of the collection
     */
    int size();

    /**
     * Checks if the collection contains any elements.
     * @return {@code true} if the collection doesn't contain any elements, {@code false} otherwise
     */
    boolean isEmpty();

    /**
     * Checks if the collection contains the specified element.
     * @param element the element to be checked
     * @return {@code true} if the collection contains the specified element, {@code false} otherwise
     */
    boolean contains(/*T*/int/*T*/ element);

    /**
     * Returns the iterator which can be used to go through the elements of this collection.
     * @return the iterator to go through the elements of this collection
     */
    _Ez_Int_Iterator iterator();

    /**
     * Returns the array which contains all elements in this collection. This method always allocates new array, so you
     * can modify it and the original collection won't be changed, and vice versa.
     * @return the array which contains all elements in this collection
     */
    /*T*/int/*T*/[] toArray();

    /**
     * Adds the element into this collection.
     * @param element the element to be added
     * @return {@code true} if the element was successfully added (or, in other words, if the collection was changed),
     * {@code false} otherwise
     */
    boolean add(/*T*/int/*T*/ element);

    /**
     * Removes the element from this collection. If this collection contains many instances of this element, only one
     * of them will be removed.
     * @param element the element to be removed
     * @return {@code true} if the element was successfully removed (or, in other words, if the collection was
     * changed), {@code false} otherwise
     */
    boolean remove(/*T*/int/*T*/ element);

    /**
     * Removes all elements from this collection.
     */
    void clear();

    /**
     * Checks if this collection is equal to other object. Different collections have different implementations of this
     * method, so please see their own javadoc. In general, for equality the passed object should be of the same class
     * as this collection and contain the same elements as this collection. If the ordering of the elements matters,
     * it should also be equal.
     * @param object the object to be checked for equality
     * @return {@code true} if this collection is equal to the specified object, {@code false} otherwise
     */
    @Override
    boolean equals(Object object);

    /**
     * Returns the hashcode of the collection. If two collections are equal, their hashcodes are also equal. If the
     * hashcodes of two collections are different, these collections are also different. Please note that different
     * collections can have equal hashcodes, though the probability of this fact is low.
     * @return the hashcode of the collection
     */
    @Override
    int hashCode();

    /**
     * Returns the human-readable string representation of the collection.
     * @return the string representation of the collection
     */
    @Override
    String toString();
}
