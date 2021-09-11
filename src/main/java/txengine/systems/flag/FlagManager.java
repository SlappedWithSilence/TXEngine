package txengine.systems.flag;

import java.util.HashMap;

public class FlagManager {

    private static HashMap<String, Boolean> flagMap;

    public FlagManager() {
        flagMap = new HashMap<String, Boolean>();
    }

    FlagManager (HashMap<String, Boolean> flagMap) {
        setFlagMap(flagMap);
    }

    public static void setFlag(String flagName, Boolean val) {flagMap.put(flagName, val);}

    public Boolean getFlag(String key) {
        if (flagMap.containsKey(key)) return flagMap.get(key);

        return null;
    }

    public boolean hasFlag(String key) {
        return flagMap.containsKey(key);
    }

    public static HashMap<String, Boolean> getFlagMap() {
        return flagMap;
    }

    public static void setFlagMap(HashMap<String, Boolean> flagMap) {
        FlagManager.flagMap = flagMap;
    }
}
