package ez.collections.arraydeque;

import ez.collections._Ez_Int_Collection;
import ez.collections._Ez_Int_Iterator;
import ez.collections._Ez_Int_Deque;
import ez.collections._Ez_Int_List;
import ez.collections.misc.PrimitiveHashCalculator;

import java.util.Collection;
import java.util.NoSuchElementException;

public class _Ez_Int_ArrayDeque implements _Ez_Int_Deque, _Ez_Int_List {
    private static final int DEFAULT_CAPACITY = 8;
    private static final int HASHCODE_INITIAL_VALUE = 0x811c9dc5;
    private static final int HASHCODE_MULTIPLIER = 0x01000193;

    private /*T*/int/*T*/[] deque;
    private int size;
    private int head;
    private int tail;
    private int mask;

    public _Ez_Int_ArrayDeque() {
        this(DEFAULT_CAPACITY);
    }

    public _Ez_Int_ArrayDeque(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must be non-negative");
        }
        if (capacity < 1) {
            capacity = 1;
        }
        if ((capacity & (capacity - 1)) != 0) {
            capacity = Integer.highestOneBit(capacity) << 1;
        }
        // Capacity is a power of 2 now
        deque = new /*T*/int/*T*/[capacity];
        size = 0;
        head = 0;
        tail = 0;
        mask = deque.length - 1;
    }

    public _Ez_Int_ArrayDeque(_Ez_Int_Collection collection) {
        this(collection.size() + 1);
        for (_Ez_Int_Iterator iterator = collection.iterator(); iterator.hasNext(); ) {
            deque[tail++] = iterator.next();
        }
        size = collection.size();
    }

    public _Ez_Int_ArrayDeque(/*T*/int/*T*/[] srcArray) {
        this(srcArray.length + 1);
        System.arraycopy(srcArray, 0, deque, 0, srcArray.length);
        tail = srcArray.length;
        size = srcArray.length;
    }

    public _Ez_Int_ArrayDeque(Collection</*W*/Integer/*W*/> javaCollection) {
        this(javaCollection.size() + 1);
        for (/*T*/int/*T*/ element : javaCollection) {
            deque[tail++] = element;
        }
        size = javaCollection.size();
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
        for (int i = head; i != tail; i = (i + 1) & mask) {
            if (deque[i] == element) {
                return true;
            }
        }
        return false;
    }

    @Override
    public _Ez_Int_Iterator iterator() {
        return new _Ez_Int_ArrayDequeIterator();
    }

    @Override
    public /*T*/int/*T*/[] toArray() {
        /*T*/int/*T*/[] result = new /*T*/int/*T*/[size];
        for (int i = head, j = 0; i != tail; i = (i + 1) & mask) {
            result[j++] = deque[i];
        }
        return result;
    }

    @Override
    public boolean add(/*T*/int/*T*/ element) {
        deque[tail] = element;
        tail = (tail + 1) & mask;
        size++;
        if (size == deque.length) {
            enlarge();
        }
        return true;
    }

    @Override
    public boolean remove(/*T*/int/*T*/ element) {
        for (int i = head, k = 0; i != tail; i = (i + 1) & mask, k++) {
            if (deque[i] == element) {
                if (k >= (size >>> 1)) {
                    for (int j = (i + 1) & mask; j != tail; j = (j + 1) & mask) {
                        deque[(j - 1) & mask] = deque[j];
                    }
                    tail = (tail - 1) & mask;
                } else {
                    for (int j = (i - 1) & mask, end = (head - 1) & mask; j != end; j = (j - 1) & mask) {
                        deque[(j + 1) & mask] = deque[j];
                    }
                    head = (head + 1) & mask;
                }
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        size = 0;
        head = 0;
        tail = 0;
    }

    @Override
    public void addFirst(/*T*/int/*T*/ element) {
        head = (head - 1) & mask;
        deque[head] = element;
        size++;
        if (size == deque.length) {
            enlarge();
        }
    }

    @Override
    public void addLast(/*T*/int/*T*/ element) {
        deque[tail] = element;
        tail = (tail + 1) & mask;
        size++;
        if (size == deque.length) {
            enlarge();
        }
    }

    @Override
    public /*T*/int/*T*/ getFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Trying to call getFirst() on empty ArrayDeque");
        }
        return deque[head];
    }

    @Override
    public /*T*/int/*T*/ getLast() {
        if (size == 0) {
            throw new NoSuchElementException("Trying to call getLast() on empty ArrayDeque");
        }
        return deque[(tail - 1) & mask];
    }

    @Override
    public /*T*/int/*T*/ removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Trying to call removeFirst() on empty ArrayDeque");
        }
        final /*T*/int/*T*/ removedElement = deque[head];
        size--;
        head = (head + 1) & mask;
        return removedElement;
    }

    @Override
    public /*T*/int/*T*/ removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("Trying to call removeLast() on empty ArrayDeque");
        }
        size--;
        tail = (tail - 1) & mask;
        return deque[tail];
    }

    @Override
    public /*T*/int/*T*/ get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range, size = " + size);
        }
        return deque[(head + index) & mask];
    }

    @Override
    public /*T*/int/*T*/ set(int index, /*T*/int/*T*/ element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range, size = " + size);
        }
        final int realIndex = (head + index) & mask;
        final /*T*/int/*T*/ oldElement = deque[realIndex];
        deque[realIndex] = element;
        return oldElement;
    }

    @Override
    public void insert(int index, /*T*/int/*T*/ element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range, size = " + size);
        }
        if (index > (size >>> 1)) {
            final int realIndex = (head + index) & mask;
            for (int i = tail; i != realIndex; i = (i - 1) & mask) {
                deque[i] = deque[(i - 1) & mask];
            }
            tail = (tail + 1) & mask;
            deque[realIndex] = element;
        } else {
            head = (head - 1) & mask;
            final int realIndex = (head + index) & mask;
            for (int i = head; i != realIndex; i = (i + 1) & mask) {
                deque[i] = deque[(i + 1) & mask];
            }
            deque[realIndex] = element;
        }
        size++;
        if (size == deque.length) {
            enlarge();
        }
    }

    @Override
    public /*T*/int/*T*/ removeAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range, size = " + size);
        }
        final int realIndex = (head + index) & mask;
        final /*T*/int/*T*/ removedElement = deque[realIndex];
        if (index >= (size >>> 1)) {
            for (int i = (realIndex + 1) & mask; i != tail; i = (i + 1) & mask) {
                deque[(i - 1) & mask] = deque[i];
            }
            tail = (tail - 1) & mask;
        } else {
            for (int i = (realIndex - 1) & mask, end = (head - 1) & mask; i != end; i = (i - 1) & mask) {
                deque[(i + 1) & mask] = deque[i];
            }
            head = (head + 1) & mask;
        }
        size--;
        return removedElement;
    }

    @Override
    public int indexOf(/*T*/int/*T*/ element) {
        for (int i = head, j = 0; i != tail; i = (i + 1) & mask, j++) {
            if (deque[i] == element) {
                return j;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(/*T*/int/*T*/ element) {
        for (int i = (tail - 1) & mask, j = size - 1, end = (head - 1) & mask; i != end; i = (i - 1) & mask, j--) {
            if (deque[i] == element) {
                return j;
            }
        }
        return -1;
    }

    private void enlarge() {
        int newSize = (size << 1);
        /*T*/int/*T*/[] newArray = new /*T*/int/*T*/[newSize];
        System.arraycopy(deque, head, newArray, 0, deque.length - head);
        System.arraycopy(deque, 0, newArray, deque.length - tail, tail);
        deque = newArray;
        head = 0;
        tail = size;
        mask = deque.length - 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        _Ez_Int_ArrayDeque that = (_Ez_Int_ArrayDeque) o;

        if (size != that.size) {
            return false;
        }
        for (int i = head, j = that.head; i != tail; i = (i + 1) & mask, j = (j + 1) & that.mask) {
            if (deque[i] != that.deque[j]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = HASHCODE_INITIAL_VALUE;
        for (int i = head; i != tail; i = (i + 1) & mask) {
            hash = (hash ^ PrimitiveHashCalculator.getHash(deque[i])) * HASHCODE_MULTIPLIER;
        }
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = head; i != tail; i = (i + 1) & mask) {
            if (i != head) {
                sb.append(", ");
            }
            sb.append(deque[i]);
        }
        sb.append(']');
        return sb.toString();
    }

    private class _Ez_Int_ArrayDequeIterator implements _Ez_Int_Iterator {
        private int curIndex = head;

        @Override
        public boolean hasNext() {
            return curIndex != tail;
        }

        @Override
        public /*T*/int/*T*/ next() {
            if (curIndex == tail) {
                throw new NoSuchElementException("Iterator doesn't have more elements");
            }
            final /*T*/int/*T*/ result = deque[curIndex];
            curIndex = (curIndex + 1) & mask;
            return result;
        }
    }
}
