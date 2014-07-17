package ez.collections.heap;

import ez.collections._Ez_Int_Iterator;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;

public class EzHeapTest {
    private void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private void shuffle(int[] a, Random rnd) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int j = i + rnd.nextInt(n - i);
            swap(a, i, j);
        }
    }

    private void validateHeapArray(_Ez_Int_Heap heap) {
        int[] heapArray = heap.toArray();
        for (int i = 1; i < heapArray.length; i++) {
            Assert.assertTrue(heapArray[(i - 1) >>> 1] <= heapArray[i]);
        }
    }

    @Test
    public void testAdd() {
        Random rnd = new Random(322);
        for (int it = 0; it < 10000; it++) {
            int length = 1 + rnd.nextInt(100);
            int[] array = new int[length];
            if (rnd.nextBoolean()) {
                for (int i = 0; i < length; i++) {
                    array[i] = i + 1;
                }
            } else {
                for (int i = 0; i < length; i++) {
                    array[i] = 1 + rnd.nextInt(length);
                }
            }
            shuffle(array, rnd);
            _Ez_Int_Heap heap = new _Ez_Int_Heap();
            for (int x : array) {
                Assert.assertTrue(heap.add(x));
            }
            validateHeapArray(heap);
        }
    }

    @Test
    public void testMakeHeap() {
        Random rnd = new Random(322);
        for (int it = 0; it < 8000; it++) {
            int length = 1 + rnd.nextInt(125);
            int[] array = new int[length];
            if (rnd.nextBoolean()) {
                for (int i = 0; i < length; i++) {
                    array[i] = i + 1;
                }
            } else {
                for (int i = 0; i < length; i++) {
                    array[i] = 1 + rnd.nextInt(length);
                }
            }
            shuffle(array, rnd);
            _Ez_Int_Heap heap = new _Ez_Int_Heap(array);
            validateHeapArray(heap);
        }
    }

    @Test
    public void testRemove() {
        Random rnd = new Random(322);
        for (int it = 0; it < 12500; it++) {
            int length = 1 + rnd.nextInt(80);
            int[] array = new int[length];
            if (rnd.nextBoolean()) {
                for (int i = 0; i < length; i++) {
                    array[i] = i + 1;
                }
            } else {
                for (int i = 0; i < length; i++) {
                    array[i] = 1 + rnd.nextInt(length);
                }
            }
            shuffle(array, rnd);
            _Ez_Int_Heap heap = new _Ez_Int_Heap(array);
            validateHeapArray(heap);
            shuffle(array, rnd);
            for (int x : array) {
                Assert.assertTrue(heap.remove(x));
                validateHeapArray(heap);
            }
            Assert.assertTrue(heap.isEmpty());
        }
    }

    @Test
    public void testContains() {
        Random rnd = new Random(322);
        for (int it = 0; it < 10100; it++) {
            int length = 1 + rnd.nextInt(99);
            int[] array = new int[length];
            for (int i = 0; i < length; i++) {
                array[i] = i + 1;
            }
            shuffle(array, rnd);
            _Ez_Int_Heap heap = new _Ez_Int_Heap(array);
            validateHeapArray(heap);
            shuffle(array, rnd);
            for (int x : array) {
                Assert.assertTrue(heap.contains(x));
                Assert.assertTrue(heap.remove(x));
                validateHeapArray(heap);
                Assert.assertFalse(heap.contains(x));
                Assert.assertFalse(heap.remove(x));
                validateHeapArray(heap);
            }
            Assert.assertTrue(heap.isEmpty());
        }
    }

    @Test
    public void testGetRemoveFirst() {
        Random rnd = new Random(322);
        for (int it = 0; it < 9090; it++) {
            int length = 1 + rnd.nextInt(110);
            int[] array = new int[length];
            if (rnd.nextBoolean()) {
                for (int i = 0; i < length; i++) {
                    array[i] = i + 1;
                }
            } else {
                for (int i = 0; i < length; i++) {
                    array[i] = 1 + rnd.nextInt(length);
                }
            }
            shuffle(array, rnd);
            _Ez_Int_Heap heap = new _Ez_Int_Heap(array);
            validateHeapArray(heap);
            Arrays.sort(array);
            for (int x : array) {
                Assert.assertEquals(heap.getFirst(), x);
                Assert.assertEquals(heap.removeFirst(), x);
                validateHeapArray(heap);
            }
            Assert.assertTrue(heap.isEmpty());
            try {
                heap.getFirst();
            } catch (NoSuchElementException e) {
                // as expected
            }
            try {
                heap.removeFirst();
            } catch (NoSuchElementException e) {
                // as expected
            }
        }
    }

    @Test
    public void testIterator() {
        _Ez_Int_Heap heap = new _Ez_Int_Heap();
        for (int i = 0; i < 42; i++) {
            Assert.assertTrue(heap.add(i));
        }
        Assert.assertEquals(heap.size(), 42);
        int[] heapArray = heap.toArray();
        _Ez_Int_Iterator it = heap.iterator();
        for (int i = 0; i < 42; i++) {
            Assert.assertTrue(it.hasNext());
            Assert.assertEquals(it.next(), heapArray[i]);
        }
        Assert.assertFalse(it.hasNext());
        try {
            it.next();
        } catch (NoSuchElementException e) {
            // as expected
        }
    }

    @Test
    public void testToString() {
        Assert.assertEquals(new _Ez_Int_Heap(new int[0]).toString(), "[]");
        Assert.assertEquals(new _Ez_Int_Heap(new int[] {1}).toString(), "[1]");
        Assert.assertEquals(new _Ez_Int_Heap(new int[] {2, 1}).toString(), "[1, 2]");
        Assert.assertEquals(new _Ez_Int_Heap(new int[] {3, 1, 2}).toString(), "[1, 2, 3]");
        Assert.assertEquals(new _Ez_Int_Heap(new int[] {4, 2, 3, 1}).toString(), "[1, 2, 3, 4]");
        Assert.assertEquals(new _Ez_Int_Heap(new int[] {2, 1, 2, 1, 2}).toString(), "[1, 1, 2, 2, 2]");
    }

    @Test
    public void testEqualsAndHashCode() {
        _Ez_Int_Heap[] heaps = new _Ez_Int_Heap[] {
                new _Ez_Int_Heap(new int[] {1, 2, 3}),
                new _Ez_Int_Heap(new int[] {1, 3, 2}),
                new _Ez_Int_Heap(new int[] {2, 1, 3}),
                new _Ez_Int_Heap(new int[] {2, 3, 1}),
                new _Ez_Int_Heap(new int[] {3, 1, 2}),
                new _Ez_Int_Heap(new int[] {3, 2, 1}),
        };
        for (_Ez_Int_Heap heap1 : heaps) {
            for (_Ez_Int_Heap heap2 : heaps) {
                Assert.assertEquals(heap1, heap2);
                Assert.assertEquals(heap1.hashCode(), heap2.hashCode());
            }
        }
    }
}
