package ez.collections.sort;

import ez.collections._Ez_Int_Comparator;

import java.util.Random;

// TODO maybe unite classes Sort, ReverseSort and CustomSort? think about it
/**
 * Provides the sort methods for the custom ordering.
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
 * @see ez.collections._Ez_Int_Comparator
 */
public final class _Ez_Int_CustomSort {
    private static final double HEAPSORT_DEPTH_COEFFICIENT = 2.0;

    private static Random rnd = new Random();

    private _Ez_Int_CustomSort() {
    }

    private static int maxQuickSortDepth(int length) {
        if (length <= 1) {
            return 0;
        }
        int log = Integer.numberOfTrailingZeros(Integer.highestOneBit(length - 1)) + 1;
        return (int) (HEAPSORT_DEPTH_COEFFICIENT * log);
    }

    /**
     * Sorts the specified array in the custom order.
     * @param a the array to be sorted.
     * @param cmp the comparator which defines the order
     */
    public static void sort(/*C*/int/*C*/[] a, _Ez_Int_Comparator cmp) {
        quickSort(a, 0, a.length, cmp, 0, maxQuickSortDepth(a.length));
    }

    /**
     * Sorts the subarray [left, right) of the specified array in the custom order.
     * @param a the array, which interval [left, right) is to be sorted.
     * @param left the left bound of the range, inclusive
     * @param right the right bound of the range, exclusive
     * @param cmp the comparator which defines the order
     * @throws IllegalArgumentException if the range is incorrect (if
     * {@code left > right || left < 0 || right > a.length})
     */
    public static void sort(/*C*/int/*C*/[] a, int left, int right, _Ez_Int_Comparator cmp) {
        if (left > right || left < 0 || right > a.length) {
            throw new IllegalArgumentException(
                    "Incorrect range [" + left + ", " + right + ") was specified for sorting, length = " + a.length);
        }
        quickSort(a, left, right, cmp, 0, maxQuickSortDepth(right - left));
    }

    private static void quickSort(/*C*/int/*C*/[] a, int left, int right, _Ez_Int_Comparator cmp, int depth, int maxDepth) {
        if (right - left <= 1) {
            return;
        }
        if (depth > maxDepth) {
            heapSort(a, left, right - left, cmp);
            return;
        }
        final /*C*/int/*C*/ pivot = a[left + rnd.nextInt(right - left)];
        int i = left;
        int j = right - 1;
        do {
            while (cmp.compare(a[i], pivot) < 0) i++;
            while (cmp.compare(pivot, a[j]) < 0) j--;
            if (i <= j) {
                /*C*/int/*C*/ tmp = a[i];
                a[i++] = a[j];
                a[j--] = tmp;
            }
        } while (i <= j);
        quickSort(a, left, j + 1, cmp, depth + 1, maxDepth);
        quickSort(a, i, right, cmp, depth + 1, maxDepth);
    }

    private static void heapSort(/*C*/int/*C*/[] a, int offset, int size, _Ez_Int_Comparator cmp) {
        // If size <= 1, nothing is executed
        for (int i = (size >>> 1) - 1; i >= 0; i--) {
            down(a, i, offset, size, cmp);
        }
        for (int i = size - 1; i > 0; i--) {
            /*C*/int/*C*/ tmp = a[offset];
            a[offset] = a[offset + i];
            a[offset + i] = tmp;
            down(a, 0, offset, i, cmp);
        }
    }

    private static void down(/*C*/int/*C*/[] a, int index, int offset, int size, _Ez_Int_Comparator cmp) {
        final /*C*/int/*C*/ element = a[offset + index];
        final int firstLeaf = (size >>> 1);
        while (index < firstLeaf) {
            int largestChild = (index << 1) + 1;
            if (largestChild + 1 < size && cmp.compare(a[offset + largestChild + 1], a[offset + largestChild]) > 0) {
                largestChild++;
            }
            if (cmp.compare(a[offset + largestChild], element) <= 0) {
                break;
            }
            a[offset + index] = a[offset + largestChild];
            index = largestChild;
        }
        a[offset + index] = element;
    }
}
