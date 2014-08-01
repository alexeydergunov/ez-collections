package ez.collections.sort;

import ez.collections._Ez_Int_Comparator;

import java.util.Random;

// TODO javadocs for class and public methods
// TODO maybe unite classes Sort, ReverseSort and CustomSort? think about it
public final class _Ez_Int_CustomSort {
    private static final double HEAPSORT_DEPTH_COEFFICIENT = 2.0;

    private static Random rnd = new Random(322);

    private _Ez_Int_CustomSort() {
    }

    private static int maxQuickSortDepth(int length) {
        if (length <= 1) {
            return 0;
        }
        int log = Integer.numberOfTrailingZeros(Integer.highestOneBit(length - 1)) + 1;
        return (int) (HEAPSORT_DEPTH_COEFFICIENT * log);
    }

    public static void sort(/*C*/int/*C*/[] a, _Ez_Int_Comparator cmp) {
        quickSort(a, 0, a.length, cmp, 0, maxQuickSortDepth(a.length));
    }

    public static void sort(/*C*/int/*C*/[] a, int left, int right, _Ez_Int_Comparator cmp) {
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
