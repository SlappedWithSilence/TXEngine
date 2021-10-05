package txengine.io.loaders;

import txengine.io.Loader;
import txengine.systems.integration.Requirement;
import txengine.systems.combat.CombatEngine;
import txengine.systems.combat.combatEffect.CombatEffect;
import txengine.systems.item.Consumable;
import txengine.systems.item.Equipment;
import txengine.systems.item.Item;
import txengine.systems.item.effect.Effect;
import txengine.systems.item.effect.EffectFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import txengine.ui.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static txengine.io.LoadUtils.*;

public class ItemLoader extends Loader {

    @Override
    public HashMap<Integer, Item> load(File file) {
        HashMap<Integer, Item> itemList = new HashMap<>();  // Store the item instances mapped to their IDs. Requires a collision checker

        // Read the JSON storage file
        JSONParser parser = new JSONParser();


        JSONObject obj;

        try {
            obj = (JSONObject) parser.parse(new FileReader(file));
        } catch (FileNotFoundException e) {
            LogUtils.error("Attempted to load from file: " + file.getAbsolutePath());
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            LogUtils.error("items.JSON appears to be corrupted. Please re-download it from https://github.com/TopperMcKek/TheAlley/blob/master/thealley/resources/items.json\n");
            e.printStackTrace();
            return null;
        }

        // Get the JSON array that contains all the items
        JSONArray items = (JSONArray) obj.get("Items");
        Iterator<JSONObject> iterator = items.iterator(); // Create an iterator over the list of items

        // Loop through the item JSON objects
        while (iterator.hasNext()) {
            JSONObject rawItem = iterator.next();
            String itemType = asString(rawItem,"type");

            switch (itemType) {
                case "item":
                    try {
                       Item i =loadItem(rawItem);
                        itemList.put(i.getId(), i); // Add the newly-built item to the itemList hashmap
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                // Build a consumable-typed item
                case "consumable":
                    try {
                        Consumable c = loadConsumable(rawItem);

                        itemList.put(c.getId(), c); // Add the newly built consumable to the itemList hashmap
                    } catch (Exception e) { // I don't expect there to be any errors here, so I've not created a robust handling system for them. In general, simply not adding the item to the global hashmap is good enough
                        e.printStackTrace();
                    }
                    break;

                case "equipment" :
                    try {
                        Equipment e = loadEquipment(rawItem);

                        itemList.put(e.getId(), e); // Add the newly built consumable to the itemList hashmap
                    } catch (Exception e) { // I don't expect there to be any errors here, so I've not created a robust handling system for them. In general, simply not adding the item to the global hashmap is good enough
                        e.printStackTrace();
                    }
                    break;

                default: // There should never be an item of a type that isn't expected. Therefore, crashing is necessary to ensure the integrity of the JSON data.
                    throw new IllegalArgumentException("Invalid item type:" + itemType);
            }
        }

        return itemList;
    }

    private Consumable loadConsumable(JSONObject rawItem) {
        // Get standard values
        String name = asString(rawItem,"name");
        String desc = asString(rawItem,"description");
        int id = asInt(rawItem, "id");
        int value = optional(rawItem, "value", Long.class, 0L ).intValue();
        int maxStacks = optional(rawItem, "maxStacks", Long.class, 10L ).intValue();

        // Begin loading effects
        ArrayList<Effect> effects = new ArrayList<>();            // The list that will hold the effects
        JSONArray jsonEffects = (JSONArray) rawItem.get("effects");        // Grab the JSON array of effects
        Iterator<JSONObject> effectsIterator = jsonEffects.iterator();    // Generate an iterator for the JSONArray that holds the effect data

        while (effectsIterator.hasNext()) {                                // Iterate through the JSONArray that holds the effect data
            JSONObject rawEffect = effectsIterator.next();
            String effectName = (String) rawEffect.get("class_name");    // Get the class name of the effect we're instantiating
            JSONArray propertiesArray = (JSONArray) rawEffect.get("properties");    // Get a sub-array of property integer values for the current effect

            String[] effectProperties = new String[propertiesArray.size()];        // Create a java-array to store the effect's property values
            for (int i = 0; i < propertiesArray.size(); i++) {                        // Iterate through the JSONArray that stores the property values
                String prop = ((String) propertiesArray.get(i));                // Collect a property values and convert it to an int.
                effectProperties[i] = prop;                                            // Store it in the array
            }

            effects.add(EffectFactory.build(effectName, effectProperties));        // Generate an instance of the effect class and assign the collected properties to it, then add it to the arraylist
        }

        return new Consumable(name, desc, id, value, maxStacks, effects);
    }

    private Item loadItem(JSONObject rawItem) {
        String name = asString(rawItem,"name");
        String desc = asString(rawItem,"description");
        int id = asInt(rawItem, "id");
        int value = optional(rawItem, "value", Long.class, 0L ).intValue();
        int maxStacks = optional(rawItem, "maxStacks", Long.class, 1L ).intValue();

        return new Item(name, desc, id, value, maxStacks);
    }

    private Equipment loadEquipment(JSONObject rawItem) {
        // Get standard values
        String name = asString(rawItem,"name");
        String desc = asString(rawItem,"description");
        int id = asInt(rawItem, "id");
        int value = optional(rawItem, "value", Long.class, 0L ).intValue();
        int maxStacks = optional(rawItem, "maxStacks", Long.class, 1L ).intValue();

        // Load Equipment-specific fields
        Equipment.EquipmentType type = Equipment.EquipmentType.valueOf(asString(rawItem,"equipment_type"));
        int damage = optional(rawItem, "damage", Long.class, 0L ).intValue();
        int defense = optional(rawItem, "defense", Long.class, 0L ).intValue();

        // Begin loading effects
        ArrayList<AbstractMap.SimpleEntry<CombatEffect, CombatEngine.CombatPhase>> effects = parseCombatEffects( optional(rawItem,"combat_effects",JSONArray.class, new JSONArray()));

        // Load tags
        List<AbstractMap.SimpleEntry<String, Float>> tagResistances = parseStringFloatPairs( optional(rawItem,"tag_resistances",JSONArray.class, new JSONArray()));

        // Load Requirements
        List<Requirement> requirements = parseRequirements( optional(rawItem,"requirements",JSONArray.class, new JSONArray()));

        //Load Abilities
        List<String> abilityNames = List.of(getStringArray( optional(rawItem,"abilities",JSONArray.class, new JSONArray())));

        //System.out.println(name + " " + desc + " " + id + " " + value + " " + type);
        return new Equipment(name, desc, id, value, maxStacks, effects, tagResistances, requirements, abilityNames, type, damage, defense);
    }
}
