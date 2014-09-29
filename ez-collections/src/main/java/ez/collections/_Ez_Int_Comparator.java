package ez.collections;

/**
 * An analogue of {@link java.util.Comparator}, an interface which is used to define a custom ordering for the elements.
 * @author Alexey Dergunov
 * @since 0.0.1
 * @see ez.collections.sort._Ez_Int_Sort
 * @see ez.collections.heap._Ez_Int_CustomHeap
 * @see ez.collections.treeset._Ez_Int_CustomTreeSet
 */
public interface _Ez_Int_Comparator {
    /**
     * A comparison function. It accepts two parameters {@code a} and {@code b} and must return a negative integer if
     * {@code a < b} (in terms of your custom ordering), a positive integer if {@code a > b}, and a zero if they are
     * equal.
     * @param a the first argument
     * @param b the second argument
     * @return a negative integer if {@code a < b}, a positive integer if {@code a > b}, and a zero if they are equal
     */
    int compare(/*C*/int/*C*/ a, /*C*/int/*C*/ b);
}
