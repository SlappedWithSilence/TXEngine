package txengine.io;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import txengine.integration.Requirement;
import txengine.integration.RequirementFactory;
import txengine.systems.combat.CombatEngine;
import txengine.systems.combat.combatEffect.CombatEffect;
import txengine.systems.combat.combatEffect.CombatEffectFactory;
import txengine.systems.event.Event;
import txengine.systems.event.EventFactory;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

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

    public static ArrayList<AbstractMap.SimpleEntry<CombatEffect, CombatEngine.CombatPhase>> parseCombatEffects(JSONArray obj) {
        ArrayList<AbstractMap.SimpleEntry<CombatEffect, CombatEngine.CombatPhase>> arr = new ArrayList<>();

        for (int i = 0; i < obj.size(); i++) {
            JSONObject rawEffect = (JSONObject) obj.get(i);

            String className = (String) rawEffect.get("class_name");
            int duration = ((Long) rawEffect.get("duration")).intValue();
            String triggerPhase = (String) rawEffect.get("trigger_phase");
            String triggerMessage = (String) rawEffect.get("trigger_message");
            String cleanupMessage = (String) rawEffect.get("cleanup_message");
            String[] properties = getStringArray((JSONArray) rawEffect.get("properties"));

            CombatEffect ce = CombatEffectFactory.build(className, duration, triggerMessage, cleanupMessage, properties);

            arr.add(new AbstractMap.SimpleEntry<>(ce, CombatEngine.CombatPhase.valueOf(triggerPhase)));
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

    public static ArrayList<AbstractMap.SimpleEntry<String, Integer>> parseResourceCosts(JSONArray obj) {
        ArrayList<AbstractMap.SimpleEntry<String, Integer>> arr = new ArrayList<>();

        // The array comes in pairs of strings and ints. That means "i" is a string, and "i+1" is an int.
        for (int i = 0; i < obj.size(); i = i+2) { // Iterate over every even index in the array

            String resourceName = (String) obj.get(i); // Get the resource name as a string
            int resourceQuantity = ((Long) obj.get(i+1)).intValue(); // Get the related resource quantity as an int

            arr.add(new AbstractMap.SimpleEntry<>(resourceName, resourceQuantity)); // add the string-int pair to the arraylist

        }

        return arr;
    }

    public static List<AbstractMap.SimpleEntry<String, Float>> parseStringFloatPairs(JSONArray obj) {
        List<AbstractMap.SimpleEntry<String, Float>> arr = new ArrayList<>();

        for (Object o : obj) {
            String[] values = ((String) o).split(",");

            arr.add(new AbstractMap.SimpleEntry<>(values[0], Float.parseFloat(values[1])));
        }

        return arr;
    }

}
