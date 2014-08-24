package ez.collections.sort;

import ez.collections._Ez_Int_Comparator;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Random;

public class EzSortTest {
    private void genPermutations(int[] sorted, int[] permutation, int left, int length) {
        if (left >= length - 1) {
            int[] copy = new int[length];
            System.arraycopy(permutation, 0, copy, 0, length);
            _Ez_Int_Sort.sort(copy);
            Assert.assertEquals(copy, sorted);
            return;
        }
        for (int i = left; i < length; i++) {
            swap(permutation, i, left);
            genPermutations(sorted, permutation, left + 1, length);
            swap(permutation, i, left);
        }
    }

    private void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    @Test
    public void testAllSmallPermutations() {
        for (int length = 0; length <= 9; length++) {
            int[] sorted = new int[length];
            int[] permutation = new int[length];
            for (int i = 0; i < length; i++) {
                sorted[i] = permutation[i] = i;
            }
            genPermutations(sorted, permutation, 0, length);
        }
    }

    @Test
    public void testSmallArrays() {
        Random rnd = new Random(322);
        for (int it = 0; it < 500000; it++) {
            int length = 1 + rnd.nextInt(42);
            int[] sorted = new int[length];
            int[] array = new int[length];
            for (int i = 0; i < length; i++) {
                sorted[i] = array[i] = rnd.nextInt(length);
            }
            Arrays.sort(sorted);
            _Ez_Int_Sort.sort(array);
            Assert.assertEquals(array, sorted);
        }
    }

    @Test
    public void testLargeArraysWithDifferentElements() {
        Random rnd = new Random(322);
        for (int it = 0; it < 100; it++) {
            int length = 50000 + rnd.nextInt(50000);
            int[] sorted = new int[length];
            int[] array = new int[length];
            for (int i = 0; i < length; i++) {
                sorted[i] = array[i] = rnd.nextInt();
            }
            Arrays.sort(sorted);
            _Ez_Int_Sort.sort(array);
            Assert.assertEquals(array, sorted);
        }
    }

    @Test
    public void testLargeArraysWithManyEqualElements() {
        Random rnd = new Random(322);
        for (int it = 0; it < 100; it++) {
            int length = 50000 + rnd.nextInt(50000);
            int[] sorted = new int[length];
            int[] array = new int[length];
            for (int i = 0; i < length; i++) {
                sorted[i] = array[i] = rnd.nextInt(200);
            }
            Arrays.sort(sorted);
            _Ez_Int_Sort.sort(array);
            Assert.assertEquals(array, sorted);
        }
    }

    @Test
    public void testExclusiveRightIndex() {
        int[] a = {5, 4, 3, 2, 1};
        _Ez_Int_Sort.sort(a, 0, 4);
        Assert.assertEquals(a, new int[] {2, 3, 4, 5, 1});
        _Ez_Int_Sort.sort(a, 0, 5);
        Assert.assertEquals(a, new int[] {1, 2, 3, 4, 5});
    }

    @Test
    public void testZeroAndOneElementArrays() {
        int[] a = new int[0];
        _Ez_Int_Sort.sort(a);
        Assert.assertEquals(a, new int[0]);
        int[] b = {100500};
        _Ez_Int_Sort.sort(b);
        Assert.assertEquals(b, new int[] {100500});
    }

    private void reverse(int[] a) {
        int n = a.length;
        for (int i = 0; i < (n >>> 1); i++) {
            int tmp = a[i];
            a[i] = a[n - 1 - i];
            a[n - 1 - i] = tmp;
        }
    }

    @Test
    public void testReverseSort() {
        Random rnd = new Random(322);
        for (int it = 0; it < 100; it++) {
            int length = 50000 + rnd.nextInt(50000);
            int[] sorted = new int[length];
            int[] array = new int[length];
            for (int i = 0; i < length; i++) {
                sorted[i] = array[i] = rnd.nextInt();
            }
            Arrays.sort(sorted);
            reverse(sorted);
            _Ez_Int_ReverseSort.sort(array);
            Assert.assertEquals(array, sorted);
        }
    }

    @Test
    public void testCustomSort() {
        _Ez_Int_Comparator reverseCmp = new _Ez_Int_Comparator() {
            @Override
            public int compare(int a, int b) {
                if (a > b) return -1;
                if (a < b) return 1;
                return 0;
            }
        };
        Random rnd = new Random(322);
        for (int it = 0; it < 100; it++) {
            int length = 50000 + rnd.nextInt(50000);
            int[] sorted = new int[length];
            int[] array = new int[length];
            for (int i = 0; i < length; i++) {
                sorted[i] = array[i] = rnd.nextInt();
            }
            Arrays.sort(sorted);
            reverse(sorted);
            _Ez_Int_CustomSort.sort(array, reverseCmp);
            Assert.assertEquals(array, sorted);
        }
    }
}
