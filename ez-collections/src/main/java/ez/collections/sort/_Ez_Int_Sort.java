package ez.collections.sort;

import java.util.Random;

/**
 * Provides the sort methods for natural (ascending) ordering.
 * <p>
 * The sorting is implemented as a quicksort with randomization, but when the recursion depth becomes too large, it is
 * switched to a heapsort (this algorithm is also known as introsort). Unlike methods in {@link java.util.Arrays},
 * which are implemented as only quicksort (it has some heuristics but still can be slowed down to O(n^2)), introsort
 * guarantees O(n log(n)) performance on all arrays.
 * <p>
 * In general this implementation is slower than the one in {@link java.util.Arrays}, so if you are absolutely sure
 * that nobody will give you an anti-quicksort array, you can use a standard algorithm.
 * @author Alexey Dergunov
 * @since 0.0.1
 */
public final class _Ez_Int_Sort {
    private static final double HEAPSORT_DEPTH_COEFFICIENT = 2.0;

    private static Random rnd = new Random(322);

    private _Ez_Int_Sort() {
    }

    private static int maxQuickSortDepth(int length) {
        if (length <= 1) {
            return 0;
        }
        int log = Integer.numberOfTrailingZeros(Integer.highestOneBit(length - 1)) + 1;
        return (int) (HEAPSORT_DEPTH_COEFFICIENT * log);
    }

    /**
     * Sorts the specified array in the ascending order.
     * @param a the array to be sorted.
     */
    public static void sort(/*C*/int/*C*/[] a) {
        quickSort(a, 0, a.length, 0, maxQuickSortDepth(a.length));
    }

    /**
     * Sorts the subarray [left, right) of the specified array in the ascending order.
     * @param a the array, which interval [left, right) is to be sorted.
     * @param left the left bound of the range, inclusive
     * @param right the right bound of the range, exclusive
     */
    public static void sort(/*C*/int/*C*/[] a, int left, int right) {
        quickSort(a, left, right, 0, maxQuickSortDepth(right - left));
    }

    private static void quickSort(/*C*/int/*C*/[] a, int left, int right, int depth, int maxDepth) {
        if (right - left <= 1) {
            return;
        }
        if (depth > maxDepth) {
            heapSort(a, left, right - left);
            return;
        }
        final /*C*/int/*C*/ pivot = a[left + rnd.nextInt(right - left)];
        int i = left;
        int j = right - 1;
        do {
            while (a[i] < pivot) i++;
            while (pivot < a[j]) j--;
            if (i <= j) {
                /*C*/int/*C*/ tmp = a[i];
                a[i++] = a[j];
                a[j--] = tmp;
            }
        } while (i <= j);
        quickSort(a, left, j + 1, depth + 1, maxDepth);
        quickSort(a, i, right, depth + 1, maxDepth);
    }

    private static void heapSort(/*C*/int/*C*/[] a, int offset, int size) {
        for (int i = (size >>> 1) - 1; i >= 0; i--) {
            down(a, i, offset, size);
        }
        for (int i = size - 1; i > 0; i--) {
            /*C*/int/*C*/ tmp = a[offset];
            a[offset] = a[offset + i];
            a[offset + i] = tmp;
            down(a, 0, offset, i);
        }
    }

    private static void down(/*C*/int/*C*/[] a, int index, int offset, int size) {
        final /*C*/int/*C*/ element = a[offset + index];
        final int firstLeaf = (size >>> 1);
        while (index < firstLeaf) {
            int largestChild = (index << 1) + 1;
            if (largestChild + 1 < size && a[offset + largestChild + 1] > a[offset + largestChild]) {
                largestChild++;
            }
            if (a[offset + largestChild] <= element) {
                break;
            }
            a[offset + index] = a[offset + largestChild];
            index = largestChild;
        }
        a[offset + index] = element;
    }
}
