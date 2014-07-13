package ez.collections.list;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EzIntArrayListTest {
    @Test
    public void testAdd() {
        _Ez_Int_ArrayList list = new _Ez_Int_ArrayList(1);
        int[] array = new int[42];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }
        for (int i = 0; i < array.length; i++) {
            Assert.assertEquals(list.add(array[i]), true);
            Assert.assertEquals(list.toArray(), Arrays.copyOfRange(array, 0, i + 1));
        }
    }

    @Test
    public void testRemove() {
        _Ez_Int_ArrayList list = new _Ez_Int_ArrayList(1);
        for (int i = 1; i <= 5; i++) {
            list.add(i);
        }
        Assert.assertEquals(list.toArray(), new int[] {1, 2, 3, 4, 5});
        Assert.assertEquals(list.remove(2), true);
        Assert.assertEquals(list.toArray(), new int[] {1, 3, 4, 5});
        Assert.assertEquals(list.remove(6), false);
        Assert.assertEquals(list.toArray(), new int[] {1, 3, 4, 5});
        Assert.assertEquals(list.removeAt(2), 4);
        Assert.assertEquals(list.toArray(), new int[] {1, 3, 5});
        Assert.assertEquals(list.removeAt(2), 5);
        Assert.assertEquals(list.toArray(), new int[] {1, 3});
        try {
            list.removeAt(2);
        } catch (IndexOutOfBoundsException e) {
            // as expected
        }
        try {
            list.removeAt(-1);
        } catch (IndexOutOfBoundsException e) {
            // as expected
        }
        Assert.assertEquals(list.toArray(), new int[] {1, 3});
        Assert.assertEquals(list.removeAt(0), 1);
        Assert.assertEquals(list.toArray(), new int[] {3});
        Assert.assertEquals(list.remove(3), true);
        Assert.assertEquals(list.toArray(), new int[0]);
        Assert.assertEquals(list.remove(3), false);
    }

     @Test
     public void testInsert() {
        _Ez_Int_ArrayList list = new _Ez_Int_ArrayList(1);
        for (int i = 1; i <= 3; i++) {
            list.add(i);
        }
        Assert.assertEquals(list.toArray(), new int[] {1, 2, 3});
        list.insert(0, 4);
        Assert.assertEquals(list.toArray(), new int[] {4, 1, 2, 3});
        list.insert(2, 5);
        Assert.assertEquals(list.toArray(), new int[] {4, 1, 5, 2, 3});
        list.insert(5, 6);
        Assert.assertEquals(list.toArray(), new int[] {4, 1, 5, 2, 3, 6});
        try {
            list.insert(8, 7);
        } catch (IndexOutOfBoundsException e) {
            // as expected
        }
        try {
            list.insert(-1, 7);
        } catch (IndexOutOfBoundsException e) {
            // as expected
        }
        Assert.assertEquals(list.toArray(), new int[] {4, 1, 5, 2, 3, 6});
    }

    @Test
    public void testSet() {
        _Ez_Int_ArrayList list = new _Ez_Int_ArrayList(1);
        for (int i = 1; i <= 3; i++) {
            list.add(i);
        }
        Assert.assertEquals(list.toArray(), new int[] {1, 2, 3});
        list.set(0, 4);
        Assert.assertEquals(list.toArray(), new int[] {4, 2, 3});
        list.set(2, 5);
        Assert.assertEquals(list.toArray(), new int[] {4, 2, 5});
        try {
            list.set(3, 6);
        } catch (IndexOutOfBoundsException e) {
            // as expected
        }
        try {
            list.set(-1, 6);
        } catch (IndexOutOfBoundsException e) {
            // as expected
        }
        Assert.assertEquals(list.toArray(), new int[] {4, 2, 5});
    }

    @Test
    public void testPushPopBack() {
        _Ez_Int_ArrayList list = new _Ez_Int_ArrayList(1);
        for (int i = 1; i <= 3; i++) {
            list.add(i);
        }
        Assert.assertEquals(list.toArray(), new int[] {1, 2, 3});
        list.pushBack(4);
        Assert.assertEquals(list.toArray(), new int[] {1, 2, 3, 4});
        Assert.assertEquals(list.back(), 4);
        Assert.assertEquals(list.popBack(), 4);
        Assert.assertEquals(list.toArray(), new int[] {1, 2, 3});
        for (int i = 3; i >= 1; i--) {
            Assert.assertEquals(list.popBack(), i);
        }
        try {
            list.back();
        } catch (IndexOutOfBoundsException e) {
            // as expected
        }
        try {
            list.popBack();
        } catch (IndexOutOfBoundsException e) {
            // as expected
        }
        Assert.assertEquals(list.toArray(), new int[0]);
    }

    @Test
    public void testIndexOf() {
        int[] srcArray = new int[] {1, 2, 3, 2, 1, 2, 3, 2, 1};
        _Ez_Int_ArrayList list = new _Ez_Int_ArrayList(srcArray);
        Assert.assertEquals(list.toArray(), srcArray);
        Assert.assertEquals(list.indexOf(1), 0);
        Assert.assertEquals(list.indexOf(2), 1);
        Assert.assertEquals(list.indexOf(3), 2);
        Assert.assertEquals(list.indexOf(4), -1);
        Assert.assertEquals(list.lastIndexOf(1), 8);
        Assert.assertEquals(list.lastIndexOf(2), 7);
        Assert.assertEquals(list.lastIndexOf(3), 6);
        Assert.assertEquals(list.lastIndexOf(4), -1);
    }

    @Test
    public void testToString() {
        Assert.assertEquals(new _Ez_Int_ArrayList(new int[0]).toString(), "[]");
        Assert.assertEquals(new _Ez_Int_ArrayList(new int[] {1}).toString(), "[1]");
        Assert.assertEquals(new _Ez_Int_ArrayList(new int[] {1, 2}).toString(), "[1, 2]");
        Assert.assertEquals(new _Ez_Int_ArrayList(new int[] {1, 2, 3}).toString(), "[1, 2, 3]");
    }

    @Test
    public void testCreationFromJavaCollection() {
        for (int length = 0; length < 20; length++) {
            int[] array = new int[length];
            for (int i = 0; i < length; i++) {
                array[i] = i + 100501;
            }
            List<Integer> javaList = new ArrayList<Integer>(length);
            for (int i = 0; i < length; i++) {
                javaList.add(array[i]);
            }
            _Ez_Int_ArrayList list = new _Ez_Int_ArrayList(javaList);
            Assert.assertEquals(list.toArray(), array);
        }
    }
}
