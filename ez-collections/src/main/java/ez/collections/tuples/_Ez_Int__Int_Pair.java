package ez.collections.tuples;

import ez.collections.misc.PrimitiveHashCalculator;

/**
 * A mutable pair of two primitive types. Its fields are public to speedup the access.
 * @author Alexey Dergunov
 * @since 0.1.0
 */
public class _Ez_Int__Int_Pair implements Comparable<_Ez_Int__Int_Pair> {
    private static final int HASHCODE_INITIAL_VALUE = 0x811c9dc5;
    private static final int HASHCODE_MULTIPLIER = 0x01000193;

    public /*T1*/int/*T1*/ first;
    public /*T2*/int/*T2*/ second;

    public _Ez_Int__Int_Pair(/*T1*/int/*T1*/ first, /*T2*/int/*T2*/ second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        _Ez_Int__Int_Pair that = (_Ez_Int__Int_Pair) o;

        return first == that.first && second == that.second;
    }

    @Override
    public int hashCode() {
        int hash = HASHCODE_INITIAL_VALUE;
        hash = (hash ^ PrimitiveHashCalculator.getHash(first)) * HASHCODE_MULTIPLIER;
        hash = (hash ^ PrimitiveHashCalculator.getHash(second)) * HASHCODE_MULTIPLIER;
        return hash;
    }

    @Override
    public int compareTo(_Ez_Int__Int_Pair o) {
        int res = /*W1*/Integer/*W1*/.compare(first, o.first);
        if (res == 0) {
            res = /*W2*/Integer/*W2*/.compare(second, o.second);
        }
        return res;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
