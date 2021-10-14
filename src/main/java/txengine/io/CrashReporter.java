package txengine.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CrashReporter {

    private static CrashReporter crashReporter;
    private final static String fileName = "crash-report.txt";

    private StringBuilder stringBuilder;

    public CrashReporter() {
        stringBuilder = new StringBuilder();
    }

    public CrashReporter append(String s) {
        stringBuilder.append(s);
        return this;
    }

    public CrashReporter append(StringBuilder s) {
        stringBuilder.append(s);
        return this;
    }

    public void write() {
        try {
            File f = new File(fileName);
            f.createNewFile();
            FileWriter fw = new FileWriter(f);
            fw.write(stringBuilder.toString());
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String fileName) {
        try {
            File f = new File(fileName);
            f.createNewFile();
            FileWriter fw = new FileWriter(f);
            fw.write(stringBuilder.toString());
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CrashReporter clear() {
        stringBuilder = new StringBuilder();
        return this;
    }

    public static CrashReporter getInstance() {
        if (crashReporter == null) crashReporter = new CrashReporter();
        return crashReporter;
    }

}
