package mckeken.io;

import org.json.simple.*;
import org.json.simple.parser.*;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.lang.Long;	

import mckeken.item.effect.Effect;
import mckeken.main.Manager;
import mckeken.player.Player;
import mckeken.item.*;
import mckeken.room.*;

public class Load {

	// Checks for a saved game
	// TODO: Implement
	public static boolean hasSave() {
		return true;
	}

	// Loads a saved game from disk
	// TODO: Implement
	public static void loadGame() {

	}

	// Sets up and configures a new game state
	// TODO: Implement
	public static void initializeNewGame() {
		Manager.player = new Player();
	}

	// Loads items from disk
	// TODO: Implement
	public static HashMap<Integer, Item> loadItems(File file) {

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

			switch(itemType) {
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

				case "consumable":
					try {
						String name = (String) rawItem.get("name");
						String desc = (String) rawItem.get("description");
						int id = ((Long) rawItem.get("id")).intValue();
						int value = ((Long) rawItem.get("value")).intValue();
						int maxStacks = ((Long) rawItem.get("maxStacks")).intValue();

						ArrayList<Effect> effects = new ArrayList<Effect>();

						// Begin loading effects
						JSONArray jsonEffects = (JSONArray) rawItem.get("effects"); // Grab the JSON array of effects
						Iterator<JSONObject> effectsIterator = jsonEffects.iterator();

						// Begin loading the properties for each effect
						ArrayList<JSONArray> properties = JSONArrayToArrayList<JSONArray>((JSONArray) rawItem.get("properties")); // Get an array of arrays that stores the properties for each effect
						Iterator<JSONArray> propertiesIterator = properties.iterator();
						
						while (effectsIterator.hasNext()) {
							JSONObject rawEffect = effectsIterator.next();
							//ArrayList<Long> rawProperties = 
							JSONArrayToArrayList<int>(propertiesIterator.next());
							
						}

						Consumable c = new Consumable(name, desc, id, value, maxStacks, effects); // Build the item from the read values

						itemList.put(id, c); // Add the newly build item to the itemList hashmap
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				break;

				default:
					throw new IllegalArgumentException("Invalid item type:" + itemType);
			}
		}

		return itemList;
	}

	// Loads rooms from disk
	// TODOL Implement
	public static HashMap<Integer, Room> loadRooms() {
		HashMap<Integer, Room> roomList = new HashMap<Integer, Room>();

		return roomList;
	}

	public static <T> ArrayList<T> JSONArrayToArrayList(JSONArray arr) {
		ArrayList<T> al = new ArrayList<T>();
		Iterator<JSONObject> iterator = arr.iterator();

		while (iterator.hasNext()) {
			JSONObject rawItem = iterator.next();
			al.add((T) rawItem);
		}

		return al;
	}
}
