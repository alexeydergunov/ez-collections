package ez.collections.treemap;

import ez.collections._Ez_Int__Int_Map;
import ez.collections._Ez_Int__Int_MapIterator;
import ez.collections.tuples._Ez_Int__Int_Pair;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

public class EzTreeMapTest {
    private void testPermutation(int[] array) {
        int n = array.length;
        _Ez_Int__Int_TreeMap map = new _Ez_Int__Int_TreeMap(0);
        for (int it = 0; it < 3; it++) {
            Assert.assertEquals(map.size(), 0);
            Assert.assertTrue(map.isEmpty());
            for (int i = 0; i < n; i++) {
                map.put(array[i], 2 * array[i]);
                Assert.assertTrue(map.returnedNull());
                Assert.assertEquals(map.size(), i + 1);
                Assert.assertFalse(map.isEmpty());
            }
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0; i < n; i++) {
                Assert.assertEquals(map.put(array[i], 3 * array[i]), 2 * array[i]);
                Assert.assertFalse(map.returnedNull());
                Assert.assertEquals(map.size(), n);
                Assert.assertFalse(map.isEmpty());
            }
            for (int i = -1; i <= n; i++) {
                if (0 <= i && i < n) {
                    Assert.assertTrue(map.containsKey(array[i]));
                    Assert.assertEquals(map.get(array[i]), 3 * array[i]);
                    Assert.assertFalse(map.returnedNull());
                } else {
                    Assert.assertFalse(map.containsKey(i));
                    map.get(i);
                    Assert.assertTrue(map.returnedNull());
                }
            }
            Assert.assertEquals(map.getFirstKey(), 0);
            Assert.assertFalse(map.returnedNull());
            Assert.assertEquals(map.getFirstEntry(), new _Ez_Int__Int_Pair(0, 0));
            Assert.assertFalse(map.returnedNull());
            Assert.assertEquals(map.getLastKey(), n - 1);
            Assert.assertFalse(map.returnedNull());
            Assert.assertEquals(map.getLastEntry(), new _Ez_Int__Int_Pair(n - 1, 3 * (n - 1)));
            Assert.assertFalse(map.returnedNull());
            for (int i = -2; i <= n + 1; i++) {
                int lowerKey = map.lowerKey(i);
                if (i > 0) {
                    Assert.assertEquals(lowerKey, Math.min(i - 1, n - 1));
                    Assert.assertFalse(map.returnedNull());
                } else {
                    Assert.assertTrue(map.returnedNull());
                }
                int higherKey = map.higherKey(i);
                if (i < n - 1) {
                    Assert.assertEquals(higherKey, Math.max(i + 1, 0));
                    Assert.assertFalse(map.returnedNull());
                } else {
                    Assert.assertTrue(map.returnedNull());
                }
                int floorKey = map.floorKey(i);
                if (i >= 0) {
                    Assert.assertEquals(floorKey, Math.min(i, n - 1));
                    Assert.assertFalse(map.returnedNull());
                } else {
                    Assert.assertTrue(map.returnedNull());
                }
                int ceilingKey = map.ceilingKey(i);
                if (i < n) {
                    Assert.assertEquals(ceilingKey, Math.max(i, 0));
                    Assert.assertFalse(map.returnedNull());
                } else {
                    Assert.assertTrue(map.returnedNull());
                }
                _Ez_Int__Int_Pair lowerEntry = map.lowerEntry(i);
                if (i > 0) {
                    Assert.assertEquals(lowerEntry, new _Ez_Int__Int_Pair(lowerKey, 3 * lowerKey));
                    Assert.assertFalse(map.returnedNull());
                } else {
                    Assert.assertNull(lowerEntry);
                    Assert.assertTrue(map.returnedNull());
                }
                _Ez_Int__Int_Pair higherEntry = map.higherEntry(i);
                if (i < n - 1) {
                    Assert.assertEquals(higherEntry, new _Ez_Int__Int_Pair(higherKey, 3 * higherKey));
                    Assert.assertFalse(map.returnedNull());
                } else {
                    Assert.assertNull(higherEntry);
                    Assert.assertTrue(map.returnedNull());
                }
                _Ez_Int__Int_Pair floorEntry = map.floorEntry(i);
                if (i >= 0) {
                    Assert.assertEquals(floorEntry, new _Ez_Int__Int_Pair(floorKey, 3 * floorKey));
                    Assert.assertFalse(map.returnedNull());
                } else {
                    Assert.assertNull(floorEntry);
                    Assert.assertTrue(map.returnedNull());
                }
                _Ez_Int__Int_Pair ceilingEntry = map.ceilingEntry(i);
                if (i < n) {
                    Assert.assertEquals(ceilingEntry, new _Ez_Int__Int_Pair(ceilingKey, 3 * ceilingKey));
                    Assert.assertFalse(map.returnedNull());
                } else {
                    Assert.assertNull(ceilingEntry);
                    Assert.assertTrue(map.returnedNull());
                }
            }
            int removed = 0;
            for (int i = -1; i <= n; i++) {
                if (0 <= i && i < n) {
                    Assert.assertEquals(map.remove(array[i]), 3 * array[i]);
                    Assert.assertFalse(map.returnedNull());
                    removed++;
                } else {
                    map.remove(i);
                    Assert.assertTrue(map.returnedNull());
                }
                Assert.assertEquals(map.size(), n - removed);
            }
            map.clear();
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
        Random rnd = new Random(-228);
        for (int it = 0; it < 7000; it++) {
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
        Random rnd = new Random(-322);
        for (int it = 0; it < 35; it++) {
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
    public void testOperationsOnEmptyMap() {
        _Ez_Int__Int_TreeMap map = new _Ez_Int__Int_TreeMap(0);
        Assert.assertTrue(map.isEmpty());
        Assert.assertEquals(map.size(), 0);
        for (int key = -200; key <= 200; key++) {
            Assert.assertFalse(map.containsKey(key));
            map.get(key);
            Assert.assertTrue(map.returnedNull());
            map.remove(key);
            Assert.assertTrue(map.returnedNull());
            map.getFirstKey();
            Assert.assertTrue(map.returnedNull());
            map.getLastKey();
            Assert.assertTrue(map.returnedNull());
            map.higherKey(key);
            Assert.assertTrue(map.returnedNull());
            map.lowerKey(key);
            Assert.assertTrue(map.returnedNull());
            map.ceilingKey(key);
            Assert.assertTrue(map.returnedNull());
            map.floorKey(key);
            Assert.assertTrue(map.returnedNull());
            Assert.assertEquals(map.higherEntry(key), null);
            Assert.assertTrue(map.returnedNull());
            Assert.assertEquals(map.lowerEntry(key), null);
            Assert.assertTrue(map.returnedNull());
            Assert.assertEquals(map.ceilingEntry(key), null);
            Assert.assertTrue(map.returnedNull());
            Assert.assertEquals(map.floorEntry(key), null);
            Assert.assertTrue(map.returnedNull());
            Assert.assertEquals(map.getFirstEntry(), null);
            Assert.assertTrue(map.returnedNull());
            Assert.assertEquals(map.getLastEntry(), null);
            Assert.assertTrue(map.returnedNull());
        }
        Assert.assertEquals(map.keys(), new int[0]);
        Assert.assertEquals(map.values(), new int[0]);
        Assert.assertEquals(map.toString(), "{}");
        _Ez_Int__Int_MapIterator it = map.iterator();
        for (int retries = 0; retries < 3; retries++) {
            Assert.assertFalse(it.hasNext());
            try {
                it.getKey();
            } catch (NoSuchElementException e) {
                // as expected
            }
            try {
                it.getValue();
            } catch (NoSuchElementException e) {
                // as expected
            }
            it.next(); // nothing must happen
        }
    }

    private void assertSortedMapMethods(_Ez_Int__Int_TreeMap map, TreeMap<Integer, Integer> javaMap, int key) {
        {
            Map.Entry<Integer, Integer> javaEntry = javaMap.floorEntry(key);
            int ezResultKey = map.floorKey(key);
            if (javaEntry == null) {
                Assert.assertTrue(map.returnedNull());
            } else {
                Assert.assertEquals(ezResultKey, (int) javaEntry.getKey());
            }
            _Ez_Int__Int_Pair ezResultEntry = map.floorEntry(key);
            if (javaEntry == null) {
                Assert.assertTrue(map.returnedNull());
                Assert.assertNull(ezResultEntry);
            } else {
                Assert.assertEquals(ezResultEntry, new _Ez_Int__Int_Pair(javaEntry.getKey(), javaEntry.getValue()));
            }
        }
        {
            Map.Entry<Integer, Integer> javaEntry = javaMap.ceilingEntry(key);
            int ezResultKey = map.ceilingKey(key);
            if (javaEntry == null) {
                Assert.assertTrue(map.returnedNull());
            } else {
                Assert.assertEquals(ezResultKey, (int) javaEntry.getKey());
            }
            _Ez_Int__Int_Pair ezResultEntry = map.ceilingEntry(key);
            if (javaEntry == null) {
                Assert.assertTrue(map.returnedNull());
                Assert.assertNull(ezResultEntry);
            } else {
                Assert.assertEquals(ezResultEntry, new _Ez_Int__Int_Pair(javaEntry.getKey(), javaEntry.getValue()));
            }
        }
        {
            Map.Entry<Integer, Integer> javaEntry = javaMap.lowerEntry(key);
            int ezResultKey = map.lowerKey(key);
            if (javaEntry == null) {
                Assert.assertTrue(map.returnedNull());
            } else {
                Assert.assertEquals(ezResultKey, (int) javaEntry.getKey());
            }
            _Ez_Int__Int_Pair ezResultEntry = map.lowerEntry(key);
            if (javaEntry == null) {
                Assert.assertTrue(map.returnedNull());
                Assert.assertNull(ezResultEntry);
            } else {
                Assert.assertEquals(ezResultEntry, new _Ez_Int__Int_Pair(javaEntry.getKey(), javaEntry.getValue()));
            }
        }
        {
            Map.Entry<Integer, Integer> javaEntry = javaMap.higherEntry(key);
            int ezResultKey = map.higherKey(key);
            if (javaEntry == null) {
                Assert.assertTrue(map.returnedNull());
            } else {
                Assert.assertEquals(ezResultKey, (int) javaEntry.getKey());
            }
            _Ez_Int__Int_Pair ezResultEntry = map.higherEntry(key);
            if (javaEntry == null) {
                Assert.assertTrue(map.returnedNull());
                Assert.assertNull(ezResultEntry);
            } else {
                Assert.assertEquals(ezResultEntry, new _Ez_Int__Int_Pair(javaEntry.getKey(), javaEntry.getValue()));
            }
        }
    }

    @Test
    public void testSignificantMethodsOnRandomData() {
        _Ez_Int__Int_TreeMap map = new _Ez_Int__Int_TreeMap();
        TreeMap<Integer, Integer> javaMap = new TreeMap<Integer, Integer>();
        Random rnd = new Random(~42);
        for (int i = 0; i < 1000000; i++) {
            Assert.assertEquals(map.size(), javaMap.size());
            double prob = rnd.nextDouble();
            if (prob < 0.301) {
                int key = rnd.nextInt(50);
                int newValue = rnd.nextInt();
                Integer oldValue = javaMap.put(key, newValue);
                if (oldValue == null) {
                    map.put(key, newValue);
                    Assert.assertTrue(map.returnedNull());
                } else {
                    int actualOldValue = map.put(key, newValue);
                    Assert.assertFalse(map.returnedNull());
                    Assert.assertEquals(actualOldValue, (int) oldValue);
                }
            } else if (prob < 0.6) {
                int key = rnd.nextInt(50);
                Integer oldValue = javaMap.remove(key);
                if (oldValue == null) {
                    map.remove(key);
                    Assert.assertTrue(map.returnedNull());
                } else {
                    int actualOldValue = map.remove(key);
                    Assert.assertFalse(map.returnedNull());
                    Assert.assertEquals(actualOldValue, (int) oldValue);
                }
            } else {
                int key = rnd.nextInt(20);
                assertSortedMapMethods(map, javaMap, key);
            }
        }
    }

    @Test
    public void testConstructors() {
        TreeMap<Integer, Integer> srcJavaMap = new TreeMap<Integer, Integer>();
        _Ez_Int__Int_Map srcEzMap = new _Ez_Int__Int_TreeMap();
        int[] keys = new int[1000];
        for (int i = 0; i < 1000; i++) {
            keys[i] = i * 10003 + 666;
            srcJavaMap.put(keys[i], ~keys[i]);
            srcEzMap.put(keys[i], ~keys[i]);
        }
        _Ez_Int__Int_TreeMap[] maps = new _Ez_Int__Int_TreeMap[] {
                new _Ez_Int__Int_TreeMap(srcEzMap),
                new _Ez_Int__Int_TreeMap(srcJavaMap)
        };
        for (_Ez_Int__Int_TreeMap map : maps) {
            Assert.assertEquals(map.size(), 1000);
            for (int i = 0; i < 1000; i++) {
                Assert.assertEquals(map.get(keys[i]), ~keys[i]);
                Assert.assertFalse(map.returnedNull());
            }
            Assert.assertEquals(map.keys(), keys);
            Assert.assertEquals(map, srcEzMap);
        }
    }

    @Test
    public void testIterator() {
        _Ez_Int__Int_TreeMap map = new _Ez_Int__Int_TreeMap();
        for (int i = 0; i < 1000; i++) {
            map.put(i, ~i);
        }
        _Ez_Int__Int_MapIterator it = map.iterator();
        for (int i = 0; i < 1000; i++) {
            Assert.assertTrue(it.hasNext());
            Assert.assertEquals(it.getKey(), i);
            Assert.assertEquals(it.getValue(), ~i);
            it.next(); // nothing must happen
        }
        for (int retries = 0; retries < 3; retries++) {
            Assert.assertFalse(it.hasNext());
            try {
                it.getKey();
            } catch (NoSuchElementException e) {
                // as expected
            }
            try {
                it.getValue();
            } catch (NoSuchElementException e) {
                // as expected
            }
            it.next(); // nothing must happen
        }
    }

    @Test
    public void testKeysValuesArrays() {
        int[] keys = new int[100];
        int[] values = new int[100];
        for (int i = 0; i < 100; i++) {
            keys[i] = i * 322 + 228;
            values[i] = i * 228 + 322;
        }
        for (int len = 0; len <= 100; len++) {
            _Ez_Int__Int_TreeMap map = new _Ez_Int__Int_TreeMap();
            for (int i = 0; i < len; i++) {
                map.put(keys[i], values[i]);
                Assert.assertTrue(map.returnedNull());
            }
            Assert.assertEquals(map.keys(), Arrays.copyOfRange(keys, 0, len));
            Assert.assertEquals(map.values(), Arrays.copyOfRange(values, 0, len));
        }
    }

    @Test
    public void testEqualsHashcode() {
        for (int mask1 = 0; mask1 < 32; mask1++) {
            for (int mask2 = 0; mask2 < 32; mask2++) {
                _Ez_Int__Int_TreeMap map1 = new _Ez_Int__Int_TreeMap(1);
                _Ez_Int__Int_TreeMap map2 = new _Ez_Int__Int_TreeMap(1);
                for (int i = 0; i < 5; i++) {
                    if ((mask1 & (1 << i)) != 0) {
                        map1.put(1000 * i, i);
                    }
                    if ((mask2 & (1 << i)) != 0) {
                        map2.put(1000 * i, i);
                    }
                }
                int hashCode1 = map1.hashCode();
                int hashCode2 = map2.hashCode();
                if (mask1 == mask2) {
                    Assert.assertTrue(map1.equals(map2));
                    Assert.assertTrue(map2.equals(map1));
                    Assert.assertTrue(hashCode1 == hashCode2);
                } else {
                    Assert.assertFalse(map1.equals(map2));
                    Assert.assertFalse(map2.equals(map1));
                }
            }
        }
    }

    @Test
    public void testToString() {
        _Ez_Int__Int_TreeMap map = new _Ez_Int__Int_TreeMap();
        Assert.assertEquals(map.toString(), "{}");
        map.put(123, 456);
        Assert.assertEquals(map.toString(), "{123=456}");
        map.put(-987, -654);
        Assert.assertEquals(map.toString(), "{-987=-654, 123=456}");
        map.put(-345, 765);
        Assert.assertEquals(map.toString(), "{-987=-654, -345=765, 123=456}");
        map.put(0, -1);
        Assert.assertEquals(map.toString(), "{-987=-654, -345=765, 0=-1, 123=456}");
    }
}
