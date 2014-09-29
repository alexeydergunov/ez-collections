package ez.collections.tuples;

import org.testng.Assert;
import org.testng.annotations.Test;

public class EzPairTest {
    @Test
    public void testCreateAndSet() {
        _Ez_Int__Int_Pair p = new _Ez_Int__Int_Pair(3, 5);
        Assert.assertEquals(p.first, 3);
        Assert.assertEquals(p.second, 5);

        p.first = -2;
        Assert.assertEquals(p.first, -2);
        Assert.assertEquals(p.second, 5);

        p.second = 0;
        Assert.assertEquals(p.first, -2);
        Assert.assertEquals(p.second, 0);
    }

    @SuppressWarnings({"EqualsBetweenInconvertibleTypes", "ConstantConditions"})
    @Test
    public void testEqualsAndHashcode() {
        _Ez_Int__Int_Pair a = new _Ez_Int__Int_Pair(12, -26);
        _Ez_Int__Int_Pair b = new _Ez_Int__Int_Pair(12, -26);
        _Ez_Int__Int_Pair c = new _Ez_Int__Int_Pair(-26, 12);
        _Ez_Int__Int_Pair d = null;
        String s = "(12, -26)";

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
    public void testToString() {
        Assert.assertEquals(new _Ez_Int__Int_Pair(0, 0).toString(), "(0, 0)");

        Assert.assertEquals(new _Ez_Int__Int_Pair(13, 0).toString(), "(13, 0)");
        Assert.assertEquals(new _Ez_Int__Int_Pair(0, 82).toString(), "(0, 82)");
        Assert.assertEquals(new _Ez_Int__Int_Pair(-13, 0).toString(), "(-13, 0)");
        Assert.assertEquals(new _Ez_Int__Int_Pair(0, -82).toString(), "(0, -82)");

        Assert.assertEquals(new _Ez_Int__Int_Pair(13, 82).toString(), "(13, 82)");
        Assert.assertEquals(new _Ez_Int__Int_Pair(-13, 82).toString(), "(-13, 82)");
        Assert.assertEquals(new _Ez_Int__Int_Pair(13, -82).toString(), "(13, -82)");
        Assert.assertEquals(new _Ez_Int__Int_Pair(-13, -82).toString(), "(-13, -82)");
    }
}
