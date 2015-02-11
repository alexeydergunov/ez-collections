package ez.collections.hashmap;

import ez.collections._Ez_Int__Int_MapIterator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Random;

public class EzHashMapTest {
    private void assertKeyData(_Ez_Int__Int_HashMap ezMap, HashMap<Integer, Integer> javaMap, int key) {
        Integer javaVal = javaMap.get(key);
        int ezVal = ezMap.get(key);
        if (javaVal == null) {
            Assert.assertTrue(ezMap.returnedNull());
        } else {
            Assert.assertFalse(ezMap.returnedNull());
            Assert.assertEquals(ezVal, (int) javaVal);
        }
        Assert.assertEquals(ezMap.containsKey(key), javaMap.containsKey(key));
    }

    @Test
    public void testPutGetRemoveContainsSmallMaps() {
        Random rnd = new Random(322);
        for (int it = 0; it < 10000; it++) {
            _Ez_Int__Int_HashMap ezMap = new _Ez_Int__Int_HashMap(0);
            HashMap<Integer, Integer> javaMap = new HashMap<Integer, Integer>();
            Assert.assertTrue(ezMap.isEmpty());
            for (int i = 0; i < 100; i++) {
                int key = rnd.nextInt(50);
                int value = rnd.nextInt(50);
                assertKeyData(ezMap, javaMap, key);
                Assert.assertEquals(ezMap.size(), javaMap.size());
                if (rnd.nextBoolean()) {
                    Integer javaVal = javaMap.put(key, value);
                    int ezVal = ezMap.put(key, value);
                    if (javaVal == null) {
                        Assert.assertTrue(ezMap.returnedNull());
                    } else {
                        Assert.assertFalse(ezMap.returnedNull());
                        Assert.assertEquals(ezVal, (int) javaVal);
                    }
                } else {
                    Integer javaVal = javaMap.remove(key);
                    int ezVal = ezMap.remove(key);
                    if (javaVal == null) {
                        Assert.assertTrue(ezMap.returnedNull());
                    } else {
                        Assert.assertFalse(ezMap.returnedNull());
                        Assert.assertEquals(ezVal, (int) javaVal);
                    }
                }
                assertKeyData(ezMap, javaMap, key);
                Assert.assertEquals(ezMap.size(), javaMap.size());
            }
        }
    }

    @Test
    public void testPutGetRemoveContainsBigMaps() {
        Random rnd = new Random(3223);
        for (int it = 0; it < 100; it++) {
            _Ez_Int__Int_HashMap ezMap = new _Ez_Int__Int_HashMap(0);
            HashMap<Integer, Integer> javaMap = new HashMap<Integer, Integer>();
            Assert.assertTrue(ezMap.isEmpty());
            for (int i = 0; i < 10000; i++) {
                int key = rnd.nextInt(5000);
                int value = rnd.nextInt(5000);
                assertKeyData(ezMap, javaMap, key);
                Assert.assertEquals(ezMap.size(), javaMap.size());
                if (rnd.nextBoolean()) {
                    Integer javaVal = javaMap.put(key, value);
                    int ezVal = ezMap.put(key, value);
                    if (javaVal == null) {
                        Assert.assertTrue(ezMap.returnedNull());
                    } else {
                        Assert.assertFalse(ezMap.returnedNull());
                        Assert.assertEquals(ezVal, (int) javaVal);
                    }
                } else {
                    Integer javaVal = javaMap.remove(key);
                    int ezVal = ezMap.remove(key);
                    if (javaVal == null) {
                        Assert.assertTrue(ezMap.returnedNull());
                    } else {
                        Assert.assertFalse(ezMap.returnedNull());
                        Assert.assertEquals(ezVal, (int) javaVal);
                    }
                }
                assertKeyData(ezMap, javaMap, key);
                Assert.assertEquals(ezMap.size(), javaMap.size());
            }
        }
    }

    @Test
    public void testPut() {
        _Ez_Int__Int_HashMap map = new _Ez_Int__Int_HashMap();
        int[] array = new int[10000];
        Arrays.fill(array, -1);
        Random rnd = new Random(32232);
        int size = 0;
        for (int i = 0; i < 1000000; i++) {
            Assert.assertEquals(map.size(), size);
            int key = rnd.nextInt(10000);
            int oldValue = map.put(key, i);
            if (array[key] == -1) {
                size++;
                Assert.assertTrue(map.returnedNull());
            } else {
                Assert.assertFalse(map.returnedNull());
                Assert.assertEquals(oldValue, array[key]);
            }
            array[key] = i;
            Assert.assertEquals(map.size(), size);
        }
    }

    @Test
    public void testGetContainsKey() {
        _Ez_Int__Int_HashMap map = new _Ez_Int__Int_HashMap();
        Random rnd = new Random(322322);
        int size = 100000;
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rnd.nextInt();
            map.put(i, array[i]);
            Assert.assertTrue(map.returnedNull());
        }
        Assert.assertEquals(map.size(), size);
        for (int i = 0; i < size; i++) {
            Assert.assertEquals(map.get(i), array[i]);
            Assert.assertFalse(map.returnedNull());
            Assert.assertTrue(map.containsKey(i));
            map.get(~i);
            Assert.assertTrue(map.returnedNull());
            Assert.assertFalse(map.containsKey(~i));
        }
    }

    @Test
    public void testRemove() {
        _Ez_Int__Int_HashMap map = new _Ez_Int__Int_HashMap();
        Random rnd = new Random(3223223);
        int size = 100000;
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rnd.nextInt();
            map.put(i, array[i]);
            Assert.assertTrue(map.returnedNull());
        }
        Assert.assertEquals(map.size(), size);
        for (int i = 0; i < size; i++) {
            Assert.assertTrue(map.containsKey(i));
            Assert.assertEquals(map.remove(i), array[i]);
            Assert.assertFalse(map.returnedNull());
            Assert.assertFalse(map.containsKey(i));
            map.remove(i);
            Assert.assertTrue(map.returnedNull());
            Assert.assertFalse(map.containsKey(i));
        }
    }

    @Test
    public void testClear() {
        _Ez_Int__Int_HashMap map = new _Ez_Int__Int_HashMap();
        Assert.assertTrue(map.isEmpty());
        Assert.assertEquals(map.size(), 0);
        int size = 0;
        for (int i = 1; i <= 1000000; i++) {
            map.put(i, -i);
            size++;
            Assert.assertFalse(map.isEmpty());
            Assert.assertEquals(map.size(), size);
            if ((i & (i - 1)) == 0) {
                map.clear();
                Assert.assertTrue(map.isEmpty());
                Assert.assertEquals(map.size(), 0);
                size = 0;
            }
        }
    }

    @Test
    public void testKeysValuesArrays() {
        Random rnd = new Random(32232232);
        int n = 100000;
        int[] keys = new int[n];
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            keys[i] = (1 + i) * 10000 + rnd.nextInt(9000);
            values[i] = rnd.nextInt();
        }
        _Ez_Int__Int_HashMap map = new _Ez_Int__Int_HashMap();
        for (int i = 0; i < n; i++) {
            map.put(keys[i], values[i]);
            Assert.assertTrue(map.returnedNull());
        }
        Assert.assertEquals(map.size(), n);
        int[] mapKeys = map.keys();
        int[] mapValues = map.values();
        Arrays.sort(keys);
        Arrays.sort(mapKeys);
        Arrays.sort(values);
        Arrays.sort(mapValues);
        Assert.assertEquals(mapKeys, keys);
        Assert.assertEquals(mapValues, values);
    }

    @Test
    public void testConstructors() {
        _Ez_Int__Int_HashMap srcMap = new _Ez_Int__Int_HashMap();
        HashMap<Integer, Integer> javaMap = new HashMap<Integer, Integer>();
        int n = 10000;
        for (int i = 0; i < n; i++) {
            srcMap.put(~i, i);
            javaMap.put(~i, i);
        }
        _Ez_Int__Int_HashMap map1 = new _Ez_Int__Int_HashMap(srcMap);
        _Ez_Int__Int_HashMap map2 = new _Ez_Int__Int_HashMap(javaMap);
        Assert.assertEquals(map1.size(), n);
        Assert.assertEquals(map2.size(), n);
        for (int i = 0; i < n; i++) {
            Assert.assertEquals(map1.get(~i), i);
            Assert.assertEquals(map2.get(~i), i);
        }
        Assert.assertEquals(map1, srcMap);
        Assert.assertEquals(map2, srcMap);
    }

    @Test
    public void testIterator() {
        Random rnd = new Random(322322322);
        int n = 1000;
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = rnd.nextInt();
        }
        _Ez_Int__Int_HashMap map = new _Ez_Int__Int_HashMap();
        for (int i = 0; i < n; i++) {
            map.put(i, array[i]);
            Assert.assertTrue(map.returnedNull());
        }
        boolean[] visited = new boolean[n];
        _Ez_Int__Int_MapIterator it = map.iterator();
        for (int i = 0; i < n; i++) {
            Assert.assertTrue(it.hasNext());
            int key = it.getKey();
            int value = it.getValue();
            Assert.assertEquals(value, array[key]);
            Assert.assertFalse(visited[key]);
            visited[key] = true;
            for (int retries = 0; retries < 2; retries++) {
                Assert.assertTrue(it.hasNext());
                Assert.assertEquals(it.getKey(), key);
                Assert.assertEquals(it.getValue(), value);
            }
            it.next();
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

    @DataProvider
    public Object[][] getHashMapAfterManyRemoves() {
        int n = 1000000;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i;
        }
        Random rnd = new Random(3223223223L);
        for (int i = 0; i < n; i++) {
            int j = i + rnd.nextInt(n - i);
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
        _Ez_Int__Int_HashMap map = new _Ez_Int__Int_HashMap();
        for (int i = 0; i < n; i++) {
            map.put(a[i], i);
        }
        for (int i = 0; i < n - 1000; i++) {
            map.remove(a[i]);
            Assert.assertFalse(map.returnedNull());
        }
        Assert.assertEquals(map.size(), 1000);
        return new Object[][] {{map}};
    }

    @Test(timeOut = 1000, dataProvider = "getHashMapAfterManyRemoves")
    public void testCompressingAfterRemoving(_Ez_Int__Int_HashMap map) {
        Assert.assertEquals(map.size(), 1000);
        // Length of the arrays in the map must be O(size). If it's not, there will be a timeout
        int dummy1 = 0, dummy2 = 0;
        for (int i = 0; i < 1000; i++) {
            for (_Ez_Int__Int_MapIterator it = map.iterator(); it.hasNext(); it.next()) {
                dummy1 ^= it.getKey();
                dummy2 ^= it.getValue();
            }
        }
        Assert.assertEquals(dummy1, 0);
        Assert.assertEquals(dummy2, 0);
    }

    @Test
    public void testEqualsHashcode() {
        for (int mask1 = 0; mask1 < 32; mask1++) {
            for (int mask2 = 0; mask2 < 32; mask2++) {
                _Ez_Int__Int_HashMap map1 = new _Ez_Int__Int_HashMap(1);
                _Ez_Int__Int_HashMap map2 = new _Ez_Int__Int_HashMap(1);
                for (int i = 0; i < 5; i++) {
                    if ((mask1 & (1 << i)) != 0) {
                        map1.put(i, 1000 * i);
                    }
                    if ((mask2 & (1 << i)) != 0) {
                        map2.put(i, 1000 * i);
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
        _Ez_Int__Int_HashMap map = new _Ez_Int__Int_HashMap();
        assertIsOneOf(map.toString(), "{}");
        map.put(123, 456);
        assertIsOneOf(map.toString(), "{123=456}");
        map.put(-987, -654);
        assertIsOneOf(map.toString(), "{123=456, -987=-654}", "{-987=-654, 123=456}");
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
