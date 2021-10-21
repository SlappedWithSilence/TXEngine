package txengine.ui;

import txengine.main.Manager;
import txengine.ui.color.ColorConsole;

import java.util.Optional;

public class Out {

    public static void d(String s) {
        System.out.println(s);
    }

    /*** Output Methods ***/

    public static void error(String text, String source) {
        if (!Manager.debug) return;
        String sourceText = "";
        Optional<String> opt = Optional.ofNullable(source);

        if (opt.isPresent()) sourceText = "[" + opt.get() + "]";

        ColorConsole.e("[Error]" + sourceText + " " +text + "\n", false);
    }

    public static void error(String text) {
        error(text, null);
    }

    public static void warn(String text,  String source) {
        if (!Manager.debug) return;
        String sourceText = "";

        Optional<String> opt = Optional.ofNullable(source);
        if (opt.isPresent()) sourceText = "[" + opt.get() + "]";

        ColorConsole.i("[Warning]" + sourceText + " " + text + "\n", false);
    }

    public static void info(String text, String source) {
        if (!Manager.debug) return;
        String sourceText = "";

        Optional<String> opt = Optional.ofNullable(source);
        if (opt.isPresent()) sourceText = "[" + opt.get() + "]";

        ColorConsole.i("[Info]" + sourceText + " " + text + "\n", false);
    }

}
