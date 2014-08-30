package ez.collections.hashset;

import ez.collections._Ez_Int_Iterator;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;

public class EzHashSetTest {
    @Test
    public void testAdd() {
        Random rnd = new Random(322);
        _Ez_Int_HashSet set = new _Ez_Int_HashSet(4);
        boolean[] contains = new boolean[50000];
        int size = 0;
        for (int i = 0; i < 200000; i++) {
            int x = rnd.nextInt(50000);
            if (contains[x]) {
                Assert.assertFalse(set.add(x));
            } else {
                Assert.assertTrue(set.add(x));
                contains[x] = true;
                size++;
            }
            Assert.assertEquals(set.size(), size);
        }
    }

    @Test
    public void testRemove() {
        Random rnd = new Random(3222);
        _Ez_Int_HashSet set = new _Ez_Int_HashSet(4);
        Assert.assertTrue(set.isEmpty());
        for (int i = 0; i < 50000; i++) {
            Assert.assertTrue(set.add(i));
        }
        boolean[] contains = new boolean[50000];
        Arrays.fill(contains, true);
        int size = 50000;
        for (int i = 0; i < 200000; i++) {
            int x = rnd.nextInt(50000);
            if (contains[x]) {
                Assert.assertTrue(set.remove(x));
                contains[x] = false;
                size--;
            } else {
                Assert.assertFalse(set.remove(x));
            }
            Assert.assertEquals(set.size(), size);
            if (size == 0) {
                Assert.assertTrue(set.isEmpty());
            } else {
                Assert.assertFalse(set.isEmpty());
            }
        }
        for (int i = 0; i < 50000; i++) {
            if (contains[i]) {
                Assert.assertTrue(set.remove(i));
                contains[i] = false;
                size--;
            } else {
                Assert.assertFalse(set.remove(i));
            }
            Assert.assertEquals(set.size(), size);
            if (size == 0) {
                Assert.assertTrue(set.isEmpty());
            } else {
                Assert.assertFalse(set.isEmpty());
            }
        }
        Assert.assertTrue(set.isEmpty());
    }

    @Test
    public void testAddRemoveContainsBig() {
        Random rnd = new Random(32222);
        _Ez_Int_HashSet set = new _Ez_Int_HashSet(4);
        boolean[] contains = new boolean[10000];
        int size = 0;
        for (int i = 0; i < 200000; i++) {
            int x = rnd.nextInt(10000);
            if (rnd.nextBoolean()) {
                // add
                if (contains[x]) {
                    Assert.assertFalse(set.add(x));
                } else {
                    Assert.assertTrue(set.add(x));
                    contains[x] = true;
                    size++;
                }
            } else {
                // remove
                if (contains[x]) {
                    Assert.assertTrue(set.remove(x));
                    contains[x] = false;
                    size--;
                } else {
                    Assert.assertFalse(set.remove(x));
                }
            }
            Assert.assertEquals(set.contains(x), contains[x]);
            Assert.assertEquals(set.size(), size);
        }
    }

    @Test
    public void testAddRemoveContainsOnFilledSet() {
        Random rnd = new Random(322222);
        _Ez_Int_HashSet set = new _Ez_Int_HashSet(4);
        boolean[] contains = new boolean[10000];
        int size = 0;
        // add half of numbers
        while (size < 5000) {
            int x = rnd.nextInt(10000);
            if (contains[x]) {
                Assert.assertFalse(set.add(x));
            } else {
                Assert.assertTrue(set.add(x));
                contains[x] = true;
                size++;
            }
            Assert.assertEquals(set.size(), size);
        }
        for (int i = 0; i < 200000; i++) {
            int x = rnd.nextInt(10000);
            if (rnd.nextBoolean()) {
                // add
                if (contains[x]) {
                    Assert.assertFalse(set.add(x));
                } else {
                    Assert.assertTrue(set.add(x));
                    contains[x] = true;
                    size++;
                }
            } else {
                // remove
                if (contains[x]) {
                    Assert.assertTrue(set.remove(x));
                    contains[x] = false;
                    size--;
                } else {
                    Assert.assertFalse(set.remove(x));
                }
            }
            Assert.assertEquals(set.contains(x), contains[x]);
            Assert.assertEquals(set.size(), size);
        }
    }

    @Test
    public void testAddRemoveContainsSmall() {
        Random rnd = new Random(3222222);
        _Ez_Int_HashSet set = new _Ez_Int_HashSet(4);
        boolean[] contains = new boolean[50];
        int size = 0;
        for (int i = 0; i < 200000; i++) {
            int x = rnd.nextInt(50);
            if (rnd.nextBoolean()) {
                // add
                if (contains[x]) {
                    Assert.assertFalse(set.add(x));
                } else {
                    Assert.assertTrue(set.add(x));
                    contains[x] = true;
                    size++;
                }
            } else {
                // remove
                if (contains[x]) {
                    Assert.assertTrue(set.remove(x));
                    contains[x] = false;
                    size--;
                } else {
                    Assert.assertFalse(set.remove(x));
                }
            }
            Assert.assertEquals(set.contains(x), contains[x]);
            Assert.assertEquals(set.size(), size);
        }
    }

    @Test
    public void testClear() {
        _Ez_Int_HashSet set = new _Ez_Int_HashSet(4);
        Assert.assertTrue(set.isEmpty());
        for (int pow = 0; pow < 10; pow++) {
            for (int i = 0; i < (1 << pow); i++) {
                Assert.assertTrue(set.add(i));
            }
            Assert.assertFalse(set.isEmpty());
            set.clear();
            Assert.assertTrue(set.isEmpty());
        }
        for (int pow = 9; pow >= 0; pow--) {
            for (int i = 0; i < (1 << pow); i++) {
                Assert.assertTrue(set.add(i));
            }
            Assert.assertFalse(set.isEmpty());
            set.clear();
            Assert.assertTrue(set.isEmpty());
        }
    }

    @Test
    public void testIterator() {
        _Ez_Int_HashSet set = new _Ez_Int_HashSet(4);
        for (int i = 0; i < 1000; i++) {
            set.add(i);
        }
        boolean[] was = new boolean[1000];
        _Ez_Int_Iterator it = set.iterator();
        for (int i = 0; i < 1000; i++) {
            Assert.assertTrue(it.hasNext());
            int x = it.next();
            Assert.assertFalse(was[x]);
            was[x] = true;
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
            _Ez_Int_HashSet set = new _Ez_Int_HashSet();
            for (int i = 0; i < len; i++) {
                set.add(arr[i]);
            }
            int[] setArray = set.toArray();
            Assert.assertEquals(setArray.length, len);
            Arrays.sort(setArray);
            Assert.assertEquals(setArray, Arrays.copyOfRange(arr, 0, len));
        }
    }

    @Test
    public void testEqualsHashcode() {
        for (int mask1 = 0; mask1 < 32; mask1++) {
            for (int mask2 = 0; mask2 < 32; mask2++) {
                _Ez_Int_HashSet set1 = new _Ez_Int_HashSet(1);
                _Ez_Int_HashSet set2 = new _Ez_Int_HashSet(1);
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
        assertIsOneOf(new _Ez_Int_HashSet(new int[0]).toString(), "[]");
        assertIsOneOf(new _Ez_Int_HashSet(new int[] {1}).toString(), "[1]");
        assertIsOneOf(new _Ez_Int_HashSet(new int[] {1, 1}).toString(), "[1]");
        assertIsOneOf(new _Ez_Int_HashSet(new int[] {1, 1, 1}).toString(), "[1]");
        assertIsOneOf(new _Ez_Int_HashSet(new int[] {1, 2}).toString(), "[1, 2]", "[2, 1]");
        assertIsOneOf(new _Ez_Int_HashSet(new int[] {1, 1, 2}).toString(), "[1, 2]", "[2, 1]");
        assertIsOneOf(new _Ez_Int_HashSet(new int[] {1, 2, 1}).toString(), "[1, 2]", "[2, 1]");
        assertIsOneOf(new _Ez_Int_HashSet(new int[] {1, 2, 2}).toString(), "[1, 2]", "[2, 1]");
        assertIsOneOf(new _Ez_Int_HashSet(new int[] {2, 1, 2}).toString(), "[1, 2]", "[2, 1]");
        assertIsOneOf(new _Ez_Int_HashSet(new int[] {1, 2, 3}).toString(),
                "[1, 2, 3]", "[1, 3, 2]", "[2, 1, 3]", "[2, 3, 1]", "[3, 1, 2]", "[3, 2, 1]");
    }

    private void assertIsOneOf(Object actual, Object... expected) {
        for (Object e : expected) {
            if (e == null && actual == null) {
                return;
            }
            if (e != null && e.equals(actual)) {
                return;
            }
        }
        Assert.fail();
    }
}
