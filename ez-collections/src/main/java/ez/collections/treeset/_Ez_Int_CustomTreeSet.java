package ez.collections.treeset;

import ez.collections._Ez_Int_Collection;
import ez.collections._Ez_Int_Comparator;
import ez.collections._Ez_Int_Iterator;
import ez.collections._Ez_Int_SortedSet;
import ez.collections.misc.PrimitiveHashCalculator;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;

public class _Ez_Int_CustomTreeSet implements _Ez_Int_SortedSet {
    private static final int DEFAULT_CAPACITY = 10;
    private static final double ENLARGE_SCALE = 2.0;
    private static final int HASHCODE_INITIAL_VALUE = 0x811c9dc5;
    private static final int HASHCODE_MULTIPLIER = 0x01000193;

    private static final boolean BLACK = false;
    private static final boolean RED = true;
    private static final int NULL = 0;
    private static final /*C*/int/*C*/ DEFAULT_NULL_ELEMENT = (new /*C*/int/*C*/[1])[0];

    // Arrays are 1-indexed. Index 0 is a null node.
    private /*C*/int/*C*/[] key;
    private int[] left;
    private int[] right;
    private int[] p;
    private boolean[] color;

    private int size;
    private int root;
    private boolean returnedNull;
    private _Ez_Int_Comparator cmp;

    public _Ez_Int_CustomTreeSet(_Ez_Int_Comparator cmp) {
        this(DEFAULT_CAPACITY, cmp);
    }

    public _Ez_Int_CustomTreeSet(int capacity, _Ez_Int_Comparator cmp) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must be non-negative");
        }
        this.cmp = cmp;
        capacity++;
        key = new /*C*/int/*C*/[capacity];
        left = new int[capacity];
        right = new int[capacity];
        p = new int[capacity];
        color = new boolean[capacity];
        color[NULL] = BLACK;
        size = 0;
        root = NULL;
        returnedNull = false;
    }

    public _Ez_Int_CustomTreeSet(_Ez_Int_Collection collection, _Ez_Int_Comparator cmp) {
        this(collection.size(), cmp);
        for (_Ez_Int_Iterator iterator = collection.iterator(); iterator.hasNext(); ) {
            add(iterator.next());
        }
    }

    public _Ez_Int_CustomTreeSet(/*C*/int/*C*/[] srcArray, _Ez_Int_Comparator cmp) {
        this(srcArray.length, cmp);
        for (/*C*/int/*C*/ element : srcArray) {
            add(element);
        }
    }

    public _Ez_Int_CustomTreeSet(Collection</*WC*/Integer/*WC*/> javaCollection, _Ez_Int_Comparator cmp) {
        this(javaCollection.size(), cmp);
        for (/*C*/int/*C*/ element : javaCollection) {
            add(element);
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
    public boolean contains(/*C*/int/*C*/ element) {
        int x = root;
        while (x != NULL) {
            int cmpRes = cmp.compare(element, key[x]);
            if (cmpRes < 0) {
                x = left[x];
            } else if (cmpRes > 0) {
                x = right[x];
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public _Ez_Int_Iterator iterator() {
        return new _Ez_Int_TreeSetIterator();
    }

    @Override
    public /*C*/int/*C*/[] toArray() {
        /*C*/int/*C*/[] result = new /*C*/int/*C*/[size];
        for (int i = 0, x = firstNode(); x != NULL; x = successorNode(x), i++) {
            result[i] = key[x];
        }
        return result;
    }

    @Override
    public boolean add(/*C*/int/*C*/ element) {
        int y = NULL;
        int x = root;
        while (x != NULL) {
            //noinspection SuspiciousNameCombination
            y = x;
            int cmpRes = cmp.compare(element, key[x]);
            if (cmpRes < 0) {
                x = left[x];
            } else if (cmpRes > 0) {
                x = right[x];
            } else {
                return false;
            }
        }
        if (size == color.length - 1) {
            enlarge();
        }
        int z = ++size;
        key[z] = element;
        p[z] = y;
        if (y == NULL) {
            root = z;
        } else {
            if (cmp.compare(element, key[y]) < 0) {
                left[y] = z;
            } else {
                right[y] = z;
            }
        }
        left[z] = NULL;
        right[z] = NULL;
        color[z] = RED;
        fixAfterAdd(z);
        return true;
    }

    @Override
    public boolean remove(/*C*/int/*C*/ element) {
        int z = root;
        while (z != NULL) {
            int cmpRes = cmp.compare(element, key[z]);
            if (cmpRes < 0) {
                z = left[z];
            } else if (cmpRes > 0) {
                z = right[z];
            } else {
                removeNode(z);
                return true;
            }
        }
        return false;
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
            key[z] = key[y];
        }
        //noinspection PointlessBooleanExpression
        if (color[y] == BLACK) {
            fixAfterRemove(x);
        }
        // Swap with last
        if (y != size) {
            // copy fields
            key[y] = key[size];
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
    public void clear() {
        color[NULL] = BLACK;
        size = 0;
        root = NULL;
    }

    private void enlarge() {
        int newLength = Math.max(color.length + 1, (int) (color.length * ENLARGE_SCALE));
        key = Arrays.copyOf(key, newLength);
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
    public /*C*/int/*C*/ getFirst() {
        if (root == NULL) {
            returnedNull = true;
            return DEFAULT_NULL_ELEMENT;
        }
        final int x = firstNode();
        returnedNull = false;
        return key[x];
    }

    @Override
    public /*C*/int/*C*/ getLast() {
        if (root == NULL) {
            returnedNull = true;
            return DEFAULT_NULL_ELEMENT;
        }
        final int x = lastNode();
        returnedNull = false;
        return key[x];
    }

    @Override
    public /*C*/int/*C*/ removeFirst() {
        if (root == NULL) {
            returnedNull = true;
            return DEFAULT_NULL_ELEMENT;
        }
        final int x = firstNode();
        returnedNull = false;
        final /*C*/int/*C*/ removedElement = key[x];
        removeNode(x);
        return removedElement;
    }

    @Override
    public /*C*/int/*C*/ removeLast() {
        if (root == NULL) {
            returnedNull = true;
            return DEFAULT_NULL_ELEMENT;
        }
        final int x = lastNode();
        returnedNull = false;
        final /*C*/int/*C*/ removedElement = key[x];
        removeNode(x);
        return removedElement;
    }

    @Override
    public /*C*/int/*C*/ floor(/*C*/int/*C*/ element) {
        int x = root;
        while (x != NULL) {
            int cmpRes = cmp.compare(element, key[x]);
            if (cmpRes > 0) {
                if (right[x] != NULL) {
                    x = right[x];
                } else {
                    returnedNull = false;
                    return key[x];
                }
            } else if (cmpRes < 0) {
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
                        return DEFAULT_NULL_ELEMENT;
                    } else {
                        returnedNull = false;
                        return key[y];
                    }
                }
            } else {
                returnedNull = false;
                return key[x];
            }
        }
        returnedNull = true;
        return DEFAULT_NULL_ELEMENT;
    }

    @Override
    public /*C*/int/*C*/ ceiling(/*C*/int/*C*/ element) {
        int x = root;
        while (x != NULL) {
            int cmpRes = cmp.compare(element, key[x]);
            if (cmpRes < 0) {
                if (left[x] != NULL) {
                    x = left[x];
                } else {
                    returnedNull = false;
                    return key[x];
                }
            } else if (cmpRes > 0) {
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
                        return DEFAULT_NULL_ELEMENT;
                    } else {
                        returnedNull = false;
                        return key[y];
                    }
                }
            } else {
                returnedNull = false;
                return key[x];
            }
        }
        returnedNull = true;
        return DEFAULT_NULL_ELEMENT;
    }

    @Override
    public /*C*/int/*C*/ lower(/*C*/int/*C*/ element) {
        int x = root;
        while (x != NULL) {
            if (cmp.compare(element, key[x]) > 0) {
                if (right[x] != NULL) {
                    x = right[x];
                } else {
                    returnedNull = false;
                    return key[x];
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
                        return DEFAULT_NULL_ELEMENT;
                    } else {
                        returnedNull = false;
                        return key[y];
                    }
                }
            }
        }
        returnedNull = true;
        return DEFAULT_NULL_ELEMENT;
    }

    @Override
    public /*C*/int/*C*/ higher(/*C*/int/*C*/ element) {
        int x = root;
        while (x != NULL) {
            if (cmp.compare(element, key[x]) < 0) {
                if (left[x] != NULL) {
                    x = left[x];
                } else {
                    returnedNull = false;
                    return key[x];
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
                        return DEFAULT_NULL_ELEMENT;
                    } else {
                        returnedNull = false;
                        return key[y];
                    }
                }
            }
        }
        returnedNull = true;
        return DEFAULT_NULL_ELEMENT;
    }

    @Override
    public boolean returnedNull() {
        return returnedNull;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        _Ez_Int_CustomTreeSet that = (_Ez_Int_CustomTreeSet) o;

        if (size != that.size) {
            return false;
        }
        for (int x = firstNode(), y = that.firstNode();
             x != NULL;
             //noinspection SuspiciousNameCombination
             x = successorNode(x), y = that.successorNode(y)
        ) {
            if (key[x] != that.key[y]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = HASHCODE_INITIAL_VALUE;
        for (int x = firstNode(); x != NULL; x = successorNode(x)) {
            hash = (hash ^ PrimitiveHashCalculator.getHash(key[x])) * HASHCODE_MULTIPLIER;
        }
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int x = firstNode(); x != NULL; x = successorNode(x)) {
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(key[x]);
        }
        sb.append(']');
        return sb.toString();
    }

    private class _Ez_Int_TreeSetIterator implements _Ez_Int_Iterator {
        private int x;

        private _Ez_Int_TreeSetIterator() {
            x = firstNode();
        }

        @Override
        public boolean hasNext() {
            return x != NULL;
        }

        @Override
        public /*C*/int/*C*/ next() {
            if (x == NULL) {
                throw new NoSuchElementException("Iterator doesn't have more elements");
            }
            final /*C*/int/*C*/ result = key[x];
            x = successorNode(x);
            return result;
        }
    }
}
