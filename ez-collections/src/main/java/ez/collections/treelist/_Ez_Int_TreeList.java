package ez.collections.treelist;

import ez.collections._Ez_Int_Collection;
import ez.collections._Ez_Int_Deque;
import ez.collections._Ez_Int_Iterator;
import ez.collections._Ez_Int_List;
import ez.collections.misc.PrimitiveHashCalculator;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;

public class _Ez_Int_TreeList implements _Ez_Int_List, _Ez_Int_Deque {
    private static final int DEFAULT_CAPACITY = 10;
    private static final double ENLARGE_SCALE = 2.0;
    private static final int HASHCODE_INITIAL_VALUE = 0x811c9dc5;
    private static final int HASHCODE_MULTIPLIER = 0x01000193;

    private static final boolean BLACK = false;
    private static final boolean RED = true;
    private static final int NULL = 0;

    // Arrays are 1-indexed. Index 0 is a null node.
    private /*T*/int/*T*/[] value;
    private int[] subtreeSize;
    private int[] left;
    private int[] right;
    private int[] p;
    private boolean[] color;

    private int size;
    private int root;

    public _Ez_Int_TreeList() {
        this(DEFAULT_CAPACITY);
    }

    public _Ez_Int_TreeList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must be non-negative");
        }
        capacity++;
        value = new /*T*/int/*T*/[capacity];
        subtreeSize = new int[capacity];
        left = new int[capacity];
        right = new int[capacity];
        p = new int[capacity];
        color = new boolean[capacity];
        color[NULL] = BLACK;
        size = 0;
        root = NULL;
    }

    public _Ez_Int_TreeList(_Ez_Int_Collection collection) {
        this(collection.size());
        for (_Ez_Int_Iterator iterator = collection.iterator(); iterator.hasNext(); ) {
            add(iterator.next());
        }
    }

    public _Ez_Int_TreeList(/*T*/int/*T*/[] srcArray) {
        this(srcArray.length);
        for (/*T*/int/*T*/ element : srcArray) {
            add(element);
        }
    }

    public _Ez_Int_TreeList(Collection</*W*/Integer/*W*/> javaCollection) {
        this(javaCollection.size());
        for (/*T*/int/*T*/ element : javaCollection) {
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
    public boolean contains(/*T*/int/*T*/ element) {
        for (int x = firstNode(); x != NULL; x = successorNode(x)) {
            if (value[x] == element) {
                return true;
            }
        }
        return false;
    }

    @Override
    public _Ez_Int_Iterator iterator() {
        return new _Ez_Int_TreeListIterator();
    }

    @Override
    public /*T*/int/*T*/[] toArray() {
        /*T*/int/*T*/[] result = new /*T*/int/*T*/[size];
        for (int i = 0, x = firstNode(); x != NULL; x = successorNode(x), i++) {
            result[i] = value[x];
        }
        return result;
    }

    @Override
    public boolean add(/*T*/int/*T*/ element) {
        final int y = lastNode();
        addAsChild(y, false, element);
        return true;
    }

    // creates a new node z with value[z] == element as a child of node y
    private void addAsChild(int y, boolean asLeftChild, /*T*/int/*T*/ element) {
        if (size == color.length - 1) {
            enlarge();
        }
        int z = ++size;
        value[z] = element;
        p[z] = y;
        if (y == NULL) {
            root = z;
        } else {
            if (asLeftChild) {
                left[y] = z;
            } else {
                right[y] = z;
            }
        }
        left[z] = NULL;
        right[z] = NULL;
        color[z] = RED;
        subtreeSize[z] = 1;
        for (int w = p[z]; w != NULL; w = p[w]) {
            subtreeSize[w]++;
        }
        fixAfterAdd(z);
    }

    @Override
    public boolean remove(/*T*/int/*T*/ element) {
        for (int x = firstNode(); x != NULL; x = successorNode(x)) {
            if (value[x] == element) {
                removeNode(x);
                return true;
            }
        }
        return false;
    }

    private int kThNode(int k) {
        if (k < 0 || k >= size) {
            throw new IllegalArgumentException("Index " + k + " was passed into kThNode(), size = " + size);
        }
        int x = root;
        while (true) {
            final int leftSize = subtreeSize[left[x]];
            if (k < leftSize) {
                x = left[x];
            } else if (k > leftSize) {
                k -= leftSize + 1;
                x = right[x];
            } else {
                return x;
            }
        }
    }

    private void removeNode(int z) {
        int y = (left[z] == NULL || right[z] == NULL) ? z : successorNode(z);
        for (int w = y; w != NULL; w = p[w]) {
            subtreeSize[w]--;
        }
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
            value[z] = value[y];
        }
        //noinspection PointlessBooleanExpression
        if (color[y] == BLACK) {
            fixAfterRemove(x);
        }
        // Swap with last
        if (y != size) {
            // copy fields
            value[y] = value[size];
            subtreeSize[y] = subtreeSize[size];
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

    private int predecessorNode(int x) {
        if (left[x] != NULL) {
            x = left[x];
            while (right[x] != NULL) {
                x = right[x];
            }
            return x;
        } else {
            int y = p[x];
            while (y != NULL && x == left[y]) {
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
        subtreeSize[y] = subtreeSize[x];
        subtreeSize[x] = subtreeSize[left[x]] + subtreeSize[right[x]] + 1;
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
        subtreeSize[y] = subtreeSize[x];
        subtreeSize[x] = subtreeSize[left[x]] + subtreeSize[right[x]] + 1;
    }

    @Override
    public void clear() {
        color[NULL] = BLACK;
        size = 0;
        root = NULL;
    }

    private void enlarge() {
        int newLength = Math.max(color.length + 1, (int) (color.length * ENLARGE_SCALE));
        value = Arrays.copyOf(value, newLength);
        subtreeSize = Arrays.copyOf(subtreeSize, newLength);
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
    public /*T*/int/*T*/ get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range, size = " + size);
        }
        final int x = kThNode(index);
        return value[x];
    }

    @Override
    public /*T*/int/*T*/ set(int index, /*T*/int/*T*/ element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range, size = " + size);
        }
        final int x = kThNode(index);
        final /*T*/int/*T*/ oldValue = value[x];
        value[x] = element;
        return oldValue;
    }

    @Override
    public void insert(int index, /*T*/int/*T*/ element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range, size = " + size);
        }
        if (index == 0) {
            final int y = firstNode();
            addAsChild(y, true, element);
        } else {
            final int x = kThNode(index - 1);
            final int y = (right[x] == NULL) ? x : successorNode(x);
            // we add the new node as a successor of the (index-1)-th node
            // node y will be the parent of the new node
            // if x == y, x doesn't have the right child, and we add the new node as a right child of x
            // if x != y, the new node will be the left child of y, where y is the successor node of x
            addAsChild(y, x != y, element);
        }
    }

    @Override
    public /*T*/int/*T*/ removeAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range, size = " + size);
        }
        final int x = kThNode(index);
        final /*T*/int/*T*/ removedValue = value[x];
        removeNode(x);
        return removedValue;
    }

    @Override
    public int indexOf(/*T*/int/*T*/ element) {
        for (int x = firstNode(), i = 0; x != NULL; x = successorNode(x), i++) {
            if (value[x] == element) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(/*T*/int/*T*/ element) {
        for (int x = lastNode(), i = size - 1; x != NULL; x = predecessorNode(x), i--) {
            if (value[x] == element) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void addFirst(/*T*/int/*T*/ element) {
        final int y = firstNode();
        addAsChild(y, true, element);
    }

    @Override
    public void addLast(/*T*/int/*T*/ element) {
        final int y = lastNode();
        addAsChild(y, false, element);
    }

    @Override
    public /*T*/int/*T*/ getFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Trying to call getFirst() on empty TreeList");
        }
        final int x = firstNode();
        return value[x];
    }

    @Override
    public /*T*/int/*T*/ getLast() {
        if (size == 0) {
            throw new NoSuchElementException("Trying to call getLast() on empty TreeList");
        }
        final int x = lastNode();
        return value[x];
    }

    @Override
    public /*T*/int/*T*/ removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Trying to call removeFirst() on empty TreeList");
        }
        final int x = firstNode();
        final /*T*/int/*T*/ removedValue = value[x];
        removeNode(x);
        return removedValue;
    }

    @Override
    public /*T*/int/*T*/ removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("Trying to call removeLast() on empty TreeList");
        }
        final int x = lastNode();
        final /*T*/int/*T*/ removedValue = value[x];
        removeNode(x);
        return removedValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        _Ez_Int_TreeList that = (_Ez_Int_TreeList) o;

        if (size != that.size) {
            return false;
        }
        for (int x = firstNode(), y = that.firstNode();
             x != NULL;
             //noinspection SuspiciousNameCombination
             x = successorNode(x), y = that.successorNode(y)
        ) {
            if (value[x] != that.value[y]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = HASHCODE_INITIAL_VALUE;
        for (int x = firstNode(); x != NULL; x = successorNode(x)) {
            hash = (hash ^ PrimitiveHashCalculator.getHash(value[x])) * HASHCODE_MULTIPLIER;
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
            sb.append(value[x]);
        }
        sb.append(']');
        return sb.toString();
    }

    private class _Ez_Int_TreeListIterator implements _Ez_Int_Iterator {
        private int x;

        private _Ez_Int_TreeListIterator() {
            x = firstNode();
        }

        @Override
        public boolean hasNext() {
            return x != NULL;
        }

        @Override
        public /*T*/int/*T*/ next() {
            if (x == NULL) {
                throw new NoSuchElementException("Iterator doesn't have more elements");
            }
            final /*T*/int/*T*/ result = value[x];
            x = successorNode(x);
            return result;
        }
    }
}
