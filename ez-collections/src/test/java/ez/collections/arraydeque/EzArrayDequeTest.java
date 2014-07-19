package ez.collections.arraydeque;

import ez.collections._Ez_Int_Iterator;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class EzArrayDequeTest {
    @Test
    public void testAdd() {
        _Ez_Int_ArrayDeque deque = new _Ez_Int_ArrayDeque(1);
        int[] array = new int[64];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        Assert.assertTrue(deque.isEmpty());
        for (int i = 0; i < array.length; i++) {
            Assert.assertTrue(deque.add(array[i]));
            Assert.assertEquals(deque.toArray(), Arrays.copyOfRange(array, 0, i + 1));
        }
    }

    @Test
    public void testAddGetRemoveLast() {
        _Ez_Int_ArrayDeque deque = new _Ez_Int_ArrayDeque(1);
        int[] array = new int[64];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        for (int i = 0; i < array.length; i++) {
            deque.addLast(array[i]);
            Assert.assertEquals(deque.toArray(), Arrays.copyOfRange(array, 0, i + 1));
        }
        for (int i = array.length - 1; i >= 0; i--) {
            Assert.assertEquals(deque.size(), i + 1);
            Assert.assertEquals(deque.getLast(), array[i]);
            Assert.assertEquals(deque.removeLast(), array[i]);
        }
        Assert.assertTrue(deque.isEmpty());
        try {
            deque.getLast();
        } catch (NoSuchElementException e) {
            // as expected
        }
        try {
            deque.removeLast();
        } catch (NoSuchElementException e) {
            // as expected
        }
    }

    @Test
    public void testAddGetRemoveFirst() {
        _Ez_Int_ArrayDeque deque = new _Ez_Int_ArrayDeque(1);
        int[] array = new int[64];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        for (int i = array.length - 1; i >= 0; i--) {
            deque.addFirst(array[i]);
            Assert.assertEquals(deque.toArray(), Arrays.copyOfRange(array, i, array.length));
        }
        for (int i = 0; i < array.length; i++) {
            Assert.assertEquals(deque.size(), array.length - i);
            Assert.assertEquals(deque.getFirst(), array[i]);
            Assert.assertEquals(deque.removeFirst(), array[i]);
        }
        Assert.assertTrue(deque.isEmpty());
        try {
            deque.getFirst();
        } catch (NoSuchElementException e) {
            // as expected
        }
        try {
            deque.removeFirst();
        } catch (NoSuchElementException e) {
            // as expected
        }
    }

    private _Ez_Int_ArrayDeque getPreparedArrayDeque(int capacity, int head) {
        Assert.assertEquals(Integer.bitCount(capacity), 1);
        _Ez_Int_ArrayDeque deque = new _Ez_Int_ArrayDeque(capacity);
        for (int i = 0; i < head; i++) {
            deque.addLast(-1);
            deque.removeFirst();
        }
        return deque;
    }

    @Test
    public void testToArray() {
        _Ez_Int_ArrayDeque deque = new _Ez_Int_ArrayDeque(1);
        Assert.assertEquals(deque.toArray(), new int[0]);
        for (int capacity = 2; capacity <= 16; capacity *= 2) {
            for (int head = 0; head < capacity; head++) {
                for (int size = 0; size < capacity; size++) {
                    deque = getPreparedArrayDeque(capacity, head);
                    int[] array = new int[size];
                    for (int i = 0; i < size; i++) {
                        array[i] = i;
                        deque.add(array[i]);
                    }
                    Assert.assertEquals(deque.toArray(), array);
                }
            }
        }
    }

    @Test
    public void testIterator() {
        _Ez_Int_ArrayDeque deque = new _Ez_Int_ArrayDeque(1);
        Assert.assertEquals(deque.toArray(), new int[0]);
        for (int capacity = 2; capacity <= 16; capacity *= 2) {
            for (int head = 0; head < capacity; head++) {
                for (int size = 0; size < capacity; size++) {
                    deque = getPreparedArrayDeque(capacity, head);
                    for (int i = 0; i < size; i++) {
                        deque.add(i);
                    }
                    _Ez_Int_Iterator it = deque.iterator();
                    for (int i = 0; i < size; i++) {
                        Assert.assertTrue(it.hasNext());
                        Assert.assertEquals(it.next(), i);
                    }
                    Assert.assertFalse(it.hasNext());
                    try {
                        it.next();
                    } catch (NoSuchElementException e) {
                        // as expected
                    }
                }
            }
        }
    }

    @Test
    public void testRemoveInsertContains() {
        for (int capacity = 2; capacity <= 16; capacity *= 2) {
            for (int head = 0; head < capacity; head++) {
                for (int size = 1; size < capacity; size++) {
                    for (int pos = 0; pos < size; pos++) {
                        _Ez_Int_ArrayDeque deque = getPreparedArrayDeque(capacity, head);
                        int[] array = new int[size];
                        int[] arrayAfterRemove = new int[size - 1];
                        Arrays.fill(array, -1);
                        Arrays.fill(arrayAfterRemove, -1);
                        array[pos] = 42;
                        for (int i = 0; i < size; i++) {
                            deque.add(array[i]);
                        }
                        Assert.assertEquals(deque.toArray(), array);
                        Assert.assertTrue(deque.contains(42));
                        Assert.assertTrue(deque.remove(42));
                        Assert.assertFalse(deque.contains(42));
                        Assert.assertEquals(deque.toArray(), arrayAfterRemove);
                        deque.insert(pos, 42);
                        Assert.assertEquals(deque.toArray(), array);
                        try {
                            deque.insert(-1, 666);
                        } catch (IndexOutOfBoundsException e) {
                            // as expected
                        }
                        try {
                            deque.insert(size + 1, 666);
                        } catch (IndexOutOfBoundsException e) {
                            // as expected
                        }
                    }
                }
            }
        }
    }

    @Test
    public void testRemoveAt() {
        for (int capacity = 2; capacity <= 16; capacity *= 2) {
            for (int head = 0; head < capacity; head++) {
                for (int size = 1; size < capacity; size++) {
                    for (int pos = 0; pos < size; pos++) {
                        _Ez_Int_ArrayDeque deque = getPreparedArrayDeque(capacity, head);
                        int[] array = new int[size];
                        int[] arrayAfterRemove = new int[size - 1];
                        Arrays.fill(array, -1);
                        Arrays.fill(arrayAfterRemove, -1);
                        array[pos] = 42;
                        for (int i = 0; i < size; i++) {
                            deque.add(array[i]);
                        }
                        Assert.assertEquals(deque.toArray(), array);
                        Assert.assertEquals(deque.removeAt(pos), 42);
                        Assert.assertEquals(deque.toArray(), arrayAfterRemove);
                        try {
                            deque.removeAt(-1);
                        } catch (IndexOutOfBoundsException e) {
                            // as expected
                        }
                        try {
                            deque.removeAt(size);
                        } catch (IndexOutOfBoundsException e) {
                            // as expected
                        }
                    }
                }
            }
        }
    }

    @Test
    public void testGetSet() {
        for (int capacity = 2; capacity <= 16; capacity *= 2) {
            for (int head = 0; head < capacity; head++) {
                for (int size = 1; size < capacity; size++) {
                    _Ez_Int_ArrayDeque deque = getPreparedArrayDeque(capacity, head);
                    for (int i = 0; i < size; i++) {
                        deque.add(-1);
                    }
                    for (int pos = 0; pos < size; pos++) {
                        Assert.assertEquals(deque.get(pos), -1);
                        Assert.assertEquals(deque.set(pos, 42), -1);
                        Assert.assertEquals(deque.get(pos), 42);
                        Assert.assertEquals(deque.set(pos, -1), 42);
                        Assert.assertEquals(deque.get(pos), -1);
                    }
                    try {
                        deque.get(-1);
                    } catch (IndexOutOfBoundsException e) {
                        // as expected
                    }
                    try {
                        deque.get(size);
                    } catch (IndexOutOfBoundsException e) {
                        // as expected
                    }
                    try {
                        deque.set(-1, 666);
                    } catch (IndexOutOfBoundsException e) {
                        // as expected
                    }
                    try {
                        deque.set(size, 666);
                    } catch (IndexOutOfBoundsException e) {
                        // as expected
                    }
                }
            }
        }
    }

    @Test
    public void testIndexOf() {
        for (int capacity = 2; capacity <= 16; capacity *= 2) {
            for (int head = 0; head < capacity; head++) {
                for (int size = 1; size < capacity; size++) {
                    _Ez_Int_ArrayDeque deque = getPreparedArrayDeque(capacity, head);
                    for (int i = 0; i < size; i++) {
                        deque.add(-1);
                    }
                    for (int pos1 = 0; pos1 < size; pos1++) {
                        for (int pos2 = pos1; pos2 < size; pos2++) {
                            deque.set(pos1, 42);
                            deque.set(pos2, 42);
                            Assert.assertEquals(deque.indexOf(42), pos1);
                            Assert.assertEquals(deque.lastIndexOf(42), pos2);
                            Assert.assertEquals(deque.indexOf(100500), -1);
                            Assert.assertEquals(deque.lastIndexOf(100500), -1);
                            deque.set(pos1, -1);
                            deque.set(pos2, -1);
                        }
                    }
                }
            }
        }
    }

    @Test
    public void testEqualsAndHashCode() {
        for (int capacity1 = 1; capacity1 <= 16; capacity1 *= 2) {
            for (int capacity2 = 1; capacity2 <= 16; capacity2 *= 2) {
                for (int head1 = 0; head1 < capacity1; head1++) {
                    for (int head2 = 0; head2 < capacity2; head2++) {
                        _Ez_Int_ArrayDeque deque1 = getPreparedArrayDeque(capacity1, head1);
                        _Ez_Int_ArrayDeque deque2 = getPreparedArrayDeque(capacity2, head2);
                        Assert.assertEquals(deque1, deque2);
                        Assert.assertEquals(deque1.hashCode(), deque2.hashCode());
                        for (int size = 0; size < Math.min(capacity1, capacity2); size++) {
                            deque1.add(size);
                            deque2.add(size);
                            Assert.assertEquals(deque1, deque2);
                            Assert.assertEquals(deque1.hashCode(), deque2.hashCode());
                        }
                    }
                }
            }
        }
    }

    @Test
    public void testToString() {
        Assert.assertEquals(new _Ez_Int_ArrayDeque(new int[0]).toString(), "[]");
        Assert.assertEquals(new _Ez_Int_ArrayDeque(new int[] {1}).toString(), "[1]");
        Assert.assertEquals(new _Ez_Int_ArrayDeque(new int[] {1, 2}).toString(), "[1, 2]");
        Assert.assertEquals(new _Ez_Int_ArrayDeque(new int[] {1, 2, 3}).toString(), "[1, 2, 3]");
        Assert.assertEquals(new _Ez_Int_ArrayDeque(new int[] {1, 2, 3, 4}).toString(), "[1, 2, 3, 4]");
        Assert.assertEquals(new _Ez_Int_ArrayDeque(new int[] {1, 2, 3, 4, 5}).toString(), "[1, 2, 3, 4, 5]");
        Assert.assertEquals(new _Ez_Int_ArrayDeque(new int[] {1, 2, 3, 4, 5, 6}).toString(), "[1, 2, 3, 4, 5, 6]");
    }
}
