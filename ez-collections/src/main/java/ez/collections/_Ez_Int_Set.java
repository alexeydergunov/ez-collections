package ez.collections;

/**
 * The root interface for sets.
 * <p>
 * Set in EZ Collections is an analogue of {@link java.util.Set}. Sets cannot contain more than one instance of the
 * same element. Sets provide you only basic operations with collections: {@link #add}, {@link #remove},
 * {@link #contains}. Child interfaces can give you more functionality.
 * @author Alexey Dergunov
 * @since 0.0.1
 * @see ez.collections.hashset._Ez_Int_HashSet
 * @see ez.collections.treeset._Ez_Int_TreeSet
 */
public interface _Ez_Int_Set extends _Ez_Int_Collection {
    /**
     * Returns the size of the set, i.e. the number of elements in it.
     * @return the size of the set
     */
    @Override
    int size();

    /**
     * Checks if the set contains any elements.
     * @return {@code true} if the set doesn't contain any elements, {@code false} otherwise
     */
    @Override
    boolean isEmpty();

    /**
     * Checks if the set contains the specified element.
     * @param element the element to be checked
     * @return {@code true} if the set contains the specified element, {@code false} otherwise
     */
    @Override
    boolean contains(/*T*/int/*T*/ element);

    /**
     * Returns the iterator which can be used to go through the elements of this set. It is not guaranteed that the
     * iterator will return the elements in any particular order.
     * @return the iterator to go through the elements of this set
     */
    @Override
    _Ez_Int_Iterator iterator();

    /**
     * Returns the array which contains all elements in this set. It is not guaranteed that the array will be ordered
     * in any particular order (you still can sort it by yourself). This method always allocates new array, so you can
     * modify it and the original set won't be changed, and vice versa.
     * @return the array which contains all elements in this set
     */
    @Override
    /*T*/int/*T*/[] toArray();

    /**
     * Adds the element into the set.
     * @param element the element to be added
     * @return {@code true} if the element was successfully added (or, in other words, if the set was changed),
     * {@code false} otherwise (if the set has already contained this element before)
     */
    @Override
    boolean add(/*T*/int/*T*/ element);

    /**
     * Removes the element from this set.
     * @param element the element to be removed
     * @return {@code true} if the element was successfully removed (or, in other words, if the set was changed),
     * {@code false} otherwise (if the set hasn't already contained this element before)
     */
    @Override
    boolean remove(/*T*/int/*T*/ element);

    /**
     * Removes all elements from this set.
     */
    @Override
    void clear();

    /**
     * Checks if this set is equal to other object. For equality the passed object should be of the same class as this
     * set and contain the same elements as this set.
     * @param object the object to be checked for equality
     * @return {@code true} if this set is equal to the specified object, {@code false} otherwise
     */
    @Override
    boolean equals(Object object);

    /**
     * Returns the hashcode of the set. If two sets are equal, their hashcodes are also equal. If the hashcodes of two
     * sets are different, these sets are also different. Please note that different sets can have equal hashcodes,
     * though the probability of this fact is low.
     * @return the hashcode of the set
     */
    @Override
    int hashCode();

    /**
     * Returns the human-readable string representation of the set.
     * @return the string representation of the set
     */
    @Override
    String toString();
}
