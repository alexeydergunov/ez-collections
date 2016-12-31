package ez.collections.tuples;

import ez.collections.misc.PrimitiveHashCalculator;

/**
 * A mutable triple of three primitive types. Its fields are public to speedup the access.
 * @author Alexey Dergunov
 * @since 0.2.0
 */
public class _Ez_Int__Int__Int_Triple implements Comparable<_Ez_Int__Int__Int_Triple> {
    private static final int HASHCODE_INITIAL_VALUE = 0x811c9dc5;
    private static final int HASHCODE_MULTIPLIER = 0x01000193;

    public /*T1*/int/*T1*/ first;
    public /*T2*/int/*T2*/ second;
    public /*T3*/int/*T3*/ third;

    public _Ez_Int__Int__Int_Triple(/*T1*/int/*T1*/ first, /*T2*/int/*T2*/ second, /*T3*/int/*T3*/ third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        _Ez_Int__Int__Int_Triple that = (_Ez_Int__Int__Int_Triple) o;

        return first == that.first && second == that.second && third == that.third;
    }

    @Override
    public int hashCode() {
        int hash = HASHCODE_INITIAL_VALUE;
        hash = (hash ^ PrimitiveHashCalculator.getHash(first)) * HASHCODE_MULTIPLIER;
        hash = (hash ^ PrimitiveHashCalculator.getHash(second)) * HASHCODE_MULTIPLIER;
        hash = (hash ^ PrimitiveHashCalculator.getHash(third)) * HASHCODE_MULTIPLIER;
        return hash;
    }

    @Override
    public int compareTo(_Ez_Int__Int__Int_Triple o) {
        int res = /*W1*/Integer/*W1*/.compare(first, o.first);
        if (res == 0) {
            res = /*W2*/Integer/*W2*/.compare(second, o.second);
            if (res == 0) {
                res = /*W3*/Integer/*W3*/.compare(third, o.third);
            }
        }
        return res;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ", " + third + ")";
    }
}
