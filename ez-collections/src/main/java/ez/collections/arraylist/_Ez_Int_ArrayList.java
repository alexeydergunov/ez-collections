package ez.collections.arraylist;

import ez.collections._Ez_Int_Collection;
import ez.collections._Ez_Int_Iterator;
import ez.collections._Ez_Int_List;
import ez.collections._Ez_Int_Stack;
import ez.collections.misc.PrimitiveHashCalculator;

import java.util.Collection;
import java.util.NoSuchElementException;

public class _Ez_Int_ArrayList implements _Ez_Int_List, _Ez_Int_Stack {
    private static final int DEFAULT_CAPACITY = 10;
    private static final double ENLARGE_SCALE = 2.0;
    private static final int HASHCODE_INITIAL_VALUE = 0x811c9dc5;
    private static final int HASHCODE_MULTIPLIER = 0x01000193;

    private /*T*/int/*T*/[] array;
    private int size;

    public _Ez_Int_ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public _Ez_Int_ArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must be non-negative");
        }
        array = new /*T*/int/*T*/[capacity];
        size = 0;
    }

    public _Ez_Int_ArrayList(_Ez_Int_Collection collection) {
        size = collection.size();
        array = new /*T*/int/*T*/[size];
        int i = 0;
        for (_Ez_Int_Iterator iterator = collection.iterator(); iterator.hasNext(); ) {
            array[i++] = iterator.next();
        }
    }

    public _Ez_Int_ArrayList(/*T*/int/*T*/[] srcArray) {
        size = srcArray.length;
        array = new /*T*/int/*T*/[size];
        System.arraycopy(srcArray, 0, array, 0, size);
    }

    public _Ez_Int_ArrayList(Collection</*W*/Integer/*W*/> javaCollection) {
        size = javaCollection.size();
        array = new /*T*/int/*T*/[size];
        int i = 0;
        for (/*T*/int/*T*/ element : javaCollection) {
            array[i++] = element;
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
        for (int i = 0; i < size; i++) {
            if (array[i] == element) {
                return true;
            }
        }
        return false;
    }

    @Override
    public _Ez_Int_Iterator iterator() {
        return new _Ez_Int_ArrayListIterator();
    }

    @Override
    public /*T*/int/*T*/[] toArray() {
        /*T*/int/*T*/[] result = new /*T*/int/*T*/[size];
        System.arraycopy(array, 0, result, 0, size);
        return result;
    }

    @Override
    public boolean add(/*T*/int/*T*/ element) {
        if (size == array.length) {
            enlarge();
        }
        array[size++] = element;
        return true;
    }

    @Override
    public boolean remove(/*T*/int/*T*/ element) {
        for (int i = 0; i < size; i++) {
            if (array[i] == element) {
                //noinspection ManualArrayCopy
                for (int j = i + 1; j < size; j++) {
                    array[j - 1] = array[j];
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
    }

    @Override
    public /*T*/int/*T*/ get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range, size = " + size);
        }
        return array[index];
    }

    @Override
    public /*T*/int/*T*/ set(int index, /*T*/int/*T*/ element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range, size = " + size);
        }
        final /*T*/int/*T*/ oldElement = array[index];
        array[index] = element;
        return oldElement;
    }

    @Override
    public void insert(int index, /*T*/int/*T*/ element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range, size = " + size);
        }
        if (size == array.length) {
            enlarge();
        }
        //noinspection ManualArrayCopy
        for (int i = size; i > index; i--) {
            array[i] = array[i - 1];
        }
        array[index] = element;
        size++;
    }

    @Override
    public /*T*/int/*T*/ removeAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range, size = " + size);
        }
        final /*T*/int/*T*/ removedElement = array[index];
        //noinspection ManualArrayCopy
        for (int i = index + 1; i < size; i++) {
            array[i - 1] = array[i];
        }
        size--;
        return removedElement;
    }

    public void addLast(/*T*/int/*T*/ element) {
        if (size == array.length) {
            enlarge();
        }
        array[size++] = element;
    }

    @Override
    public /*T*/int/*T*/ getLast() {
        if (size == 0) {
            throw new NoSuchElementException("Trying to call getLast() on empty ArrayList");
        }
        return array[size - 1];
    }

    @Override
    public /*T*/int/*T*/ removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("Trying to call removeLast() on empty ArrayList");
        }
        return array[--size];
    }

    @Override
    public int indexOf(/*T*/int/*T*/ element) {
        for (int i = 0; i < size; i++) {
            if (array[i] == element) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(/*T*/int/*T*/ element) {
        for (int i = size - 1; i >= 0; i--) {
            if (array[i] == element) {
                return i;
            }
        }
        return -1;
    }

    private void enlarge() {
        int newSize = Math.max(size + 1, (int) (size * ENLARGE_SCALE));
        /*T*/int/*T*/[] newArray = new /*T*/int/*T*/[newSize];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        _Ez_Int_ArrayList that = (_Ez_Int_ArrayList) o;

        if (size != that.size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (array[i] != that.array[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = HASHCODE_INITIAL_VALUE;
        for (int i = 0; i < size; i++) {
            hash = (hash ^ PrimitiveHashCalculator.getHash(array[i])) * HASHCODE_MULTIPLIER;
        }
        return hash;
    }

    @Override
    public String toString() {
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

    private class _Ez_Int_ArrayListIterator implements _Ez_Int_Iterator {
        private int curIndex = 0;

        @Override
        public boolean hasNext() {
            return curIndex < size;
        }

        @Override
        public /*T*/int/*T*/ next() {
            if (curIndex == size) {
                throw new NoSuchElementException("Iterator doesn't have more elements");
            }
            return array[curIndex++];
        }
    }
}
