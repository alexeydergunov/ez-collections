package ez.collections.tuples;

import org.testng.Assert;
import org.testng.annotations.Test;

public class EzTripleTest {
    @Test
    public void testCreateAndSet() {
        _Ez_Int__Int__Int_Triple t = new _Ez_Int__Int__Int_Triple(3, 5, 8);
        Assert.assertEquals(t.first, 3);
        Assert.assertEquals(t.second, 5);
        Assert.assertEquals(t.third, 8);

        t.first = -2;
        Assert.assertEquals(t.first, -2);
        Assert.assertEquals(t.second, 5);
        Assert.assertEquals(t.third, 8);

        t.second = 0;
        Assert.assertEquals(t.first, -2);
        Assert.assertEquals(t.second, 0);
        Assert.assertEquals(t.third, 8);

        t.third = 7;
        Assert.assertEquals(t.first, -2);
        Assert.assertEquals(t.second, 0);
        Assert.assertEquals(t.third, 7);
    }

    @SuppressWarnings({"EqualsBetweenInconvertibleTypes", "ConstantConditions"})
    @Test
    public void testEqualsAndHashcode() {
        _Ez_Int__Int__Int_Triple a = new _Ez_Int__Int__Int_Triple(12, -26, 5);
        _Ez_Int__Int__Int_Triple b = new _Ez_Int__Int__Int_Triple(12, -26, 5);
        _Ez_Int__Int__Int_Triple c = new _Ez_Int__Int__Int_Triple(12, 5, -26);
        _Ez_Int__Int__Int_Triple d = null;
        String s = "(12, -26, 5)";

        Assert.assertTrue(a.equals(b));
        Assert.assertFalse(a.equals(c));
        Assert.assertFalse(a.equals(d));
        Assert.assertFalse(a.equals(s));

        Assert.assertTrue(b.equals(a));
        Assert.assertFalse(b.equals(c));
        Assert.assertFalse(b.equals(d));
        Assert.assertFalse(b.equals(s));

        Assert.assertFalse(c.equals(a));
        Assert.assertFalse(c.equals(b));
        Assert.assertFalse(c.equals(d));
        Assert.assertFalse(c.equals(s));

        Assert.assertTrue(a.hashCode() == b.hashCode());

        // no way they are equal
        Assert.assertFalse(a.hashCode() == c.hashCode());
        Assert.assertFalse(b.hashCode() == c.hashCode());
    }

    @Test
    public void testCompare() {
        final int[] values = {
                Integer.MIN_VALUE, Integer.MIN_VALUE + 1, Integer.MIN_VALUE + 2, Integer.MIN_VALUE + 3,
                -3, -2, -1, 0, 1, 2, 3,
                Integer.MAX_VALUE - 3, Integer.MAX_VALUE - 2, Integer.MAX_VALUE - 1, Integer.MAX_VALUE
        };
        final int n = values.length;
        _Ez_Int__Int__Int_Triple[] arr = new _Ez_Int__Int__Int_Triple[n * n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    arr[i*n*n + j*n + k] = new _Ez_Int__Int__Int_Triple(values[i], values[j], values[k]);
                }
            }
        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                int cmp = arr[i].compareTo(arr[j]);
                if (i < j) {
                    Assert.assertTrue(cmp < 0);
                } else if (i > j) {
                    Assert.assertTrue(cmp > 0);
                } else {
                    Assert.assertEquals(cmp, 0);
                }
            }
        }
    }

    @Test
    public void testToString() {
        Assert.assertEquals(new _Ez_Int__Int__Int_Triple(0, 0, 0).toString(), "(0, 0, 0)");

        Assert.assertEquals(new _Ez_Int__Int__Int_Triple(-13, 82, 0).toString(), "(-13, 82, 0)");
        Assert.assertEquals(new _Ez_Int__Int__Int_Triple(13, 0, 11).toString(), "(13, 0, 11)");
        Assert.assertEquals(new _Ez_Int__Int__Int_Triple(0, 82, 11).toString(), "(0, 82, 11)");
        Assert.assertEquals(new _Ez_Int__Int__Int_Triple(0, 0, 11).toString(), "(0, 0, 11)");
        Assert.assertEquals(new _Ez_Int__Int__Int_Triple(0, 82, 0).toString(), "(0, 82, 0)");
        Assert.assertEquals(new _Ez_Int__Int__Int_Triple(-13, 0, 0).toString(), "(-13, 0, 0)");

        Assert.assertEquals(new _Ez_Int__Int__Int_Triple(13, 82, 11).toString(), "(13, 82, 11)");
        Assert.assertEquals(new _Ez_Int__Int__Int_Triple(-13, 82, 11).toString(), "(-13, 82, 11)");
        Assert.assertEquals(new _Ez_Int__Int__Int_Triple(13, -82, 11).toString(), "(13, -82, 11)");
        Assert.assertEquals(new _Ez_Int__Int__Int_Triple(-13, -82, 11).toString(), "(-13, -82, 11)");
        Assert.assertEquals(new _Ez_Int__Int__Int_Triple(13, 82, -11).toString(), "(13, 82, -11)");
        Assert.assertEquals(new _Ez_Int__Int__Int_Triple(-13, 82, -11).toString(), "(-13, 82, -11)");
        Assert.assertEquals(new _Ez_Int__Int__Int_Triple(13, -82, -11).toString(), "(13, -82, -11)");
        Assert.assertEquals(new _Ez_Int__Int__Int_Triple(-13, -82, -11).toString(), "(-13, -82, -11)");
    }
}
