package txengine.io.load;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyTags {

    // Commonly-used tags
    public static final String hostileMarker = toPropertyTag("HOSTILE");
    public static final String friendlyMarker = toPropertyTag("FRIENDLY");
    public static final String rewardsMarker = toPropertyTag("REWARDS");
    public  static final String seedMarker = toPropertyTag("SEED");
    public static final String lootMarker = toPropertyTag("LOOT");

    // Utility Methods
    public static String toPropertyTag(String str) {
        return "{" + str.replace(" ", "") + "}";
    }

    public static boolean isPropertyTag(String str) {
        if (str == null) return false;
        if (str.length() < 3) return false;
        return str.charAt(0) == '{' && str.charAt(str.length() - 1) == '}';
    }

    public static Map<String, List<String>> getMarkedProperties(String[] properties) {
        Map<String, List<String>> propertiesMap = new HashMap<>();

        String mode = null;

        for (String s : properties) {
            if (isPropertyTag(s)) {
                if (!propertiesMap.containsKey(s)) propertiesMap.put(s, new ArrayList<>());
                mode = s;
            }
            else if (isPropertyTag(mode)) {
                propertiesMap.get(mode).add(s);
            }
        }

        return propertiesMap;
    }

}
