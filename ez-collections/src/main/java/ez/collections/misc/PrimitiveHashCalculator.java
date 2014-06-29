package ez.collections.misc;

public final class PrimitiveHashCalculator {
    private PrimitiveHashCalculator() {
    }

    public static int getHash(boolean x) {
        return x ? 1 : 0;
    }

    public static int getHash(byte x) {
        return x;
    }

    public static int getHash(short x) {
        return x;
    }

    public static int getHash(char x) {
        return x;
    }

    public static int getHash(int x) {
        return x;
    }

    public static int getHash(long x) {
        return (int)x ^ (int)(x >>> 32);
    }

    public static int getHash(float x) {
        return Float.floatToIntBits(x);
    }

    public static int getHash(double x) {
        long y = Double.doubleToLongBits(x);
        return (int)y ^ (int)(y >>> 32);
    }
}
