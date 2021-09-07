package mckeken.io;

import org.junit.jupiter.api.Test;

public class LogUtilsTest {

    @Test
    public void centerStringEvenOnEven() {

        String s = "TEXT";

        assert LogUtils.centerString(s, 6).equals(" TEXT ");
        assert LogUtils.centerString(s, 8).equals("  TEXT  ");
        assert LogUtils.centerString(s, 1).equals(s);

    }

    @Test
    public void centerStringEvenOnOdd() {

        String s = "TEXT";

        assert LogUtils.centerString(s, 5).equals(" TEXT");
        assert LogUtils.centerString(s, 7).equals("  TEXT ");

    }

    @Test
    public void centerStringOddOnOdd() {

        String s = "TOP";

        assert LogUtils.centerString(s, 5).equals(" TOP ");
        assert LogUtils.centerString(s, 7).equals("  TOP  ");

    }

    @Test
    public void centerStringOddOnEven() {
        String s = "TOP";

        assert LogUtils.centerString(s, 4).equals(" TOP");
        assert LogUtils.centerString(s, 6).equals("  TOP ");
        assert LogUtils.centerString(s, 8).equals("   TOP  ");

    }
}