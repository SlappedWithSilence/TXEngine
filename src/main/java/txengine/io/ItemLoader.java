package txengine.io;

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

public class ItemLoader implements Loader {

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
            String itemType = (String) rawItem.get("type");

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
        String name = (String) rawItem.get("name");
        String desc = (String) rawItem.get("description");
        int id = ((Long) rawItem.get("id")).intValue();
        int value = ((Long) rawItem.get("value")).intValue();
        int maxStacks = ((Long) rawItem.get("maxStacks")).intValue();

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
        String name = (String) rawItem.get("name");
        String desc = (String) rawItem.get("description");
        int id = ((Long) rawItem.get("id")).intValue();
        int value = ((Long) rawItem.get("value")).intValue();
        int maxStacks = ((Long) rawItem.get("maxStacks")).intValue();

        return new Item(name, desc, id, value, maxStacks);
    }

    private Equipment loadEquipment(JSONObject rawItem) {
        // Get standard values
        String name = (String) rawItem.get("name");
        String desc = (String) rawItem.get("description");
        int id = ((Long) rawItem.get("id")).intValue();
        int value = ((Long) rawItem.get("value")).intValue();
        int maxStacks = ((Long) rawItem.get("maxStacks")).intValue();

        // Load Equipment-specific fields
        Equipment.EquipmentType type = Equipment.EquipmentType.valueOf((String) rawItem.get("equipment_type"));
        int damage = ((Long) rawItem.get("damage")).intValue();
        int defense = ((Long) rawItem.get("defense")).intValue();

        // Begin loading effects
        ArrayList<AbstractMap.SimpleEntry<CombatEffect, CombatEngine.CombatPhase>> effects = LoadUtils.parseCombatEffects((JSONArray) rawItem.get("combat_effects"));

        // Load tags
        List<AbstractMap.SimpleEntry<String, Float>> tagResistances = LoadUtils.parseStringFloatPairs((JSONArray) rawItem.get("tag_resistances"));

        // Load Requirements
        List<Requirement> requirements = LoadUtils.parseRequirements((JSONArray) rawItem.get("requirements"));

        List<String> abilityNames = List.of(LoadUtils.getStringArray((JSONArray) rawItem.get("abilities")));

        return new Equipment(name, desc, id, value, maxStacks, effects, tagResistances, requirements, abilityNames, type, damage, defense);
    }
}
