package txengine.io;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import txengine.structures.Pair;
import txengine.systems.integration.Requirement;
import txengine.systems.integration.RequirementFactory;
import txengine.systems.combat.CombatEngine;
import txengine.systems.combat.combatEffect.CombatEffect;
import txengine.systems.combat.combatEffect.CombatEffectFactory;
import txengine.systems.event.Event;
import txengine.systems.event.EventFactory;
import txengine.ui.LogUtils;
import txengine.ui.Out;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoadUtils {

    public static List<Event> parseEvents(JSONArray object) {
        List<Event> events = new ArrayList<>();
        for (Object o : object) {
            events.add(parseEvent((JSONObject) o));
        }

        return events;
    }

    public static Event parseEvent(JSONObject rawEvent) {
        String className = (String) rawEvent.get("class_name");
        JSONArray rawProperties = (JSONArray) rawEvent.get("properties");
        String[] properties = getStringArray(rawProperties);

        return EventFactory.build(className, properties);
    }

    public static List<Requirement> parseRequirements(JSONArray rawRequirementArray) {
        List<Requirement> requirements = new ArrayList<>();

        for (int i = 0; i < rawRequirementArray.size(); i++) {
            JSONObject rawRequirement = (JSONObject) rawRequirementArray.get(i);
            String className = (String) rawRequirement.get("class_name");
            String[] properties = getStringArray((JSONArray) rawRequirement.get("properties"));

            requirements.add(RequirementFactory.build(className, properties));
        }

        return requirements;
    }

    public static ArrayList<Pair<CombatEffect, CombatEngine.CombatPhase>> parseCombatEffects(JSONArray obj) {
        ArrayList<Pair<CombatEffect, CombatEngine.CombatPhase>> arr = new ArrayList<>();

        for (int i = 0; i < obj.size(); i++) {
            JSONObject rawEffect = (JSONObject) obj.get(i);

            String className = (String) rawEffect.get("class_name");
            int duration = ((Long) rawEffect.get("duration")).intValue();
            String triggerPhase = (String) rawEffect.get("trigger_phase");
            String triggerMessage = (String) rawEffect.get("trigger_message");
            String cleanupMessage = (String) rawEffect.get("cleanup_message");
            String[] properties = getStringArray((JSONArray) rawEffect.get("properties"));
            String[] tags = LoadUtils.getStringArray(optional(rawEffect,"tags",JSONArray.class, new JSONArray()));
            CombatEffect ce = CombatEffectFactory.build(className, duration, triggerMessage, cleanupMessage, properties);

            if (tags == null) ce.setTags(new String[0]);
            else ce.setTags(tags);

            arr.add(new Pair<>(ce, CombatEngine.CombatPhase.valueOf(triggerPhase)));
        }

        return arr;
    }

    public static String[] getStringArray(JSONArray stringArray) {
        String[] array = new String[stringArray.size()];

        for (int i = 0; i < stringArray.size(); i++) {
            array[i] = (String) stringArray.get(i);
        }

        return array;
    }

    public static int[] getIntArray(JSONArray intArray) {
        int[] array = new int[intArray.size()];

        for (int i = 0; i < intArray.size(); i++) {
            array[i] = ((Long) intArray.get(i)).intValue();
        }

        return array;
    }

    public static List<Pair<String, Integer>> parseResourceCosts(JSONArray obj) {
        return parseStringIntPairs(obj);
    }

    public static List<Pair<String, Float>> parseStringFloatPairs(JSONArray obj) {
        List<Pair<String, Float>> arr = new ArrayList<>();

        for (Object o : obj) {
            String[] values = ((String) o).split(",");

            arr.add(new Pair<>(values[0], Float.parseFloat(values[1])));
        }

        return arr;
    }

    public static List<Pair<String, Integer>> parseStringIntPairs(JSONArray obj) {
        List<Pair<String, Integer>> arr = new ArrayList<>();

        if (obj.isEmpty()) return arr;

        for (Object o : obj) {
            String[] values = ((String) o).split(",");

            arr.add(new Pair<>(values[0], Integer.parseInt(values[1])));
        }

        return arr;
    }

    public static List<Pair<Integer, Integer>> parseIntPairs(JSONArray obj) {
        List<Pair<Integer, Integer>> arr = new ArrayList<>();

        for (Object o : obj) {
            String[] values = ((String) o).split(",");

            arr.add(new Pair<>(Integer.parseInt(values[0]), Integer.parseInt(values[1])));
        }

        return arr;
    }

    // Get a value from a JSONObject, convert it to a string, and verify that it matches the given regex patter.
    // If anything goes wrong, return null
    public static String asString(final JSONObject obj, final String key, final String pattern) {
        String str = null;

        try {
            str = (String) obj.get(key);

        } catch (Exception e) {
            Out.error(key + " is not a String!", "Loader, " + key);
        }

        Pattern p;
        try {
            p = Pattern.compile(pattern);
        } catch (Exception e) {
            Out.error(pattern + " is not a valid regex!", "Loader, " + key);
            return null;
        }

        if (str == null) {
            Out.error("No field " + key + " found!", "Loader, " + key);
            return null;
        }

        if (!p.matcher(str).matches()) {
            Out.warn(str + " doesn't match " + pattern, "Loader, " + key);
        }

        return str;
    }

    // Get a value from a JSONObject, convert it to a string
    public static String asString(final JSONObject obj, final String key) {
        String str = null;

        try {
             str = (String) obj.get(key);
        } catch (Exception e) {
            Out.error(key + " is not a String!","Loader, " + key);
        }

        if (str == null) Out.error("No field " + key + " found!", "Loader, " + key);

        return str;
    }

    // Get a value from a JSONObject, convert it to an Integer
    public static Integer asInt(final JSONObject obj, final String key) {
        Integer i = null;

        try {
            i = ((Long) obj.get(key)).intValue();
        } catch (Exception e) {
            Out.error(key + " is not an int!", "Loader, " + key);
        }

        if (i == null) Out.error("No field " + key + " found!", "Loader, " + key);

        return i;
    }

    // Get a value from a JSONObject, convert it to a Double
    public static Double asDouble(final JSONObject obj, final String key) {
        Double d = null;

        try {
             d = (Double) obj.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            Out.error(key + " is not a double!", "Loader, " + key);
            return null;
        }

        if (d == null) Out.error("No field " + key + " found!", "Loader, " + key);

        return d;
    }

    public static <T> T optional(final JSONObject obj, final String key, Class<T> tClass, T defaultValue) {
        T value = null;
        try {
            value = (T) obj.get(key);
        }  catch (Exception e) {
            Out.warn("Defaulting to value " + defaultValue, "Loader, " + key);
        }

        if (value != null) return value;

        Out.warn("Defaulting to value " + defaultValue, "Loader, " + key);
        return defaultValue;
    }
}
