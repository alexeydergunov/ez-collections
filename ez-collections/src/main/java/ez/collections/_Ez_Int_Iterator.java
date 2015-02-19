package ez.collections;

/**
 * The root interface for iterators.
 * <p>
 * Iterators are used to go through a collection and get its elements. This interface provides the same methods as
 * {@link java.util.Iterator} except {@code remove()} method.
 * @author Alexey Dergunov
 * @since 0.1.0
 */
public interface _Ez_Int_Iterator {
    /**
     * Checks if the iterator has more available elements.
     * @return {@code true} if the iterator has more elements, {@code false} otherwise
     */
    boolean hasNext();

    /**
     * Returns the next element in the collection.
     * @return the next element in the collection
     * @throws java.util.NoSuchElementException if the iterator doesn't have more elements
     */
    /*T*/int/*T*/ next();
}
