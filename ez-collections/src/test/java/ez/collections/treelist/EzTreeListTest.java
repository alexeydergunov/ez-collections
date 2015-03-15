package ez.collections.treelist;

import ez.collections._Ez_Int_Iterator;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

public class EzTreeListTest {
    private void assertEquals(_Ez_Int_TreeList ezList, List<Integer> javaList) {
        Assert.assertEquals(ezList.size(), javaList.size());
        _Ez_Int_Iterator ezIterator = ezList.iterator();
        for (int element : javaList) {
            Assert.assertTrue(ezIterator.hasNext());
            Assert.assertEquals(ezIterator.next(), element);
        }
        Assert.assertFalse(ezIterator.hasNext());
    }

    private void assertEquals(_Ez_Int_TreeList ezList, int[] array) {
        Assert.assertEquals(ezList.size(), array.length);
        Assert.assertEquals(ezList.toArray(), array);
        _Ez_Int_Iterator ezIterator = ezList.iterator();
        for (int element : array) {
            Assert.assertTrue(ezIterator.hasNext());
            Assert.assertEquals(ezIterator.next(), element);
        }
        Assert.assertFalse(ezIterator.hasNext());
    }

    @Test
    public void testAdd() {
        _Ez_Int_TreeList list = new _Ez_Int_TreeList(0);
        int[] array = new int[64];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        Assert.assertTrue(list.isEmpty());
        for (int i = 0; i < array.length; i++) {
            Assert.assertTrue(list.add(array[i]));
            Assert.assertEquals(list.toArray(), Arrays.copyOfRange(array, 0, i + 1));
        }
    }

    @Test
    public void testRemoveContainsIndexOf() {
        _Ez_Int_TreeList list = new _Ez_Int_TreeList();
        int[] array = new int[200];
        for (int i = 0; i < 200; i++) {
            list.add(i % 100);
            array[i] = i % 100;
        }
        assertEquals(list, array);
        for (int i = 0; i < 100; i++) {
            Assert.assertTrue(list.contains(i));
            Assert.assertEquals(list.indexOf(i), i);
            Assert.assertEquals(list.lastIndexOf(i), i + 100);
        }
        for (int i = 0; i < 100; i++) {
            Assert.assertTrue(list.contains(i));
            Assert.assertEquals(list.indexOf(i), 0);
            Assert.assertEquals(list.lastIndexOf(i), 100);
            Assert.assertTrue(list.remove(i));
            assertEquals(list, Arrays.copyOfRange(array, i + 1, array.length));
        }
        for (int i = 0; i < 100; i++) {
            Assert.assertTrue(list.contains(i));
            Assert.assertEquals(list.indexOf(i), i);
            Assert.assertEquals(list.lastIndexOf(i), i);
        }
        for (int i = 0; i < 100; i++) {
            Assert.assertTrue(list.contains(i));
            Assert.assertEquals(list.indexOf(i), 0);
            Assert.assertEquals(list.lastIndexOf(i), 0);
            Assert.assertTrue(list.remove(i));
            assertEquals(list, Arrays.copyOfRange(array, i + 101, array.length));
        }
        Assert.assertTrue(list.isEmpty());
        Assert.assertEquals(list.size(), 0);
    }

    @Test
    public void testRemoveAt() {
        _Ez_Int_TreeList ezList = new _Ez_Int_TreeList();
        List<Integer> javaList = new ArrayList<Integer>(2000);
        for (int i = 0; i < 2000; i++) {
            ezList.add(i);
            javaList.add(i);
        }
        assertEquals(ezList, javaList);
        Random rnd = new Random(322);
        for (int i = 0; i < 2000; i++) {
            int pos = rnd.nextInt(ezList.size());
            int ezRemoved = ezList.removeAt(pos);
            int javaRemoved = javaList.remove(pos);
            Assert.assertEquals(ezRemoved, javaRemoved);
            assertEquals(ezList, javaList);
        }
    }

    @Test
    public void testInsertCorrectness() {
        _Ez_Int_TreeList ezList = new _Ez_Int_TreeList(0);
        List<Integer> javaList = new ArrayList<Integer>(2500);
        Random rnd = new Random(3223);
        for (int i = 0; i < 2500; i++) {
            int pos = rnd.nextInt(ezList.size() + 1);
            int num = rnd.nextInt();
            ezList.insert(pos, num);
            javaList.add(pos, num);
            assertEquals(ezList, javaList);
        }
        try {
            ezList.insert(-1, 0);
        } catch (IndexOutOfBoundsException e) {
            // as expected
        }
        try {
            ezList.insert(ezList.size() + 1, 0);
        } catch (IndexOutOfBoundsException e) {
            // as expected
        }
    }

    @Test(timeOut = 1000)
    public void testInsertSpeed() {
        _Ez_Int_TreeList ezList = new _Ez_Int_TreeList(0);
        Random rnd = new Random(32232);
        for (int i = 0; i < 100000; i++) {
            int pos = rnd.nextInt(ezList.size() + 1);
            int num = rnd.nextInt();
            ezList.insert(pos, num);
            Assert.assertEquals(ezList.size(), i + 1);
        }
    }

    @Test
    public void testGetSet() {
        _Ez_Int_TreeList list = new _Ez_Int_TreeList();
        int[] array = new int[500];
        for (int i = 0; i < 500; i++) {
            list.add(i);
            array[i] = i;
        }
        assertEquals(list, array);
        Random rnd = new Random(322322);
        for (int i = 0; i < 2000; i++) {
            int pos = rnd.nextInt(list.size());
            int oldElement = array[pos];
            int newElement = 500 + i;
            array[pos] = newElement;
            Assert.assertEquals(list.get(pos), oldElement);
            Assert.assertEquals(list.set(pos, newElement), oldElement);
            Assert.assertEquals(list.get(pos), newElement);
            assertEquals(list, array);
        }
        try {
            list.get(-1);
        } catch (IndexOutOfBoundsException e) {
            // as expected
        }
        try {
            list.get(list.size());
        } catch (IndexOutOfBoundsException e) {
            // as expected
        }
        try {
            list.set(-1, 0);
        } catch (IndexOutOfBoundsException e) {
            // as expected
        }
        try {
            list.set(list.size(), 0);
        } catch (IndexOutOfBoundsException e) {
            // as expected
        }
    }

    @Test
    public void testAddGetRemoveFirst() {
        _Ez_Int_TreeList list = new _Ez_Int_TreeList(0);
        try {
            list.getFirst();
        } catch (NoSuchElementException e) {
            // as expected
        }
        try {
            list.removeFirst();
        } catch (NoSuchElementException e) {
            // as expected
        }
        for (int i = 0; i < 1000; i++) {
            list.addFirst(i);
        }
        for (int i = 0; i < 1000; i++) {
            Assert.assertEquals(list.getFirst(), 1000 - i - 1);
            Assert.assertEquals(list.removeFirst(), 1000 - i - 1);
        }
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void testAddGetRemoveLast() {
        _Ez_Int_TreeList list = new _Ez_Int_TreeList(0);
        try {
            list.getLast();
        } catch (NoSuchElementException e) {
            // as expected
        }
        try {
            list.removeLast();
        } catch (NoSuchElementException e) {
            // as expected
        }
        for (int i = 0; i < 1000; i++) {
            list.addLast(i);
        }
        for (int i = 0; i < 1000; i++) {
            Assert.assertEquals(list.getLast(), 1000 - i - 1);
            Assert.assertEquals(list.removeLast(), 1000 - i - 1);
        }
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void testToArrayToString() {
        _Ez_Int_TreeList list = new _Ez_Int_TreeList(0);
        Assert.assertEquals(list.toArray(), new int[0]);
        Assert.assertEquals(list.toString(), "[]");
        Assert.assertTrue(list.add(1));
        Assert.assertEquals(list.toArray(), new int[]{1});
        Assert.assertEquals(list.toString(), "[1]");
        Assert.assertTrue(list.add(2));
        Assert.assertEquals(list.toArray(), new int[] {1, 2});
        Assert.assertEquals(list.toString(), "[1, 2]");
        list.insert(0, 3);
        Assert.assertEquals(list.toArray(), new int[] {3, 1, 2});
        Assert.assertEquals(list.toString(), "[3, 1, 2]");
        Assert.assertEquals(list.set(1, 4), 1);
        Assert.assertEquals(list.toArray(), new int[] {3, 4, 2});
        Assert.assertEquals(list.toString(), "[3, 4, 2]");
        Assert.assertEquals(list.removeAt(2), 2);
        Assert.assertEquals(list.toArray(), new int[] {3, 4});
        Assert.assertEquals(list.toString(), "[3, 4]");
        Assert.assertTrue(list.remove(3));
        Assert.assertEquals(list.toArray(), new int[] {4});
        Assert.assertEquals(list.toString(), "[4]");
        list.clear();
        Assert.assertEquals(list.toArray(), new int[0]);
        Assert.assertEquals(list.toString(), "[]");
    }

    @Test
    public void testToArrayLarge() {
        Random rnd = new Random(3223223);
        _Ez_Int_TreeList list = new _Ez_Int_TreeList();
        int[] array = new int[100000];
        for (int i = 0; i < 100000; i++) {
            int x = rnd.nextInt();
            list.add(x);
            array[i] = x;
        }
        Assert.assertEquals(list.toArray(), array);
    }

    @Test
    public void testIterator() {
        Random rnd = new Random(32232232);
        _Ez_Int_TreeList list = new _Ez_Int_TreeList();
        int[] array = new int[100000];
        for (int i = 0; i < 100000; i++) {
            int x = rnd.nextInt();
            list.add(x);
            array[i] = x;
        }
        _Ez_Int_Iterator it = list.iterator();
        for (int i = 0; i < 100000; i++) {
            Assert.assertTrue(it.hasNext());
            Assert.assertEquals(it.next(), array[i]);
        }
        Assert.assertFalse(it.hasNext());
        try {
            it.next();
        } catch (NoSuchElementException e) {
            // as expected
        }
    }

    @Test
    public void testIsEmptySizeClear() {
        _Ez_Int_TreeList list = new _Ez_Int_TreeList(0);
        Assert.assertTrue(list.isEmpty());
        Assert.assertEquals(list.size(), 0);
        for (int it = 0; it < 100; it++) {
            for (int i = 0; i < 100; i++) {
                list.add(i);
                Assert.assertFalse(list.isEmpty());
                Assert.assertEquals(list.size(), i + 1);
            }
            list.clear();
            Assert.assertTrue(list.isEmpty());
            Assert.assertEquals(list.size(), 0);
        }
    }

    @Test
    public void testEqualsHashcode() {
        final int n = 8;
        for (int mask1 = 0; mask1 < (1 << n); mask1++) {
            for (int mask2 = 0; mask2 < (1 << n); mask2++) {
                _Ez_Int_TreeList list1 = new _Ez_Int_TreeList(0);
                _Ez_Int_TreeList list2 = new _Ez_Int_TreeList(0);
                for (int i = 0; i < n; i++) {
                    if ((mask1 & (1 << i)) != 0) {
                        list1.add(i);
                    }
                    if ((mask2 & (1 << i)) != 0) {
                        list2.add(i);
                    }
                }
                //noinspection EqualsWithItself
                Assert.assertTrue(list1.equals(list1));
                //noinspection EqualsWithItself
                Assert.assertTrue(list2.equals(list2));
                if (mask1 == mask2) {
                    Assert.assertTrue(list1.equals(list2));
                    Assert.assertTrue(list2.equals(list1));
                    Assert.assertTrue(list1.hashCode() == list2.hashCode());
                } else {
                    Assert.assertFalse(list1.equals(list2));
                    Assert.assertFalse(list2.equals(list1));
                }
            }
        }
    }

    @Test
    public void testConstructors() {
        try {
            new _Ez_Int_TreeList(-1);
        } catch (IllegalArgumentException e) {
            // as expected
        }
        for (int length = 0; length <= 100; length++) {
            int[] array = new int[length];
            List<Integer> javaList = new ArrayList<Integer>(length);
            List<_Ez_Int_TreeList> ezLists = new ArrayList<_Ez_Int_TreeList>();
            ezLists.add(new _Ez_Int_TreeList());
            ezLists.add(new _Ez_Int_TreeList(0));
            ezLists.add(new _Ez_Int_TreeList(1));
            ezLists.add(new _Ez_Int_TreeList(100));
            ezLists.add(new _Ez_Int_TreeList(101));
            ezLists.add(new _Ez_Int_TreeList(228));
            for (int i = 0; i < length; i++) {
                array[i] = i;
                javaList.add(i);
                for (_Ez_Int_TreeList ezList : ezLists) {
                    ezList.add(i);
                }
            }
            ezLists.add(new _Ez_Int_TreeList(array));
            ezLists.add(new _Ez_Int_TreeList(javaList));
            ezLists.add(new _Ez_Int_TreeList(ezLists.get(0)));
            for (int i = 0; i < ezLists.size(); i++) {
                //noinspection ForLoopReplaceableByForEach
                for (int j = 0; j < ezLists.size(); j++) {
                    _Ez_Int_TreeList list1 = ezLists.get(i);
                    _Ez_Int_TreeList list2 = ezLists.get(j);
                    Assert.assertTrue(list1.equals(list2));
                }
            }
        }
    }
}
