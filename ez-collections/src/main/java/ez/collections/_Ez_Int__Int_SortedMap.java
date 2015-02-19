package ez.collections;

import ez.collections.tuples._Ez_Int__Int_Pair;

/**
 * The interface for sorted maps.
 * <p>
 * Sorted map in EZ Collections is an analogue of {@link java.util.NavigableMap}. Besides standard map operations:
 * {@link #containsKey}, {@link #get}, {@link #put}, {@link #remove} it supports key ordering, so it's possible to
 * get first and last keys and entries, and to get lower / higher / floor / ceiling keys and entries for the given key.
 * See the javadocs of the methods to get more information.
 * @author Alexey Dergunov
 * @since 0.1.0
 * @see ez.collections.treemap._Ez_Int__Int_TreeMap
 */
public interface _Ez_Int__Int_SortedMap extends _Ez_Int__Int_Map {
    // TODO null issues, e.g. when there is no higher key - consider returning special 'null value'

    /**
     * Returns the size of the map, i.e. the number of entries (key-value pairs) in it.
     * @return the size of the map
     */
    @Override
    int size();

    /**
     * Checks if the map contains at least one entry (key-value pair).
     * @return {@code true} if the map doesn't contain any entries, {@code false} otherwise
     */
    @Override
    boolean isEmpty();

    /**
     * Checks if the map contains the entry with the specified key.
     * @param key the key to be checked
     * @return {@code true} if the map contains the entry with the specified key, {@code false} otherwise
     */
    @Override
    boolean containsKey(/*KC*/int/*KC*/ key);

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
    @Override
    /*V*/int/*V*/ get(/*KC*/int/*KC*/ key);

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
    @Override
    /*V*/int/*V*/ put(/*KC*/int/*KC*/ key, /*V*/int/*V*/ value);

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
    @Override
    /*V*/int/*V*/ remove(/*KC*/int/*KC*/ key);

    /**
     * Checks if the last call of {@link #get}, {@link #put}, {@link #remove}, {@link #getFirstKey},
     * {@link #getLastKey}, {@link #floorKey}, {@link #ceilingKey}, {@link #lowerKey}, {@link #higherKey},
     * {@link #getFirstEntry}, {@link #getLastEntry}, {@link #floorEntry}, {@link #ceilingEntry}, {@link #lowerEntry}
     * or {@link #higherEntry} has returned 'null'. Some of these methods return primitive types, and as we can't
     * return {@code null} using the primitive types, they can return everything but set the special flag. You should
     * check the using this method. Other methods from the list return object types, so they are able to return
     * {@code null}. They set this flag too. See the javadocs of the listed methods for more detailed information.
     * <p>
     * In other words, this method returns {@code true} if and only if the map from the Java Collections Library
     * ({@link java.util.NavigableMap}) which contains the same entries returns {@code null} after calling the same
     * method.
     * @return {@code true} if the last call of one of the methods listed above has returned incorrect value (for the
     * methods that return primitive types) or has returned {@code null} itself (for the methods that return object
     * types), {@code false} if that call was successful and the returned value is correct and not null
     */
    @Override
    boolean returnedNull();

    /**
     * Removes all entries from the map.
     */
    @Override
    void clear();

    /**
     * Returns the array which contains all keys in this map. The array will be sorted in ascending order.
     * Additionally, two consecutive calls of {@code keys()} and {@link #values()} methods return two arrays related
     * to each other such that for every index {@code i} the entry {@code (keys[i], values[i])} is contained in the
     * map. This method always allocates new array, so you can modify it and the original map won't be changed, and
     * vice versa.
     * @return the array which contains all keys in this map
     */
    @Override
    /*KC*/int/*KC*/[] keys();

    /**
     * Returns the array which contains all values in this map. The array will be sorted in the ascending order of the
     * corresponding keys. That is, two consecutive calls of {@link #keys()} and {@code values()} methods return two
     * arrays related to each other such that for every index {@code i} the entry {@code (keys[i], values[i])} is
     * contained in the map. This method always allocates new array, so you can modify it and the original map won't be
     * changed, and vice versa.
     * @return the array which contains all values in this map
     */
    @Override
    /*V*/int/*V*/[] values();

    /**
     * Returns the iterator which can be used to go through the entries of this map. The iterator will return the
     * entries in ascending order of the keys.
     * @return the iterator to go through the entries of this map
     */
    @Override
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

    /**
     * Returns the smallest key in the map. If the map is empty, it can return everything, but sets the special flag,
     * you should check it immediately after using getFirstKey() this way:
     * <pre>
     * if (map.returnedNull()) {
     *     // The map is empty.
     *     // Take it into account and don't use the key returned by getFirstKey().
     * } else {
     *     // The call of getFirstKey() was successful.
     *     // You can use the key returned by getFirstKey().
     * }
     * </pre>
     * @return the smallest key, in the case the map isn't empty
     * @see #returnedNull()
     */
    /*KC*/int/*KC*/ getFirstKey();

    /**
     * Returns the largest key in the map. If the map is empty, it can return everything, but sets the special flag,
     * you should check it immediately after using getLastKey() this way:
     * <pre>
     * if (map.returnedNull()) {
     *     // The map is empty.
     *     // Take it into account and don't use the key returned by getLastKey().
     * } else {
     *     // The call of getLastKey() was successful.
     *     // You can use the key returned by getLastKey().
     * }
     * </pre>
     * @return the largest key, in the case the map isn't empty
     * @see #returnedNull()
     */
    /*KC*/int/*KC*/ getLastKey();

    /**
     * Returns the entry with the smallest key. The field {@code first} of the returned pair will contain the key, and
     * the field {@code second} will contain the value. If the map is empty, it returns {@code null} and sets the
     * special flag which can be checked by calling {@link #returnedNull()} immediately after using this method.
     * @return the entry with the smallest key, or {@code null} if the map is empty
     * @see #returnedNull()
     */
    _Ez_Int__Int_Pair getFirstEntry();

    /**
     * Returns the entry with the largest key. The field {@code first} of the returned pair will contain the key, and
     * the field {@code second} will contain the value. If the map is empty, it returns {@code null} and sets the
     * special flag which can be checked by calling {@link #returnedNull()} immediately after using this method.
     * @return the entry with the largest key, or {@code null} if the map is empty
     * @see #returnedNull()
     */
    _Ez_Int__Int_Pair getLastEntry();

    /**
     * Returns the greatest key in the map that is less than or equal to the specified one. If the map doesn't contain
     * such a key, this method can return everything, but sets the special flag, you should check it immediately after
     * using floorKey() this way:
     * <pre>
     * if (map.returnedNull()) {
     *     // The map doesn't contain any key that is less than or equal to the specified one.
     *     // Take it into account and don't use the key returned by floorKey().
     * } else {
     *     // The call of floorKey() was successful.
     *     // You can use the key returned by floorKey().
     * }
     * </pre>
     * @param key the key to compare
     * @return the greatest key in the map that is less than or equal to the specified one
     * @see #returnedNull()
     */
    /*KC*/int/*KC*/ floorKey(/*KC*/int/*KC*/ key);

    /**
     * Returns the least key in the map that is greater than or equal to the specified one. If the map doesn't contain
     * such a key, this method can return everything, but sets the special flag, you should check it immediately after
     * using ceilingKey() this way:
     * <pre>
     * if (map.returnedNull()) {
     *     // The map doesn't contain any key that is greater than or equal to the specified one.
     *     // Take it into account and don't use the key returned by ceilingKey().
     * } else {
     *     // The call of ceilingKey() was successful.
     *     // You can use the key returned by ceilingKey().
     * }
     * </pre>
     * @param key the key to compare
     * @return the least key in the map that is greater than or equal to the specified one
     * @see #returnedNull()
     */
    /*KC*/int/*KC*/ ceilingKey(/*KC*/int/*KC*/ key);

    /**
     * Returns the greatest key in the map that is strictly less than the specified one. If the map doesn't contain
     * such a key, this method can return everything, but sets the special flag, you should check it immediately after
     * using lowerKey() this way:
     * <pre>
     * if (map.returnedNull()) {
     *     // The map doesn't contain any key that is strictly less than the specified one.
     *     // Take it into account and don't use the key returned by lowerKey().
     * } else {
     *     // The call of lowerKey() was successful.
     *     // You can use the key returned by lowerKey().
     * }
     * </pre>
     * @param key the key to compare
     * @return the greatest key in the map that is strictly less than the specified one
     * @see #returnedNull()
     */
    /*KC*/int/*KC*/ lowerKey(/*KC*/int/*KC*/ key);

    /**
     * Returns the least key in the map that is strictly greater than the specified one. If the map doesn't contain
     * such a key, this method can return everything, but sets the special flag, you should check it immediately after
     * using higherKey() this way:
     * <pre>
     * if (map.returnedNull()) {
     *     // The map doesn't contain any key that is strictly greater than the specified one.
     *     // Take it into account and don't use the key returned by higherKey().
     * } else {
     *     // The call of higherKey() was successful.
     *     // You can use the key returned by higherKey().
     * }
     * </pre>
     * @param key the key to compare
     * @return the least key in the map that is strictly greater than the specified one
     * @see #returnedNull()
     */
    /*KC*/int/*KC*/ higherKey(/*KC*/int/*KC*/ key);

    /**
     * Returns the entry with the greatest key that is less than or equal to the specified one. The field {@code first}
     * of the returned pair will contain the key, and the field {@code second} will contain the value. If the map
     * doesn't contain such a key, it returns {@code null} and sets the special flag which can be checked by calling
     * {@link #returnedNull()} immediately after using this method.
     * @return the entry with the greatest key that is less than or equal to the specified one, or {@code null} if the
     * map is empty
     * @see #returnedNull()
     */
    _Ez_Int__Int_Pair floorEntry(/*KC*/int/*KC*/ key);

    /**
     * Returns the entry with the least key that is greater than or equal to the specified one. The field {@code first}
     * of the returned pair will contain the key, and the field {@code second} will contain the value. If the map
     * doesn't contain such a key, it returns {@code null} and sets the special flag which can be checked by calling
     * {@link #returnedNull()} immediately after using this method.
     * @return the entry with the least key that is greater than or equal to the specified one, or {@code null} if the
     * map is empty
     * @see #returnedNull()
     */
    _Ez_Int__Int_Pair ceilingEntry(/*KC*/int/*KC*/ key);

    /**
     * Returns the entry with the greatest key that is strictly less than the specified one. The field {@code first}
     * of the returned pair will contain the key, and the field {@code second} will contain the value. If the map
     * doesn't contain such a key, it returns {@code null} and sets the special flag which can be checked by calling
     * {@link #returnedNull()} immediately after using this method.
     * @return the entry with the greatest key that is strictly less than the specified one, or {@code null} if the
     * map is empty
     * @see #returnedNull()
     */
    _Ez_Int__Int_Pair lowerEntry(/*KC*/int/*KC*/ key);

    /**
     * Returns the entry with the least key that is strictly greater than the specified one. The field {@code first}
     * of the returned pair will contain the key, and the field {@code second} will contain the value. If the map
     * doesn't contain such a key, it returns {@code null} and sets the special flag which can be checked by calling
     * {@link #returnedNull()} immediately after using this method.
     * @return the entry with the least key that is strictly greater than the specified one, or {@code null} if the
     * map is empty
     * @see #returnedNull()
     */
    _Ez_Int__Int_Pair higherEntry(/*KC*/int/*KC*/ key);
}
