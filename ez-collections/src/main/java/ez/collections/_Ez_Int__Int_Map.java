package ez.collections;

/**
 * The root interface for maps.
 * <p>
 * Map in EZ Collections is an analogue of {@link java.util.Map}. Map (also known as dictionary) is a data structure
 * that stores keys and values: all keys are unique, and each key has its own value. It's possible to do all standard
 * map operations: check if the key is contained in the map, get the value by the key, put / override the value for
 * the key, or remove the key and its value from the map. Child interfaces can provide you more functionality.
 * @author Alexey Dergunov
 * @since 0.1.0
 * @see ez.collections.hashmap._Ez_Int__Int_HashMap
 * @see ez.collections.treemap._Ez_Int__Int_TreeMap
 */
public interface _Ez_Int__Int_Map {
    // TODO null issues, e.g. when there is no value for a certain key - consider returning special 'null value'

    /**
     * Returns the size of the map, i.e. the number of entries (key-value pairs) in it.
     * @return the size of the map
     */
    int size();

    /**
     * Checks if the map contains at least one entry (key-value pair).
     * @return {@code true} if the map doesn't contain any entries, {@code false} otherwise
     */
    boolean isEmpty();

    /**
     * Checks if the map contains the entry with the specified key.
     * @param key the key to be checked
     * @return {@code true} if the map contains the entry with the specified key, {@code false} otherwise
     */
    boolean containsKey(/*T1*/int/*T1*/ key);

    /**
     * Returns the value for the specified key. If the map doesn't contain the entry with the specified key, this
     * method can return everything, but sets the special flag, you should check it immediately after using get() this
     * way:
     * <pre>
     * if (map.returnedNull()) {
     *     // The map doesn't contain the entry with the specified key.
     *     // Take it into account and don't use the value returned by get().
     * } else {
     *     // The call of get() was successful.
     *     // You can use the value returned by get().
     * }
     * </pre>
     * @param key the key of the entry
     * @return the value for the specified key
     * @see #returnedNull()
     */
    /*T2*/int/*T2*/ get(/*T1*/int/*T1*/ key);

    /**
     * Puts the specified key-value pair into the map. That is, if the map already contains the entry for the specified
     * key, the previous value is overridden by the new one, and if it doesn't, the new entry is created. In the first
     * case, the previous value is returned, and in the second case it can return everything, but the special flag is
     * set. If you are going to use the returned value, you should check it immediately after using put() this way:
     * <pre>
     * if (map.returnedNull()) {
     *     // The map didn't contain the entry with the specified key. New entry was created.
     *     // Take it into account and don't use the value returned by put().
     * } else {
     *     // The map contained the entry with the specified key. The value was overridden.
     *     // You can use the previous value returned by put().
     * }
     * </pre>
     * @param key the key of the entry
     * @param value the value of the entry
     * @return the previous value for the specified key
     * @see #returnedNull()
     */
    /*T2*/int/*T2*/ put(/*T1*/int/*T1*/ key, /*T2*/int/*T2*/ value);

    /**
     * Removes the entry with the specified key from the map. This method returns the value of the removed entry,
     * however, if the map doesn't contain the entry with the specified key, it can return everything, but sets the
     * special flag, you should check it immediately after using remove() this way:
     * <pre>
     * if (map.returnedNull()) {
     *     // The map doesn't contain the entry with the specified key. Nothing has been removed.
     *     // Take it into account and don't use the value returned by remove().
     * } else {
     *     // The call of remove() was successful.
     *     // You can use the value returned by remove().
     * }
     * </pre>
     * @param key the key of the entry to be removed
     * @return the value of the removed entry
     * @see #returnedNull()
     */
    /*T2*/int/*T2*/ remove(/*T1*/int/*T1*/ key);

    /**
     * Checks if the last call of {@link #get}, {@link #put} or {@link #remove} has returned 'null'. Since we can't
     * return {@code null} using the primitive types, the methods listed above can return everything but set the
     * special flag. You should check this flag using this method.
     * <p>
     * In other words, this method returns {@code true} if and only if the map from the Java Collections Library
     * ({@link java.util.Map}) which contains the same entries returns {@code null} after calling the same method.
     * @return {@code true} if the last call of one of the methods listed above has returned incorrect value ('null'),
     * {@code false} if that call was successful and the returned value is correct
     */
    boolean returnedNull();

    /**
     * Removes all entries from the map.
     */
    void clear();

    /**
     * Returns the array which contains all keys in this map. It is not guaranteed that the array will be ordered in
     * any particular order. However, two consecutive calls of {@code keys()} and {@link #values()} methods return two
     * arrays related to each other such that for every index {@code i} the entry {@code (keys[i], values[i])} is
     * contained in the map. This method always allocates new array, so you can modify it and the original map won't
     * be changed, and vice versa.
     * @return the array which contains all keys in this map
     */
    /*T1*/int/*T1*/[] keys();

    /**
     * Returns the array which contains all values in this map. It is not guaranteed that the array will be ordered in
     * any particular order. However, two consecutive calls of {@link #keys()} and {@code values()} methods return two
     * arrays related to each other such that for every index {@code i} the entry {@code (keys[i], values[i])} is
     * contained in the map. This method always allocates new array, so you can modify it and the original map won't
     * be changed, and vice versa.
     * @return the array which contains all values in this map
     */
    /*T2*/int/*T2*/[] values();

    /**
     * Returns the iterator which can be used to go through the entries of this map. It is not guaranteed that the
     * iterator will return the entries in any particular order.
     * @return the iterator to go through the entries of this map
     */
    _Ez_Int__Int_MapIterator iterator();

    /**
     * Checks if this map is equal to other object. For equality the passed object should be of the same class as this
     * map and contain the same key-value pairs as this map.
     * @param object the object to be checked for equality
     * @return {@code true} if this map is equal to the specified object, {@code false} otherwise
     */
    @Override
    boolean equals(Object object);

    /**
     * Returns the hashcode of the map. If two maps are equal, their hashcodes are also equal. If the hashcodes of two
     * maps are different, these maps are also different. Please note that different maps can have equal hashcodes,
     * though the probability of this fact is low.
     * @return the hashcode of the map
     */
    @Override
    int hashCode();

    /**
     * Returns the human-readable string representation of the map.
     * @return the string representation of the map
     */
    @Override
    String toString();
}
