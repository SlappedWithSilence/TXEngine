package txengine.ui;

import txengine.main.Manager;
import txengine.ui.color.ColorConsole;

import java.util.Optional;

public class Out {
    // 0 = error only
    // 1 = warning + error
    // 2 = info + warning + error
    private static int verbosity = 0;

    /*** Output Methods ***/

    // Standard Dialog
    public static void d(String s) {
        System.out.println(s);
    }

    public static void error(String text, String source) {
        String sourceText = "";
        Optional<String> opt = Optional.ofNullable(source);

        if (opt.isPresent()) sourceText = "[" + opt.get() + "]";

        ColorConsole.e("[Error]" + sourceText + " " +text + "\n", false);
    }

    public static void error(String text) {
        error(text, null);
    }

    public static void warn(String text,  String source) {
        if (verbosity < 1) return;
        String sourceText = "";

        Optional<String> opt = Optional.ofNullable(source);
        if (opt.isPresent()) sourceText = "[" + opt.get() + "]";

        ColorConsole.i("[Warning]" + sourceText + " " + text + "\n", false);
    }

    public static void info(String text, String source) {
        if (verbosity < 2) return;
        String sourceText = "";

        Optional<String> opt = Optional.ofNullable(source);
        if (opt.isPresent()) sourceText = "[" + opt.get() + "]";

        ColorConsole.i("[Info]" + sourceText + " " + text + "\n", false);
    }

    /*** Helper Methods ***/
    public static void setVerbosity(int v) {
        verbosity = v;
    }

}
