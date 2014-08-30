package ez.collections.treeset;

import ez.collections._Ez_Int_Collection;
import ez.collections._Ez_Int_Iterator;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

public class EzTreeSetTest {
    private void testPermutation(int[] array) {
        int n = array.length;
        _Ez_Int_TreeSet set = new _Ez_Int_TreeSet(0);
        for (int it = 0; it < 3; it++) {
            Assert.assertTrue(set.isEmpty());
            for (int i = 0; i < n; i++) {
                Assert.assertTrue(set.add(array[i]));
                Assert.assertEquals(set.size(), i + 1);
                Assert.assertFalse(set.isEmpty());
            }
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0; i < n; i++) {
                Assert.assertFalse(set.add(array[i]));
                Assert.assertEquals(set.size(), n);
                Assert.assertFalse(set.isEmpty());
            }
            for (int i = -1; i <= n; i++) {
                Assert.assertEquals(set.contains(i), 0 <= i && i < n);
                Assert.assertEquals(set.size(), n);
                Assert.assertFalse(set.isEmpty());
            }
            Assert.assertEquals(set.getFirst(), 0);
            Assert.assertTrue(set.wasCorrectValueReturned());
            Assert.assertEquals(set.getLast(), n - 1);
            Assert.assertTrue(set.wasCorrectValueReturned());
            for (int i = -2; i <= n + 1; i++) {
                int lower = set.lower(i);
                if (i > 0) {
                    Assert.assertEquals(lower, Math.min(i - 1, n - 1));
                    Assert.assertTrue(set.wasCorrectValueReturned());
                } else {
                    Assert.assertFalse(set.wasCorrectValueReturned());
                }
                int higher = set.higher(i);
                if (i < n - 1) {
                    Assert.assertEquals(higher, Math.max(i + 1, 0));
                    Assert.assertTrue(set.wasCorrectValueReturned());
                } else {
                    Assert.assertFalse(set.wasCorrectValueReturned());
                }
                int floor = set.floor(i);
                if (i >= 0) {
                    Assert.assertEquals(floor, Math.min(i, n - 1));
                    Assert.assertTrue(set.wasCorrectValueReturned());
                } else {
                    Assert.assertFalse(set.wasCorrectValueReturned());
                }
                int ceiling = set.ceiling(i);
                if (i < n) {
                    Assert.assertEquals(ceiling, Math.max(i, 0));
                    Assert.assertTrue(set.wasCorrectValueReturned());
                } else {
                    Assert.assertFalse(set.wasCorrectValueReturned());
                }
            }
            int removed = 0;
            for (int i = -1; i <= n; i++) {
                boolean contains = (0 <= i && i < n);
                int x = contains ? array[i] : i;
                if (x == 0) {
                    Assert.assertEquals(set.removeFirst(), x);
                    Assert.assertTrue(set.wasCorrectValueReturned());
                } else if (x == n - 1) {
                    Assert.assertEquals(set.removeLast(), x);
                    Assert.assertTrue(set.wasCorrectValueReturned());
                } else {
                    Assert.assertEquals(set.remove(x), contains);
                }
                if (contains) {
                    removed++;
                }
                Assert.assertEquals(set.size(), n - removed);
            }
            set.clear();
        }
    }

    private void genPermutations(int[] permutation, int left, int length) {
        if (left >= length - 1) {
            testPermutation(permutation);
            return;
        }
        for (int i = left; i < length; i++) {
            swap(permutation, i, left);
            genPermutations(permutation, left + 1, length);
            swap(permutation, i, left);
        }
    }

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

    @Test
    public void testAllSmallPermutations() {
        for (int length = 1; length <= 8; length++) {
            int[] a = new int[length];
            for (int i = 0; i < length; i++) {
                a[i] = i;
            }
            genPermutations(a, 0, length);
        }
    }

    @Test
    public void testManyPermutationsLength100() {
        Random rnd = new Random(322);
        for (int it = 0; it < 10000; it++) {
            int length = 9 + rnd.nextInt(92);
            int[] a = new int[length];
            for (int i = 0; i < length; i++) {
                a[i] = i;
            }
            shuffle(a, rnd);
            testPermutation(a);
        }
    }

    @Test
    public void testFewPermutationsLength10000() {
        Random rnd = new Random(228);
        for (int it = 0; it < 50; it++) {
            int length = 9 + rnd.nextInt(9992);
            int[] a = new int[length];
            for (int i = 0; i < length; i++) {
                a[i] = i;
            }
            shuffle(a, rnd);
            testPermutation(a);
        }
    }

    @Test
    public void testOperationsOnEmptySet() {
        _Ez_Int_TreeSet set = new _Ez_Int_TreeSet();
        Assert.assertTrue(set.isEmpty());
        Assert.assertEquals(set.size(), 0);
        set.getFirst();
        Assert.assertFalse(set.wasCorrectValueReturned());
        set.getLast();
        Assert.assertFalse(set.wasCorrectValueReturned());
        set.removeFirst();
        Assert.assertFalse(set.wasCorrectValueReturned());
        set.removeLast();
        Assert.assertFalse(set.wasCorrectValueReturned());
        for (int x : new int[] {Integer.MIN_VALUE, 0, Integer.MAX_VALUE}) {
            Assert.assertFalse(set.contains(x));
            Assert.assertFalse(set.remove(x));
            set.ceiling(x);
            Assert.assertFalse(set.wasCorrectValueReturned());
            set.floor(x);
            Assert.assertFalse(set.wasCorrectValueReturned());
            set.higher(x);
            Assert.assertFalse(set.wasCorrectValueReturned());
            set.lower(x);
            Assert.assertFalse(set.wasCorrectValueReturned());
        }
    }

    @Test
    public void testAddRemove() {
        _Ez_Int_TreeSet set = new _Ez_Int_TreeSet();
        for (int it = 0; it < 3; it++) {
            Assert.assertEquals(set.toArray(), new int[0]);
            Assert.assertTrue(set.add(1));
            Assert.assertEquals(set.toArray(), new int[]{1});
            Assert.assertTrue(set.add(2));
            Assert.assertEquals(set.toArray(), new int[]{1, 2});
            Assert.assertTrue(set.add(3));
            Assert.assertEquals(set.toArray(), new int[]{1, 2, 3});
            Assert.assertFalse(set.add(3));
            Assert.assertEquals(set.toArray(), new int[]{1, 2, 3});
            Assert.assertTrue(set.remove(2));
            Assert.assertEquals(set.toArray(), new int[]{1, 3});
            Assert.assertTrue(set.remove(3));
            Assert.assertEquals(set.toArray(), new int[]{1});
            Assert.assertFalse(set.remove(2));
            Assert.assertEquals(set.toArray(), new int[]{1});
            Assert.assertTrue(set.remove(1));
            Assert.assertEquals(set.toArray(), new int[0]);
            Assert.assertFalse(set.remove(1));
            Assert.assertEquals(set.toArray(), new int[0]);
        }
    }

    @Test
    public void testContainsFirstLast() {
        int[] array = {1, 4, 5, 8, 10, 12, 13};
        _Ez_Int_TreeSet set = new _Ez_Int_TreeSet(array);
        for (int i = 0; i < 15; i++) {
            int bsIndex = Arrays.binarySearch(array, i);
            Assert.assertEquals(set.contains(i), bsIndex >= 0);
        }
        Assert.assertEquals(set.getFirst(), 1);
        Assert.assertEquals(set.getLast(), 13);
        Assert.assertEquals(set.removeFirst(), 1);
        Assert.assertTrue(set.wasCorrectValueReturned());
        Assert.assertEquals(set.removeLast(), 13);
        Assert.assertTrue(set.wasCorrectValueReturned());
    }

    @Test
    public void testLowerHigher() {
        int[] array = {1, 4, 5, 8, 10, 12, 13};
        _Ez_Int_TreeSet set = new _Ez_Int_TreeSet(array);
        TreeSet<Integer> javaSet = new TreeSet<Integer>();
        for (int x : array) {
            javaSet.add(x);
        }
        for (int i = 0; i < 15; i++) {
            int lower = set.lower(i);
            Integer javaLower = javaSet.lower(i);
            if (javaLower == null) {
                Assert.assertFalse(set.wasCorrectValueReturned());
            } else {
                Assert.assertTrue(set.wasCorrectValueReturned());
                Assert.assertEquals(lower, javaLower.intValue());
            }
        }
        for (int i = 0; i < 15; i++) {
            int higher = set.higher(i);
            Integer javaHigher = javaSet.higher(i);
            if (javaHigher == null) {
                Assert.assertFalse(set.wasCorrectValueReturned());
            } else {
                Assert.assertTrue(set.wasCorrectValueReturned());
                Assert.assertEquals(higher, javaHigher.intValue());
            }
        }
    }

    @Test
    public void testFloorCeiling() {
        int[] array = {1, 4, 5, 8, 10, 12, 13};
        _Ez_Int_TreeSet set = new _Ez_Int_TreeSet(array);
        TreeSet<Integer> javaSet = new TreeSet<Integer>();
        for (int x : array) {
            javaSet.add(x);
        }
        for (int i = 0; i < 15; i++) {
            int floor = set.floor(i);
            Integer javaFloor = javaSet.floor(i);
            if (javaFloor == null) {
                Assert.assertFalse(set.wasCorrectValueReturned());
            } else {
                Assert.assertTrue(set.wasCorrectValueReturned());
                Assert.assertEquals(floor, javaFloor.intValue());
            }
        }
        for (int i = 0; i < 15; i++) {
            int ceiling = set.ceiling(i);
            Integer javaCeiling = javaSet.ceiling(i);
            if (javaCeiling == null) {
                Assert.assertFalse(set.wasCorrectValueReturned());
            } else {
                Assert.assertTrue(set.wasCorrectValueReturned());
                Assert.assertEquals(ceiling, javaCeiling.intValue());
            }
        }
    }

    @Test
    public void testConstructors() {
        int[] array = new int[] {2, 4, 5};
        Collection<Integer> javaCollection = new ArrayList<Integer>();
        _Ez_Int_Collection ezCollection = new _Ez_Int_TreeSet();
        for (int x : array) {
            javaCollection.add(x);
            ezCollection.add(x);
        }
        _Ez_Int_TreeSet[] sets = new _Ez_Int_TreeSet[] {
                new _Ez_Int_TreeSet(array),
                new _Ez_Int_TreeSet(ezCollection),
                new _Ez_Int_TreeSet(javaCollection)
        };
        for (_Ez_Int_TreeSet set : sets) {
            Assert.assertEquals(set.size(), 3);
            for (int x : array) {
                Assert.assertTrue(set.contains(x));
            }
            Assert.assertEquals(set.toArray(), array);
        }
    }

    @Test
    public void testIterator() {
        _Ez_Int_TreeSet set = new _Ez_Int_TreeSet();
        for (int i = 0; i < 1000; i++) {
            set.add(i);
        }
        _Ez_Int_Iterator it = set.iterator();
        for (int i = 0; i < 1000; i++) {
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

    @Test
    public void testToArray() {
        int[] arr = new int[100];
        for (int i = 0; i < 100; i++) {
            arr[i] = i;
        }
        for (int len = 0; len <= 100; len++) {
            _Ez_Int_TreeSet set = new _Ez_Int_TreeSet();
            for (int i = 0; i < len; i++) {
                set.add(arr[i]);
            }
            Assert.assertEquals(set.toArray(), Arrays.copyOfRange(arr, 0, len));
        }
    }

    @Test
    public void testEqualsHashcode() {
        for (int mask1 = 0; mask1 < 32; mask1++) {
            for (int mask2 = 0; mask2 < 32; mask2++) {
                _Ez_Int_TreeSet set1 = new _Ez_Int_TreeSet();
                _Ez_Int_TreeSet set2 = new _Ez_Int_TreeSet();
                for (int i = 0; i < 5; i++) {
                    if ((mask1 & (1 << i)) != 0) {
                        set1.add(i);
                    }
                    if ((mask2 & (1 << i)) != 0) {
                        set2.add(i);
                    }
                }
                int hashCode1 = set1.hashCode();
                int hashCode2 = set2.hashCode();
                if (mask1 == mask2) {
                    Assert.assertTrue(set1.equals(set2));
                    Assert.assertTrue(set2.equals(set1));
                    Assert.assertTrue(hashCode1 == hashCode2);
                } else {
                    Assert.assertFalse(set1.equals(set2));
                    Assert.assertFalse(set2.equals(set1));
                }
            }
        }
    }

    @Test
    public void testToString() {
        Assert.assertEquals(new _Ez_Int_TreeSet(new int[0]).toString(), "[]");
        Assert.assertEquals(new _Ez_Int_TreeSet(new int[] {1}).toString(), "[1]");
        Assert.assertEquals(new _Ez_Int_TreeSet(new int[] {1, 1}).toString(), "[1]");
        Assert.assertEquals(new _Ez_Int_TreeSet(new int[] {1, 1, 1}).toString(), "[1]");
        Assert.assertEquals(new _Ez_Int_TreeSet(new int[] {1, 2}).toString(), "[1, 2]");
        Assert.assertEquals(new _Ez_Int_TreeSet(new int[] {1, 1, 2}).toString(), "[1, 2]");
        Assert.assertEquals(new _Ez_Int_TreeSet(new int[] {1, 2, 1}).toString(), "[1, 2]");
        Assert.assertEquals(new _Ez_Int_TreeSet(new int[] {1, 2, 2}).toString(), "[1, 2]");
        Assert.assertEquals(new _Ez_Int_TreeSet(new int[] {2, 1, 2}).toString(), "[1, 2]");
        Assert.assertEquals(new _Ez_Int_TreeSet(new int[] {1, 2, 3}).toString(), "[1, 2, 3]");
    }
}
