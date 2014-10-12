package ez.collections.treemap;

import ez.collections._Ez_Int__Int_Map;
import ez.collections._Ez_Int__Int_MapIterator;
import ez.collections._Ez_Int__Int_SortedMap;
import ez.collections.misc.PrimitiveHashCalculator;
import ez.collections.tuples._Ez_Int__Int_Pair;

import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;

public class _Ez_Int__Int_TreeMap implements _Ez_Int__Int_SortedMap {
    private static final int DEFAULT_CAPACITY = 10;
    private static final double ENLARGE_SCALE = 2.0;
    private static final int HASHCODE_INITIAL_VALUE = 0x811c9dc5;
    private static final int HASHCODE_MULTIPLIER = 0x01000193;

    private static final boolean BLACK = false;
    private static final boolean RED = true;
    private static final int NULL = 0;
    private static final /*KC*/int/*KC*/ DEFAULT_NULL_KEY = (new /*KC*/int/*KC*/[1])[0];
    private static final /*V*/int/*V*/ DEFAULT_NULL_VALUE = (new /*V*/int/*V*/[1])[0];

    // Arrays are 1-indexed. Index 0 is a null node.
    private /*KC*/int/*KC*/[] keys;
    private /*V*/int/*V*/[] values;
    private int[] left;
    private int[] right;
    private int[] p;
    private boolean[] color;

    private int size;
    private int root;
    private boolean returnedNull;

    public _Ez_Int__Int_TreeMap() {
        this(DEFAULT_CAPACITY);
    }

    public _Ez_Int__Int_TreeMap(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must be non-negative");
        }
        capacity++;
        keys = new /*KC*/int/*KC*/[capacity];
        values = new /*V*/int/*V*/[capacity];
        left = new int[capacity];
        right = new int[capacity];
        p = new int[capacity];
        color = new boolean[capacity];
        color[NULL] = BLACK;
        size = 0;
        root = NULL;
        returnedNull = false;
    }

    public _Ez_Int__Int_TreeMap(_Ez_Int__Int_Map map) {
        this(map.size());
        for (_Ez_Int__Int_MapIterator it = map.iterator(); it.hasNext(); it.next()) {
            put(it.getKey(), it.getValue());
        }
    }

    public _Ez_Int__Int_TreeMap(Map</*KWC*/Integer/*KWC*/, /*VW*/Integer/*VW*/> javaMap) {
        this(javaMap.size());
        for (Map.Entry</*KWC*/Integer/*KWC*/, /*VW*/Integer/*VW*/> e : javaMap.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(/*KC*/int/*KC*/ key) {
        int x = root;
        while (x != NULL) {
            if (key < keys[x]) {
                x = left[x];
            } else if (key > keys[x]) {
                x = right[x];
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public /*V*/int/*V*/ get(/*KC*/int/*KC*/ key) {
        int x = root;
        while (x != NULL) {
            if (key < keys[x]) {
                x = left[x];
            } else if (key > keys[x]) {
                x = right[x];
            } else {
                returnedNull = false;
                return values[x];
            }
        }
        returnedNull = true;
        return DEFAULT_NULL_VALUE;
    }

    @Override
    public /*V*/int/*V*/ put(/*KC*/int/*KC*/ key, /*V*/int/*V*/ value) {
        int y = NULL;
        int x = root;
        while (x != NULL) {
            //noinspection SuspiciousNameCombination
            y = x;
            if (key < keys[x]) {
                x = left[x];
            } else if (key > keys[x]) {
                x = right[x];
            } else {
                final /*V*/int/*V*/ oldValue = values[x];
                values[x] = value;
                returnedNull = false;
                return oldValue;
            }
        }
        if (size == color.length - 1) {
            enlarge();
        }
        int z = ++size;
        keys[z] = key;
        values[z] = value;
        p[z] = y;
        if (y == NULL) {
            root = z;
        } else {
            if (key < keys[y]) {
                left[y] = z;
            } else {
                right[y] = z;
            }
        }
        left[z] = NULL;
        right[z] = NULL;
        color[z] = RED;
        fixAfterAdd(z);
        returnedNull = true;
        return DEFAULT_NULL_VALUE;
    }

    @Override
    public /*V*/int/*V*/ remove(/*KC*/int/*KC*/ key) {
        int z = root;
        while (z != NULL) {
            if (key < keys[z]) {
                z = left[z];
            } else if (key > keys[z]) {
                z = right[z];
            } else {
                final /*V*/int/*V*/ removedValue = values[z];
                removeNode(z);
                returnedNull = false;
                return removedValue;
            }
        }
        returnedNull = true;
        return DEFAULT_NULL_VALUE;
    }

    private void removeNode(int z) {
        int y = (left[z] == NULL || right[z] == NULL) ? z : successorNode(z);
        int x = (left[y] != NULL) ? left[y] : right[y];
        p[x] = p[y];
        if (p[y] == NULL) {
            root = x;
        } else {
            if (y == left[p[y]]) {
                left[p[y]] = x;
            } else {
                right[p[y]] = x;
            }
        }
        if (y != z) {
            keys[z] = keys[y];
            values[z] = values[y];
        }
        //noinspection PointlessBooleanExpression
        if (color[y] == BLACK) {
            fixAfterRemove(x);
        }
        // Swap with last
        if (y != size) {
            // copy fields
            keys[y] = keys[size];
            values[y] = values[size];
            left[y] = left[size];
            right[y] = right[size];
            p[y] = p[size];
            color[y] = color[size];
            // fix the children's parents
            p[left[size]] = y;
            p[right[size]] = y;
            // fix one of the parent's children
            if (left[p[size]] == size) {
                left[p[size]] = y;
            } else {
                right[p[size]] = y;
            }
            // fix root
            if (root == size) {
                root = y;
            }
        }
        size--;
    }

    private int successorNode(int x) {
        if (right[x] != NULL) {
            x = right[x];
            while (left[x] != NULL) {
                x = left[x];
            }
            return x;
        } else {
            int y = p[x];
            while (y != NULL && x == right[y]) {
                //noinspection SuspiciousNameCombination
                x = y;
                y = p[y];
            }
            return y;
        }
    }

    @SuppressWarnings("PointlessBooleanExpression")
    private void fixAfterAdd(int z) {
        while (color[p[z]] == RED) {
            if (p[z] == left[p[p[z]]]) {
                int y = right[p[p[z]]];
                if (color[y] == RED) {
                    color[p[z]] = BLACK;
                    color[y] = BLACK;
                    color[p[p[z]]] = RED;
                    z = p[p[z]];
                } else {
                    if (z == right[p[z]]) {
                        z = p[z];
                        rotateLeft(z);
                    }
                    color[p[z]] = BLACK;
                    color[p[p[z]]] = RED;
                    rotateRight(p[p[z]]);
                }
            } else {
                int y = left[p[p[z]]];
                if (color[y] == RED) {
                    color[p[z]] = BLACK;
                    color[y] = BLACK;
                    color[p[p[z]]] = RED;
                    z = p[p[z]];
                } else {
                    if (z == left[p[z]]) {
                        z = p[z];
                        rotateRight(z);
                    }
                    color[p[z]] = BLACK;
                    color[p[p[z]]] = RED;
                    rotateLeft(p[p[z]]);
                }
            }
        }
        color[root] = BLACK;
    }

    @SuppressWarnings("PointlessBooleanExpression")
    private void fixAfterRemove(int x) {
        while (x != root && color[x] == BLACK) {
            if (x == left[p[x]]) {
                int w = right[p[x]];
                if (color[w] == RED) {
                    color[w] = BLACK;
                    color[p[x]] = RED;
                    rotateLeft(p[x]);
                    w = right[p[x]];
                }
                if (color[left[w]] == BLACK && color[right[w]] == BLACK) {
                    color[w] = RED;
                    x = p[x];
                } else {
                    if (color[right[w]] == BLACK) {
                        color[left[w]] = BLACK;
                        color[w] = RED;
                        rotateRight(w);
                        w = right[p[x]];
                    }
                    color[w] = color[p[x]];
                    color[p[x]] = BLACK;
                    color[right[w]] = BLACK;
                    rotateLeft(p[x]);
                    x = root;
                }
            } else {
                int w = left[p[x]];
                if (color[w] == RED) {
                    color[w] = BLACK;
                    color[p[x]] = RED;
                    rotateRight(p[x]);
                    w = left[p[x]];
                }
                if (color[left[w]] == BLACK && color[right[w]] == BLACK) {
                    color[w] = RED;
                    x = p[x];
                } else {
                    if (color[left[w]] == BLACK) {
                        color[right[w]] = BLACK;
                        color[w] = RED;
                        rotateLeft(w);
                        w = left[p[x]];
                    }
                    color[w] = color[p[x]];
                    color[p[x]] = BLACK;
                    color[left[w]] = BLACK;
                    rotateRight(p[x]);
                    x = root;
                }
            }
        }
        color[x] = BLACK;
    }

    private void rotateLeft(int x) {
        int y = right[x];
        right[x] = left[y];
        if (left[y] != NULL) {
            p[left[y]] = x;
        }
        p[y] = p[x];
        if (p[x] == NULL) {
            root = y;
        } else {
            if (x == left[p[x]]) {
                left[p[x]] = y;
            } else {
                right[p[x]] = y;
            }
        }
        left[y] = x;
        p[x] = y;
    }

    private void rotateRight(int x) {
        int y = left[x];
        left[x] = right[y];
        if (right[y] != NULL) {
            p[right[y]] = x;
        }
        p[y] = p[x];
        if (p[x] == NULL) {
            root = y;
        } else {
            if (x == right[p[x]]) {
                right[p[x]] = y;
            } else {
                left[p[x]] = y;
            }
        }
        right[y] = x;
        p[x] = y;
    }

    @Override
    public boolean returnedNull() {
        return returnedNull;
    }

    @Override
    public void clear() {
        color[NULL] = BLACK;
        size = 0;
        root = NULL;
    }

    @Override
    public /*KC*/int/*KC*/[] keys() {
        /*KC*/int/*KC*/[] result = new /*KC*/int/*KC*/[size];
        for (int i = 0, x = firstNode(); x != NULL; x = successorNode(x), i++) {
            result[i] = keys[x];
        }
        return result;
    }

    @Override
    public /*V*/int/*V*/[] values() {
        /*V*/int/*V*/[] result = new /*V*/int/*V*/[size];
        for (int i = 0, x = firstNode(); x != NULL; x = successorNode(x), i++) {
            result[i] = values[x];
        }
        return result;
    }

    @Override
    public _Ez_Int__Int_MapIterator iterator() {
        return new _Ez_Int__Int_TreeMapIterator();
    }

    private void enlarge() {
        int newLength = Math.max(color.length + 1, (int) (color.length * ENLARGE_SCALE));
        keys = Arrays.copyOf(keys, newLength);
        values = Arrays.copyOf(values, newLength);
        left = Arrays.copyOf(left, newLength);
        right = Arrays.copyOf(right, newLength);
        p = Arrays.copyOf(p, newLength);
        color = Arrays.copyOf(color, newLength);
    }

    private int firstNode() {
        int x = root;
        while (left[x] != NULL) {
            x = left[x];
        }
        return x;
    }

    private int lastNode() {
        int x = root;
        while (right[x] != NULL) {
            x = right[x];
        }
        return x;
    }

    @Override
    public /*KC*/int/*KC*/ getFirstKey() {
        if (root == NULL) {
            returnedNull = true;
            return DEFAULT_NULL_KEY;
        }
        final int x = firstNode();
        returnedNull = false;
        return keys[x];
    }

    @Override
    public /*KC*/int/*KC*/ getLastKey() {
        if (root == NULL) {
            returnedNull = true;
            return DEFAULT_NULL_KEY;
        }
        final int x = lastNode();
        returnedNull = false;
        return keys[x];
    }

    @Override
    public _Ez_Int__Int_Pair getFirstEntry() {
        if (root == NULL) {
            returnedNull = true;
            return null;
        }
        final int x = firstNode();
        returnedNull = false;
        return new _Ez_Int__Int_Pair(keys[x], values[x]);
    }

    @Override
    public _Ez_Int__Int_Pair getLastEntry() {
        if (root == NULL) {
            returnedNull = true;
            return null;
        }
        final int x = lastNode();
        returnedNull = false;
        return new _Ez_Int__Int_Pair(keys[x], values[x]);
    }

    @Override
    public /*KC*/int/*KC*/ floorKey(/*KC*/int/*KC*/ key) {
        int x = root;
        while (x != NULL) {
            if (key > keys[x]) {
                if (right[x] != NULL) {
                    x = right[x];
                } else {
                    returnedNull = false;
                    return keys[x];
                }
            } else if (key < keys[x]) {
                if (left[x] != NULL) {
                    x = left[x];
                } else {
                    int y = p[x];
                    while (y != NULL && x == left[y]) {
                        //noinspection SuspiciousNameCombination
                        x = y;
                        y = p[y];
                    }
                    if (y == NULL) {
                        returnedNull = true;
                        return DEFAULT_NULL_KEY;
                    } else {
                        returnedNull = false;
                        return keys[y];
                    }
                }
            } else {
                returnedNull = false;
                return keys[x];
            }
        }
        returnedNull = true;
        return DEFAULT_NULL_KEY;
    }

    @Override
    public /*KC*/int/*KC*/ ceilingKey(/*KC*/int/*KC*/ key) {
        int x = root;
        while (x != NULL) {
            if (key < keys[x]) {
                if (left[x] != NULL) {
                    x = left[x];
                } else {
                    returnedNull = false;
                    return keys[x];
                }
            } else if (key > keys[x]) {
                if (right[x] != NULL) {
                    x = right[x];
                } else {
                    int y = p[x];
                    while (y != NULL && x == right[y]) {
                        //noinspection SuspiciousNameCombination
                        x = y;
                        y = p[y];
                    }
                    if (y == NULL) {
                        returnedNull = true;
                        return DEFAULT_NULL_KEY;
                    } else {
                        returnedNull = false;
                        return keys[y];
                    }
                }
            } else {
                returnedNull = false;
                return keys[x];
            }
        }
        returnedNull = true;
        return DEFAULT_NULL_KEY;
    }

    @Override
    public /*KC*/int/*KC*/ lowerKey(/*KC*/int/*KC*/ key) {
        int x = root;
        while (x != NULL) {
            if (key > keys[x]) {
                if (right[x] != NULL) {
                    x = right[x];
                } else {
                    returnedNull = false;
                    return keys[x];
                }
            } else {
                if (left[x] != NULL) {
                    x = left[x];
                } else {
                    int y = p[x];
                    while (y != NULL && x == left[y]) {
                        //noinspection SuspiciousNameCombination
                        x = y;
                        y = p[y];
                    }
                    if (y == NULL) {
                        returnedNull = true;
                        return DEFAULT_NULL_KEY;
                    } else {
                        returnedNull = false;
                        return keys[y];
                    }
                }
            }
        }
        returnedNull = true;
        return DEFAULT_NULL_KEY;
    }

    @Override
    public /*KC*/int/*KC*/ higherKey(/*KC*/int/*KC*/ key) {
        int x = root;
        while (x != NULL) {
            if (key < keys[x]) {
                if (left[x] != NULL) {
                    x = left[x];
                } else {
                    returnedNull = false;
                    return keys[x];
                }
            } else {
                if (right[x] != NULL) {
                    x = right[x];
                } else {
                    int y = p[x];
                    while (y != NULL && x == right[y]) {
                        //noinspection SuspiciousNameCombination
                        x = y;
                        y = p[y];
                    }
                    if (y == NULL) {
                        returnedNull = true;
                        return DEFAULT_NULL_KEY;
                    } else {
                        returnedNull = false;
                        return keys[y];
                    }
                }
            }
        }
        returnedNull = true;
        return DEFAULT_NULL_KEY;
    }

    @Override
    public _Ez_Int__Int_Pair floorEntry(/*KC*/int/*KC*/ key) {
        int x = root;
        while (x != NULL) {
            if (key > keys[x]) {
                if (right[x] != NULL) {
                    x = right[x];
                } else {
                    returnedNull = false;
                    return new _Ez_Int__Int_Pair(keys[x], values[x]);
                }
            } else if (key < keys[x]) {
                if (left[x] != NULL) {
                    x = left[x];
                } else {
                    int y = p[x];
                    while (y != NULL && x == left[y]) {
                        //noinspection SuspiciousNameCombination
                        x = y;
                        y = p[y];
                    }
                    if (y == NULL) {
                        returnedNull = true;
                        return null;
                    } else {
                        returnedNull = false;
                        return new _Ez_Int__Int_Pair(keys[y], values[y]);
                    }
                }
            } else {
                returnedNull = false;
                return new _Ez_Int__Int_Pair(keys[x], values[x]);
            }
        }
        returnedNull = true;
        return null;
    }

    @Override
    public _Ez_Int__Int_Pair ceilingEntry(/*KC*/int/*KC*/ key) {
        int x = root;
        while (x != NULL) {
            if (key < keys[x]) {
                if (left[x] != NULL) {
                    x = left[x];
                } else {
                    returnedNull = false;
                    return new _Ez_Int__Int_Pair(keys[x], values[x]);
                }
            } else if (key > keys[x]) {
                if (right[x] != NULL) {
                    x = right[x];
                } else {
                    int y = p[x];
                    while (y != NULL && x == right[y]) {
                        //noinspection SuspiciousNameCombination
                        x = y;
                        y = p[y];
                    }
                    if (y == NULL) {
                        returnedNull = true;
                        return null;
                    } else {
                        returnedNull = false;
                        return new _Ez_Int__Int_Pair(keys[y], values[y]);
                    }
                }
            } else {
                returnedNull = false;
                return new _Ez_Int__Int_Pair(keys[x], values[x]);
            }
        }
        returnedNull = true;
        return null;
    }

    @Override
    public _Ez_Int__Int_Pair lowerEntry(/*KC*/int/*KC*/ key) {
        int x = root;
        while (x != NULL) {
            if (key > keys[x]) {
                if (right[x] != NULL) {
                    x = right[x];
                } else {
                    returnedNull = false;
                    return new _Ez_Int__Int_Pair(keys[x], values[x]);
                }
            } else {
                if (left[x] != NULL) {
                    x = left[x];
                } else {
                    int y = p[x];
                    while (y != NULL && x == left[y]) {
                        //noinspection SuspiciousNameCombination
                        x = y;
                        y = p[y];
                    }
                    if (y == NULL) {
                        returnedNull = true;
                        return null;
                    } else {
                        returnedNull = false;
                        return new _Ez_Int__Int_Pair(keys[y], values[y]);
                    }
                }
            }
        }
        returnedNull = true;
        return null;
    }

    @Override
    public _Ez_Int__Int_Pair higherEntry(/*KC*/int/*KC*/ key) {
        int x = root;
        while (x != NULL) {
            if (key < keys[x]) {
                if (left[x] != NULL) {
                    x = left[x];
                } else {
                    returnedNull = false;
                    return new _Ez_Int__Int_Pair(keys[x], values[x]);
                }
            } else {
                if (right[x] != NULL) {
                    x = right[x];
                } else {
                    int y = p[x];
                    while (y != NULL && x == right[y]) {
                        //noinspection SuspiciousNameCombination
                        x = y;
                        y = p[y];
                    }
                    if (y == NULL) {
                        returnedNull = true;
                        return null;
                    } else {
                        returnedNull = false;
                        return new _Ez_Int__Int_Pair(keys[y], values[y]);
                    }
                }
            }
        }
        returnedNull = true;
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        _Ez_Int__Int_TreeMap that = (_Ez_Int__Int_TreeMap) o;

        if (size != that.size) {
            return false;
        }
        for (int x = firstNode(), y = that.firstNode();
             x != NULL;
             //noinspection SuspiciousNameCombination
             x = successorNode(x), y = that.successorNode(y)
        ) {
            if (keys[x] != that.keys[y] || values[x] != that.values[y]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = HASHCODE_INITIAL_VALUE;
        for (int x = firstNode(); x != NULL; x = successorNode(x)) {
            hash = (hash ^ PrimitiveHashCalculator.getHash(keys[x])) * HASHCODE_MULTIPLIER;
            hash = (hash ^ PrimitiveHashCalculator.getHash(values[x])) * HASHCODE_MULTIPLIER;
        }
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (int x = firstNode(); x != NULL; x = successorNode(x)) {
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(keys[x]);
            sb.append('=');
            sb.append(values[x]);
        }
        sb.append('}');
        return sb.toString();
    }

    private class _Ez_Int__Int_TreeMapIterator implements _Ez_Int__Int_MapIterator {
        private int x;

        private _Ez_Int__Int_TreeMapIterator() {
            x = firstNode();
        }

        @Override
        public boolean hasNext() {
            return x != NULL;
        }

        @Override
        public /*KC*/int/*KC*/ getKey() {
            if (x == NULL) {
                throw new NoSuchElementException("Iterator doesn't have more entries");
            }
            return keys[x];
        }

        @Override
        public /*V*/int/*V*/ getValue() {
            if (x == NULL) {
                throw new NoSuchElementException("Iterator doesn't have more entries");
            }
            return values[x];
        }

        @Override
        public void next() {
            if (x == NULL) {
                return;
            }
            x = successorNode(x);
        }
    }
}
