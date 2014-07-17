package ez.collections.heap;

import ez.collections._Ez_Int_Collection;
import ez.collections._Ez_Int_Iterator;
import ez.collections._Ez_Int_PriorityQueue;
import ez.collections.misc.PrimitiveHashCalculator;
import ez.collections.sort._Ez_Int_Sort;

import java.util.Collection;
import java.util.NoSuchElementException;

public class _Ez_Int_Heap implements _Ez_Int_PriorityQueue {
    private static final int DEFAULT_CAPACITY = 10;
    private static final double ENLARGE_SCALE = 2.0;
    private static final int HASHCODE_INITIAL_VALUE = 0x811c9dc5;
    private static final int HASHCODE_MULTIPLIER = 0x01000193;

    private /*C*/int/*C*/[] heap;
    private int size;

    public _Ez_Int_Heap() {
        this(DEFAULT_CAPACITY);
    }

    public _Ez_Int_Heap(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must be non-negative");
        }
        heap = new /*C*/int/*C*/[capacity];
        size = 0;
    }

    public _Ez_Int_Heap(_Ez_Int_Collection collection) {
        size = collection.size();
        heap = new /*C*/int/*C*/[size];
        int i = 0;
        for (_Ez_Int_Iterator iterator = collection.iterator(); iterator.hasNext(); ) {
            heap[i++] = iterator.next();
        }
        makeHeap();
    }

    public _Ez_Int_Heap(/*C*/int/*C*/[] srcArray) {
        size = srcArray.length;
        heap = new /*C*/int/*C*/[size];
        System.arraycopy(srcArray, 0, heap, 0, size);
        makeHeap();
    }

    public _Ez_Int_Heap(Collection</*WC*/Integer/*WC*/> javaCollection) {
        size = javaCollection.size();
        heap = new /*C*/int/*C*/[size];
        int i = 0;
        for (/*C*/int/*C*/ element : javaCollection) {
            heap[i++] = element;
        }
        makeHeap();
    }

    private void makeHeap() {
        for (int i = (size >>> 1) - 1; i >= 0; i--) {
            down(i);
        }
    }

    private void up(int index) {
        final /*C*/int/*C*/ element = heap[index];
        int parent = (index - 1) >>> 1;
        while (index > 0 && element < heap[parent]) {
            heap[index] = heap[parent];
            index = parent;
            parent = (index - 1) >>> 1;
        }
        heap[index] = element;
    }

    private void down(int index) {
        final /*C*/int/*C*/ element = heap[index];
        final int firstLeaf = size >>> 1;
        while (index < firstLeaf) {
            int smallestChild = (index << 1) + 1;
            if (smallestChild + 1 < size && heap[smallestChild + 1] < heap[smallestChild]) {
                smallestChild++;
            }
            if (heap[smallestChild] >= element) {
                break;
            }
            heap[index] = heap[smallestChild];
            index = smallestChild;
        }
        heap[index] = element;
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
        for (int i = 0; i < size; i++) {
            if (heap[i] == element) {
                return true;
            }
        }
        return false;
    }

    @Override
    public _Ez_Int_Iterator iterator() {
        return new _Ez_Int_HeapIterator();
    }

    @Override
    public /*C*/int/*C*/[] toArray() {
        /*C*/int/*C*/[] result = new /*C*/int/*C*/[size];
        System.arraycopy(heap, 0, result, 0, size);
        return result;
    }

    @Override
    public boolean add(/*C*/int/*C*/ element) {
        if (size == heap.length) {
            enlarge();
        }
        heap[size] = element;
        up(size++);
        return true;
    }

    @Override
    public boolean remove(/*C*/int/*C*/ element) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (heap[i] == element) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return false;
        }
        heap[index] = heap[--size];
        if (index > 0 && heap[index] <= heap[(index - 1) >>> 1]) {
            up(index);
        } else {
            down(index);
        }
        return true;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public /*C*/int/*C*/ getFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Trying to call getFirst() on empty Heap");
        }
        return heap[0];
    }

    @Override
    public /*C*/int/*C*/ removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Trying to call removeFirst() on empty Heap");
        }
        final /*C*/int/*C*/ minimalElement = heap[0];
        heap[0] = heap[--size];
        down(0);
        return minimalElement;
    }

    private void enlarge() {
        int newSize = Math.max(size + 1, (int) (size * ENLARGE_SCALE));
        /*C*/int/*C*/[] newArray = new /*C*/int/*C*/[newSize];
        System.arraycopy(heap, 0, newArray, 0, size);
        heap = newArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        _Ez_Int_Heap that = (_Ez_Int_Heap) o;

        if (size != that.size) {
            return false;
        }
        /*C*/int/*C*/[] thisArray = toArray();
        /*C*/int/*C*/[] thatArray = that.toArray();
        _Ez_Int_Sort.sort(thisArray);
        _Ez_Int_Sort.sort(thatArray);
        for (int i = 0; i < size; i++) {
            if (thisArray[i] != thatArray[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        /*C*/int/*C*/[] array = toArray();
        _Ez_Int_Sort.sort(array);
        int hash = HASHCODE_INITIAL_VALUE;
        for (int i = 0; i < size; i++) {
            hash = (hash ^ PrimitiveHashCalculator.getHash(array[i])) * HASHCODE_MULTIPLIER;
        }
        return hash;
    }

    @Override
    public String toString() {
        /*C*/int/*C*/[] array = toArray();
        _Ez_Int_Sort.sort(array);
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < size; i++) {
            sb.append(array[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }

    private class _Ez_Int_HeapIterator implements _Ez_Int_Iterator {
        private int curIndex = 0;

        @Override
        public boolean hasNext() {
            return curIndex < size;
        }

        @Override
        public /*C*/int/*C*/ next() {
            if (curIndex == size) {
                throw new NoSuchElementException("Iterator doesn't have more elements");
            }
            return heap[curIndex++];
        }
    }
}
