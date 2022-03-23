package ez.collections;

/**
 * The interface for sorted sets.
 * <p>
 * Sorted set in EZ Collections is an analogue of {@link java.util.NavigableSet}. As all sets, sorted sets cannot
 * contain more than one instance of the same element. In addition to the basic operations: {@link #add},
 * {@link #remove}, {@link #contains}, sorted sets allow you to get/remove the first or the last element, as well as
 * get lower/floor/ceiling/higher elements relative to the given element. See javadocs of the methods for more details.
 * @author Alexey Dergunov
 * @since 0.1.0
 * @see ez.collections.treeset._Ez_Int_TreeSet
 * @see ez.collections.treeset._Ez_Int_CustomTreeSet
 */
public interface _Ez_Int_SortedSet extends _Ez_Int_Set {
    // TODO null issues, e.g. when there is no higher element - consider returning special 'null value'

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
    boolean contains(/*C*/int/*C*/ element);

    /**
     * Returns the iterator which can be used to go through the elements of this set. The iterator will return the
     * elements in ascending order.
     * @return the iterator to go through the elements of this set
     */
    @Override
    _Ez_Int_Iterator iterator();

    /**
     * Returns the array which contains all elements in this set. The array will be sorted in ascending order. This
     * method always allocates new array, so you can modify it and the original set won't be changed, and vice versa.
     * @return the array which contains all elements in this set
     */
    @Override
    /*C*/int/*C*/[] toArray();

    /**
     * Adds the element into the set.
     * @param element the element to be added
     * @return {@code true} if the element was successfully added (or, in other words, if the set was changed),
     * {@code false} otherwise (if the set has already contained this element before)
     */
    @Override
    boolean add(/*C*/int/*C*/ element);

    /**
     * Removes the element from this set.
     * @param element the element to be removed
     * @return {@code true} if the element was successfully removed (or, in other words, if the set was changed),
     * {@code false} otherwise (if the set hasn't already contained this element before)
     */
    @Override
    boolean remove(/*C*/int/*C*/ element);

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
     * Returns the smallest element in the set. If the set is empty, it can return everything, but sets the special
     * flag, you should check it immediately after using getFirst() this way:
     * <pre>
     * if (set.returnedNull()) {
     *     // The set is empty.
     *     // Take it into account and don't use the element returned by getFirst().
     * } else {
     *     // The call of getFirst() was successful.
     *     // You can use the element returned by getFirst().
     * }
     * </pre>
     * @return the smallest element, in the case the set isn't empty
     * @see #returnedNull()
     */
    /*C*/int/*C*/ getFirst();

    /**
     * Returns the largest element in the set. If the set is empty, it can return everything, but sets the special
     * flag, you should check it immediately after using getLast() this way:
     * <pre>
     * if (set.returnedNull()) {
     *     // The set is empty.
     *     // Take it into account and don't use the element returned by getLast().
     * } else {
     *     // The call of getLast() was successful.
     *     // You can use the element returned by getLast().
     * }
     * </pre>
     * @return the largest element, in the case the set isn't empty
     * @see #returnedNull()
     */
    /*C*/int/*C*/ getLast();

    /**
     * Removes the smallest element in the set. If the set is empty, it doesn't do anything and sets the special flag.
     * If you are going to use the value returned by removeFirst(), you should check it immediately this way:
     * <pre>
     * if (set.returnedNull()) {
     *     // The set has already been empty. Nothing has been removed.
     *     // Take it into account and don't use the element returned by removeFirst().
     * } else {
     *     // The call of removeFirst() was successful.
     *     // You can use the element returned by removeFirst().
     * }
     * </pre>
     * @return the removed element, in the case the set isn't empty
     * @see #returnedNull()
     */
    /*C*/int/*C*/ removeFirst();

    /**
     * Removes the largest element in the set. If the set is empty, it doesn't do anything and sets the special flag.
     * If you are going to use the value returned by removeLast(), you should check it immediately this way:
     * <pre>
     * if (set.returnedNull()) {
     *     // The set has already been empty. Nothing has been removed.
     *     // Take it into account and don't use the element returned by removeLast().
     * } else {
     *     // The call of removeLast() was successful.
     *     // You can use the element returned by removeLast().
     * }
     * </pre>
     * @return the removed element, in the case the set isn't empty
     * @see #returnedNull()
     */
    /*C*/int/*C*/ removeLast();

    /**
     * Returns the greatest element in the set that is less than or equal to the specified one. If the set doesn't
     * contain such an element, this method can return everything, but sets the special flag, you should check it
     * immediately after using floor() this way:
     * <pre>
     * if (set.returnedNull()) {
     *     // The set doesn't contain any element that is less than or equal to the specified element.
     *     // Take it into account and don't use the element returned by floor().
     * } else {
     *     // The call of floor() was successful.
     *     // You can use the element returned by floor().
     * }
     * </pre>
     * @param element the element to compare
     * @return the greatest element in the set that is less than or equal to the specified element
     * @see #returnedNull()
     */
    /*C*/int/*C*/ floor(/*C*/int/*C*/ element);

    /**
     * Returns the least element in the set that is greater than or equal to the specified one. If the set doesn't
     * contain such an element, this method can return everything, but sets the special flag, you should check it
     * immediately after using ceiling() this way:
     * <pre>
     * if (set.returnedNull()) {
     *     // The set doesn't contain any element that is greater than or equal to the specified element.
     *     // Take it into account and don't use the element returned by ceiling().
     * } else {
     *     // The call of ceiling() was successful.
     *     // You can use the element returned by ceiling().
     * }
     * </pre>
     * @param element the element to compare
     * @return the least element in the set that is greater than or equal to the specified element
     * @see #returnedNull()
     */
    /*C*/int/*C*/ ceiling(/*C*/int/*C*/ element);

    /**
     * Returns the greatest element in the set that is strictly less than the specified one. If the set doesn't contain
     * such an element, this method can return everything, but sets the special flag, you should check it immediately
     * after using lower() this way:
     * <pre>
     * if (set.returnedNull()) {
     *     // The set doesn't contain any element that is strictly less than the specified element.
     *     // Take it into account and don't use the element returned by lower().
     * } else {
     *     // The call of lower() was successful.
     *     // You can use the element returned by lower().
     * }
     * </pre>
     * @param element the element to compare
     * @return the greatest element in the set that is strictly less than the specified element
     * @see #returnedNull()
     */
    /*C*/int/*C*/ lower(/*C*/int/*C*/ element);

    /**
     * Returns the least element in the set that is strictly greater than the specified one. If the set doesn't contain
     * such an element, this method can return everything, but sets the special flag, you should check it immediately
     * after using higher() this way:
     * <pre>
     * if (set.returnedNull()) {
     *     // The set doesn't contain any element that is strictly greater than the specified element.
     *     // Take it into account and don't use the element returned by higher().
     * } else {
     *     // The call of higher() was successful.
     *     // You can use the element returned by higher().
     * }
     * </pre>
     * @param element the element to compare
     * @return the least element in the set that is strictly greater than the specified element
     * @see #returnedNull()
     */
    /*C*/int/*C*/ higher(/*C*/int/*C*/ element);

    public void removeNode(int z);

    /**
     * Checks if the last call of {@link #getFirst}, {@link #getLast}, {@link #removeFirst}, {@link #removeLast},
     * {@link #floor}, {@link #ceiling}, {@link #lower} or {@link #higher} has returned 'null'. Since we can't return
     * {@code null} using the primitive types, the methods listed above can return everything but set the special flag.
     * You should check this flag using this method.
     * <p>
     * In other words, this method returns {@code true} if and only if the set from the Java Collections Library
     * ({@link java.util.NavigableSet}) which contains the same elements returns {@code null} after calling the same
     * method.
     * @return {@code true} if the last call of one of the methods listed above has returned incorrect value ('null'),
     * {@code false} if that call was successful and the returned value is correct
     */
    boolean returnedNull();
}
