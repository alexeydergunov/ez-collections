package ez.collections.list;

import ez.collections.Ez$Int$Collection;
import ez.collections.Ez$Int$Iterator;

public class Ez$Int$ArrayList implements Ez$Int$List {
    private static final int DEFAULT_CAPACITY = 10;
    private static final double ENLARGE_SCALE = 2.0;
    private static final int HASHCODE_MULTIPLIER = 1664525;
    private static final int HASHCODE_INCREMENT = 1013904223;

    private int[] array;
    private int size;

    public Ez$Int$ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public Ez$Int$ArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must be non-negative");
        }
        array = new int[capacity];
        size = 0;
    }

    public Ez$Int$ArrayList(Ez$Int$Collection collection) {
        size = collection.size();
        array = new int[size];
        int i = 0;
        for (Ez$Int$Iterator iterator = collection.iterator(); iterator.hasNext(); ) {
            array[i++] = iterator.next();
        }
    }

    public Ez$Int$ArrayList(int[] srcArray) {
        size = srcArray.length;
        array = new int[size];
        System.arraycopy(srcArray, 0, array, 0, size);
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
    public boolean contains(int element) {
        for (int i = 0; i < size; i++) {
            if (array[i] == element) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Ez$Int$Iterator iterator() {
        return new Ez$Int$ArrayListIterator();
    }

    @Override
    public int[] toArray() {
        int[] result = new int[size];
        System.arraycopy(array, 0, result, 0, size);
        return result;
    }

    @Override
    public boolean add(int element) {
        if (size == array.length) {
            enlarge();
        }
        array[size++] = element;
        return true;
    }

    @Override
    public boolean remove(int element) {
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
    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range, size = " + size);
        }
        return array[index];
    }

    @Override
    public int set(int index, int element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range, size = " + size);
        }
        int oldElement = array[index];
        array[index] = element;
        return oldElement;
    }

    @Override
    public void insert(int index, int element) {
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
    public int removeAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range, size = " + size);
        }
        int removedElement = array[index];
        //noinspection ManualArrayCopy
        for (int i = index + 1; i < size; i++) {
            array[i - 1] = array[i];
        }
        size--;
        return removedElement;
    }

    public void pushBack(int element) {
        if (size == array.length) {
            enlarge();
        }
        array[size++] = element;
    }

    public int back() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Trying to call back() on empty ArrayList");
        }
        return array[size - 1];
    }

    public int popBack() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Trying to call popBack() on empty ArrayList");
        }
        return array[--size];
    }

    @Override
    public int indexOf(int element) {
        for (int i = 0; i < size; i++) {
            if (array[i] == element) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(int element) {
        for (int i = size - 1; i >= 0; i--) {
            if (array[i] == element) {
                return i;
            }
        }
        return -1;
    }

    private void enlarge() {
        int newSize = Math.max(size + 1, (int) (size * ENLARGE_SCALE));
        int[] newArray = new int[newSize];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ez$Int$ArrayList that = (Ez$Int$ArrayList) o;

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
        int hash = 0;
        for (int i = 0; i < size; i++) {
            hash = hash * HASHCODE_MULTIPLIER + HASHCODE_INCREMENT;
        }
        return hash;
    }

    private class Ez$Int$ArrayListIterator implements Ez$Int$Iterator {
        private int curIndex = 0;

        @Override
        public boolean hasNext() {
            return curIndex < size;
        }

        @Override
        public int next() {
            return array[curIndex++];
        }
    }
}
