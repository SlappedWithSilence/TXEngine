package txengine.main;

import txengine.ui.LogUtils;
import txengine.ui.Out;

import java.util.*;
import java.util.regex.Pattern;


// A class for parsing run time arguments
// must follow these rules:
// - <ARG_KEY> <ARG_VALUE> <ARG_KEY>... etc.
// An arg key must be followed

public class ArgsHandler {

    private static ArgsHandler argsHandler;

    private static final String ARG_REGEX = "-[a-zA-Z]+";

    private HashMap<String, List<String>> argumentMap; // Maps a given args key to whatever args values come after it
    private HashMap<String, List<Handler>> handlers; // Maps a given args key to whatever handlers are registered to it
    private HashMap<String, List<Handler>> lateHandlers; // Maps a given args key to handlers that need to run after initialization is complete

    public ArgsHandler() {
        argumentMap = new HashMap<>();
        handlers = new HashMap<>();
        lateHandlers = new HashMap<>();
    }

    public void parseArgs(String[] s) {
        String lastKey = null;

        Pattern argPattern = Pattern.compile(ARG_REGEX);
        List<String> args = List.of(s);
        Iterator<String> iter = args.iterator();

        while (iter.hasNext()){
            String curArg =  iter.next(); // Iterate over each string in the args

            // Case: An argument key is passed
            if (argPattern.matcher(curArg).matches() && !argumentMap.containsKey(curArg)) { // Check if the current argument is a key
                argumentMap.put(curArg, new ArrayList<>());
                lastKey = curArg;
            } else if (argumentMap.containsKey(curArg)) {
                Out.error("Argument \"" + curArg + "\" already passed!","ArgsHandler");
                throw new IllegalArgumentException();
            }

            // Case: an argument value is passed
            else {
                // Case: a value was passed with no preceding key
                if (lastKey == null) {
                    Out.error("Value \"" + curArg + "\" was passed with no key!","ArgsHandler");
                    throw new IllegalArgumentException();
                }
                // Case: a value was passed with a valid preceding key
                argumentMap.get(lastKey).add(curArg); // Add the current argument to the list of values associated with the previous key
            }
        }
    }

    public void run() {
        for (String key : argsHandler.argumentMap.keySet()) {
            if (!handlers.containsKey(key)) continue;
            ArrayList<String> values = new ArrayList<>(argumentMap.get(key));
            for (Handler h : handlers.get(key)) h.handle(values);
        }
    }

    public void runLate() {
        for (String key : argsHandler.argumentMap.keySet()) {
            if (!lateHandlers.containsKey(key)) continue;

            ArrayList<String> values = new ArrayList<>(argumentMap.get(key));
            for (Handler h : lateHandlers.get(key)) h.handle(values);
        }
    }

    public void registerHandler(String key, Handler h) {
        handlers.computeIfAbsent(key, k -> new ArrayList<>());
        handlers.get(key).add(h);
    }

    public void registerLateHandler(String key, Handler h) {
        lateHandlers.computeIfAbsent(key, k -> new ArrayList<>());
        lateHandlers.get(key).add(h);
    }

    public static ArgsHandler getInstance() {
        if (argsHandler == null) argsHandler = new ArgsHandler();
        return argsHandler;
    }

}