package ez.collections.hashset;

import ez.collections._Ez_Int_Collection;
import ez.collections._Ez_Int_Iterator;
import ez.collections._Ez_Int_Set;
import ez.collections.misc.PrimitiveHashCalculator;
import ez.collections.sort._Ez_Int_Sort;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Random;

public class _Ez_Int_HashSet implements _Ez_Int_Set {
    private static final int DEFAULT_CAPACITY = 8;
    private static final double ENLARGE_NON_FREE_CELLS_RATIO = 0.5;
    private static final double REBUILD_REMOVED_CELLS_RATIO = 0.25;
    private static final int HASHCODE_INITIAL_VALUE = 0x811c9dc5;
    private static final int HASHCODE_MULTIPLIER = 0x01000193;

    private static final byte FREE = 0;
    private static final byte REMOVED = 1;
    private static final byte FILLED = 2;

    private static final Random rnd = new Random();
    private static final int POS_RANDOM_SHIFT_1;
    private static final int POS_RANDOM_SHIFT_2;
    private static final int STEP_RANDOM_SHIFT_1;
    private static final int STEP_RANDOM_SHIFT_2;

    static {
        POS_RANDOM_SHIFT_1 = rnd.nextInt(10) + 11;
        POS_RANDOM_SHIFT_2 = rnd.nextInt(10) + 21;
        STEP_RANDOM_SHIFT_1 = rnd.nextInt(10) + 11;
        STEP_RANDOM_SHIFT_2 = rnd.nextInt(10) + 21;
    }

    private /*T*/int/*T*/[] table;
    private byte[] status;
    private int size;
    private int removedCount;
    private int mask;
    private final int hashSeed;

    public _Ez_Int_HashSet() {
        this(DEFAULT_CAPACITY);
    }

    public _Ez_Int_HashSet(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must be non-negative");
        }
        capacity = Math.max(4, (int) (capacity / ENLARGE_NON_FREE_CELLS_RATIO));
        if ((capacity & (capacity - 1)) != 0) {
            capacity = Integer.highestOneBit(capacity) << 1;
        }
        // Capacity is a power of 2 now
        table = new /*T*/int/*T*/[capacity];
        status = new byte[capacity];
        size = 0;
        removedCount = 0;
        mask = capacity - 1;
        hashSeed = rnd.nextInt();
    }

    public _Ez_Int_HashSet(_Ez_Int_Collection collection) {
        this(collection.size());
        for (_Ez_Int_Iterator iterator = collection.iterator(); iterator.hasNext(); ) {
            add(iterator.next());
        }
    }

    public _Ez_Int_HashSet(/*T*/int/*T*/[] srcArray) {
        this(srcArray.length);
        for (/*T*/int/*T*/ element : srcArray) {
            add(element);
        }
    }

    public _Ez_Int_HashSet(Collection</*W*/Integer/*W*/> javaCollection) {
        this(javaCollection.size());
        for (/*T*/int/*T*/ element : javaCollection) {
            add(element);
        }
    }

    private int getStartPos(int h) {
        h ^= hashSeed;
        h ^= (h >>> POS_RANDOM_SHIFT_1) ^ (h >>> POS_RANDOM_SHIFT_2);
        return h & mask;
    }

    private int getStep(int h) {
        h ^= hashSeed;
        h ^= (h >>> STEP_RANDOM_SHIFT_1) ^ (h >>> STEP_RANDOM_SHIFT_2);
        return ((h << 1) | 1) & mask;
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
        final int elementHash = PrimitiveHashCalculator.getHash(element);
        int pos = getStartPos(elementHash);
        final int step = getStep(elementHash);
        for (; status[pos] != FREE; pos = (pos + step) & mask) {
            if (status[pos] == FILLED && table[pos] == element) {
                return true;
            }
        }
        return false;
    }

    @Override
    public _Ez_Int_Iterator iterator() {
        return new _Ez_Int_HashSetIterator();
    }

    @Override
    public /*T*/int/*T*/[] toArray() {
        /*T*/int/*T*/[] result = new /*T*/int/*T*/[size];
        for (int i = 0, j = 0; i < table.length; i++) {
            if (status[i] == FILLED) {
                result[j++] = table[i];
            }
        }
        return result;
    }

    @Override
    public boolean add(/*T*/int/*T*/ element) {
        final int elementHash = PrimitiveHashCalculator.getHash(element);
        int pos = getStartPos(elementHash);
        final int step = getStep(elementHash);
        for (; status[pos] == FILLED; pos = (pos + step) & mask) {
            if (table[pos] == element) {
                return false;
            }
        }
        if (status[pos] == FREE) {
            status[pos] = FILLED;
            table[pos] = element;
            size++;
            if (size + removedCount > table.length * ENLARGE_NON_FREE_CELLS_RATIO) {
                rebuild(table.length * 2);
            }
            return true;
        }
        final int removedPos = pos;
        for (pos = (pos + step) & mask; status[pos] != FREE; pos = (pos + step) & mask) {
            if (status[pos] == FILLED && table[pos] == element) {
                return false;
            }
        }
        status[removedPos] = FILLED;
        table[removedPos] = element;
        size++;
        removedCount--;
        return true;
    }

    @Override
    public boolean remove(/*T*/int/*T*/ element) {
        final int elementHash = PrimitiveHashCalculator.getHash(element);
        int pos = getStartPos(elementHash);
        final int step = getStep(elementHash);
        for (; status[pos] != FREE; pos = (pos + step) & mask) {
            if (status[pos] == FILLED && table[pos] == element) {
                status[pos] = REMOVED;
                size--;
                removedCount++;
                if (removedCount > table.length * REBUILD_REMOVED_CELLS_RATIO) {
                    rebuild(table.length);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        Arrays.fill(status, FREE);
        size = 0;
        removedCount = 0;
    }

    private void rebuild(int newCapacity) {
        /*T*/int/*T*/[] oldTable = table;
        byte[] oldStatus = status;
        table = new /*T*/int/*T*/[newCapacity];
        status = new byte[newCapacity];
        size = 0;
        removedCount = 0;
        mask = newCapacity - 1;
        for (int i = 0; i < oldTable.length; i++) {
            if (oldStatus[i] == FILLED) {
                add(oldTable[i]);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        _Ez_Int_HashSet that = (_Ez_Int_HashSet) o;

        if (size != that.size) {
            return false;
        }
        for (int i = 0; i < table.length; i++) {
            if (status[i] == FILLED) {
                if (!that.contains(table[i])) {
                    return false;
                }
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
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < table.length; i++) {
            if (status[i] == FILLED) {
                if (sb.length() > 1) {
                    sb.append(", ");
                }
                sb.append(table[i]);
            }
        }
        sb.append(']');
        return sb.toString();
    }

    private class _Ez_Int_HashSetIterator implements _Ez_Int_Iterator {
        private int curIndex = 0;

        @Override
        public boolean hasNext() {
            while (curIndex < table.length && status[curIndex] != FILLED) {
                curIndex++;
            }
            return curIndex < table.length;
        }

        @Override
        public /*T*/int/*T*/ next() {
            while (curIndex < table.length && status[curIndex] != FILLED) {
                curIndex++;
            }
            if (curIndex == table.length) {
                throw new NoSuchElementException("Iterator doesn't have more elements");
            }
            return table[curIndex++];
        }
    }
}
