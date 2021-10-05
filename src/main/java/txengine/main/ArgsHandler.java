package txengine.main;

import txengine.ui.LogUtils;

import java.util.*;
import java.util.regex.Pattern;


// A class for parsing run time arguments
// must follow these rules:
// - <ARG_KEY> <ARG_VALUE> <ARG_KEY>... etc.
// An arg key must be followed

public class ArgsHandler {

    private static ArgsHandler argsHandler;

    private static final String ARG_REGEX = "-[a-zA-Z]+";

    private HashMap<String, List<String>> argsParams; // Maps a given args key to whatever args values come after it
    private HashMap<String, List<Handler>> paramHandlers; // Maps a given args key to whatever handlers are registered to it

    public ArgsHandler() {
        argsParams = new HashMap<>();
        paramHandlers = new HashMap<>();
    }

    public void parseArgs(String[] s) {
        String lastKey = null;

        Pattern argPattern = Pattern.compile(ARG_REGEX);
        List<String> args = List.of(s);
        Iterator<String> iter = args.iterator();

        while (iter.hasNext()){
            String curArg =  iter.next(); // Iterate over each string in the args

            // Case: An argument key is passed
            if (argPattern.matcher(curArg).matches() && !argsParams.containsKey(curArg)) { // Check if the current argument is a key
                argsParams.put(curArg, new ArrayList<>());
                lastKey = curArg;
            } else if (argsParams.containsKey(curArg)) {
                LogUtils.error("Argument \"" + curArg + "\" already passed!","ArgsHandler");
                throw new IllegalArgumentException();

            }

            // Case: an argument value is passed
            else {
                // Case: a value was passed with no preceding key
                if (lastKey == null) {
                    LogUtils.error("Value \"" + curArg + "\" was passed with no key!","ArgsHandler");
                    throw new IllegalArgumentException();
                }
                // Case: a value was passed with a valid preceding key
                argsParams.get(lastKey).add(curArg); // Add the current argument to the list of values associated with the previous key
            }
        }
    }

    public void run() {
        for (String key : argsHandler.argsParams.keySet()) {
            ArrayList<String> values = new ArrayList<>(argsParams.get(key));
            for (Handler h : paramHandlers.get(key)) h.handle(values);
        }
    }

    public void registerHandler(String key, Handler h) {
        paramHandlers.computeIfAbsent(key, k -> new ArrayList<>());
        paramHandlers.get(key).add(h);
    }

    public static ArgsHandler getInstance() {
        if (argsHandler == null) argsHandler = new ArgsHandler();
        return argsHandler;
    }

}