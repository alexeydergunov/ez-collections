package ez.collections.heap;

import ez.collections._Ez_Int_Collection;
import ez.collections._Ez_Int_Comparator;
import ez.collections._Ez_Int_Iterator;
import ez.collections._Ez_Int_PriorityQueue;
import ez.collections.misc.PrimitiveHashCalculator;
import ez.collections.sort._Ez_Int_SortWithComparator;

import java.util.Collection;
import java.util.NoSuchElementException;

public class _Ez_Int_CustomHeap implements _Ez_Int_PriorityQueue {
    private static final int DEFAULT_CAPACITY = 10;
    private static final double ENLARGE_SCALE = 2.0;
    private static final int HASHCODE_INITIAL_VALUE = 0x811c9dc5;
    private static final int HASHCODE_MULTIPLIER = 0x01000193;

    private /*C*/int/*C*/[] heap;
    private int size;
    private _Ez_Int_Comparator cmp;

    public _Ez_Int_CustomHeap(_Ez_Int_Comparator cmp) {
        this(DEFAULT_CAPACITY, cmp);
    }

    public _Ez_Int_CustomHeap(int capacity, _Ez_Int_Comparator cmp) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must be non-negative");
        }
        heap = new /*C*/int/*C*/[capacity];
        size = 0;
        this.cmp = cmp;
    }

    public _Ez_Int_CustomHeap(_Ez_Int_Collection collection, _Ez_Int_Comparator cmp) {
        size = collection.size();
        heap = new /*C*/int/*C*/[size];
        this.cmp = cmp;
        int i = 0;
        for (_Ez_Int_Iterator iterator = collection.iterator(); iterator.hasNext(); ) {
            heap[i++] = iterator.next();
        }
        makeHeap();
    }

    public _Ez_Int_CustomHeap(/*C*/int/*C*/[] srcArray, _Ez_Int_Comparator cmp) {
        size = srcArray.length;
        heap = new /*C*/int/*C*/[size];
        this.cmp = cmp;
        System.arraycopy(srcArray, 0, heap, 0, size);
        makeHeap();
    }

    public _Ez_Int_CustomHeap(Collection</*WC*/Integer/*WC*/> javaCollection, _Ez_Int_Comparator cmp) {
        size = javaCollection.size();
        heap = new /*C*/int/*C*/[size];
        this.cmp = cmp;
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
        while (index > 0 && cmp.compare(element, heap[parent]) < 0) {
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
            if (smallestChild + 1 < size && cmp.compare(heap[smallestChild + 1], heap[smallestChild]) < 0) {
                smallestChild++;
            }
            if (cmp.compare(heap[smallestChild], element) >= 0) {
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
        if (index > 0 && cmp.compare(heap[index], heap[(index - 1) >>> 1]) <= 0) {
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
        final /*C*/int/*C*/ firstElement = heap[0];
        heap[0] = heap[--size];
        down(0);
        return firstElement;
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
        _Ez_Int_CustomHeap that = (_Ez_Int_CustomHeap) o;

        if (size != that.size) {
            return false;
        }
        /*C*/int/*C*/[] thisArray = toArray();
        /*C*/int/*C*/[] thatArray = that.toArray();
        _Ez_Int_SortWithComparator.sort(thisArray, cmp);
        _Ez_Int_SortWithComparator.sort(thatArray, cmp);
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
        _Ez_Int_SortWithComparator.sort(array, cmp);
        int hash = HASHCODE_INITIAL_VALUE;
        for (int i = 0; i < size; i++) {
            hash = (hash ^ PrimitiveHashCalculator.getHash(array[i])) * HASHCODE_MULTIPLIER;
        }
        return hash;
    }

    @Override
    public String toString() {
        /*C*/int/*C*/[] array = toArray();
        _Ez_Int_SortWithComparator.sort(array, cmp);
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
