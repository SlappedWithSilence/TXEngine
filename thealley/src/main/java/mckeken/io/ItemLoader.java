package mckeken.io;

import mckeken.item.Consumable;
import mckeken.item.Item;
import mckeken.item.effect.Effect;
import mckeken.item.effect.EffectFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ItemLoader implements Loader {

    @Override
    public HashMap<Integer, Item> load(File file) {
        HashMap<Integer, Item> itemList = new HashMap<Integer, Item>();  // Store the item instances mapped to their IDs. Requires a collision checker

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
                        String name = (String) rawItem.get("name");
                        String desc = (String) rawItem.get("description");
                        int id = ((Long) rawItem.get("id")).intValue();
                        int value = ((Long) rawItem.get("value")).intValue();
                        int maxStacks = ((Long) rawItem.get("maxStacks")).intValue();

                        Item i = new Item(name, desc, id, value, maxStacks); // Build the item from the read values

                        itemList.put(id, i); // Add the newly build item to the itemList hashmap
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                // Build a consumable-typed item
                case "consumable":
                    try {
                        // Get standard values
                        String name = (String) rawItem.get("name");
                        String desc = (String) rawItem.get("description");
                        int id = ((Long) rawItem.get("id")).intValue();
                        int value = ((Long) rawItem.get("value")).intValue();
                        int maxStacks = ((Long) rawItem.get("maxStacks")).intValue();

                        // Begin loading effects
                        ArrayList<Effect> effects = new ArrayList<Effect>();            // The list that will hold the effects
                        JSONArray jsonEffects = (JSONArray) rawItem.get("effects");        // Grab the JSON array of effects
                        Iterator<JSONObject> effectsIterator = jsonEffects.iterator();    // Generate an iterator for the JSONArray that holds the effect data

                        while (effectsIterator.hasNext()) {                                // Iterate through the JSONArray that holds the effect data
                            JSONObject rawEffect = effectsIterator.next();
                            String effectName = (String) rawEffect.get("className");    // Get the class name of the effect we're instantiating
                            JSONArray propertiesArray = (JSONArray) rawEffect.get("properties");    // Get a sub-array of property integer values for the current effect

                            Integer[] effectProperties = new Integer[propertiesArray.size()];        // Create a java-array to store the effect's property values
                            for (int i = 0; i < propertiesArray.size(); i++) {                        // Iterate through the JSONArray that stores the property values
                                int prop = ((Long) propertiesArray.get(i)).intValue();                // Collect a property values and convert it to an int.
                                effectProperties[i] = prop;                                            // Store it in the array
                            }

                            effects.add(EffectFactory.build(effectName, effectProperties));        // Generate an instance of the effect class and assign the collected properties to it, then add it to the arraylist
                        }

                        Consumable c = new Consumable(name, desc, id, value, maxStacks, effects);    // Build the consumable from the read values

                        itemList.put(id, c); // Add the newly built consumable to the itemList hashmap
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
}
