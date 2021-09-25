package txengine.io;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public abstract class Loader {

     enum STRING_CLASS {
         STRING_PAIR,
         STRING_INT_PAIR,
         STRING_DOUBLE_PAIR
    }

    String getPattern(STRING_CLASS cls) {
        switch (cls) {
            case STRING_PAIR -> {return ".+,.+";}
            case STRING_INT_PAIR -> {return "[0-9]+,[0-9]+";}
            case STRING_DOUBLE_PAIR -> {return ".+,[0-9]*\\.[0-9]+";}
            default -> {return null;}
        }
    }

    public abstract Map load(File file);
}
