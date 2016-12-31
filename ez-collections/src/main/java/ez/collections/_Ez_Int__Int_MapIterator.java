package ez.collections;

/**
 * The root interface for map iterators.
 * <p>
 * Map iterators are used to go through a map and get its keys and values. The analogues in Java Collections are sets
 * {@link java.util.Map#keySet()} and {@link java.util.Map#entrySet()} and collection {@link java.util.Map#values()}
 * which can give you iterators. The only difference is that iterators in EZ Collections are read-only and cannot
 * remove entries.
 * @author Alexey Dergunov
 * @since 0.1.0
 */
public interface _Ez_Int__Int_MapIterator {
    /**
     * Checks if the iterator has more available entries.
     * @return {@code true} if the iterator has more entries, {@code false} otherwise
     */
    boolean hasNext();

    /**
     * Returns the key of the entry at which the iterator currently points.
     * @return the key of the entry at which the iterator currently points
     * @throws java.util.NoSuchElementException if the iterator doesn't have more entries
     */
    /*T1*/int/*T1*/ getKey();

    /**
     * Returns the value of the entry at which the iterator currently points.
     * @return the value of the entry at which the iterator currently points
     * @throws java.util.NoSuchElementException if the iterator doesn't have more entries
     */
    /*T2*/int/*T2*/ getValue();

    /**
     * Moves the iterator to the next entry in the map. If there are no remaining entries, does nothing.
     */
    void next();
}
