package ez.collections;

import ez.collections.misc.PrimitiveHashCalculator;

/**
 * An interface to define a custom hashing for the elements.
 * @author Alexey Dergunov
 * @since 0.1.1
 * @see ez.collections.hashset._Ez_Int_CustomHashSet
 */
public interface _Ez_Int_Hasher {
    /**
     * A function which returns the hash of the element. It must return the same values for the same input parameters.
     * @param x the element whose hash will be calculated
     * @return the hash of the given element
     */
    int getHash(/*T*/int/*T*/ x);

    /**
     * Default hasher, returns the same values as usual hashCode() methods in Java.
     */
    static final _Ez_Int_Hasher DEFAULT = new _Ez_Int_Hasher() {
        @Override
        public int getHash(/*T*/int/*T*/ x) {
            return PrimitiveHashCalculator.getHash(x);
        }
    };
}
