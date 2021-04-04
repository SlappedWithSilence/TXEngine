package mckeken.io;

import org.json.simple.*;
import org.json.simple.parser.*;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;

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
					String name = (String) rawItem.get("name");
					int id = (int) rawItem.get("id");
					int value = (int) rawItem.get("value");
					int maxStacks = (int) rawItem.get("maxStacks");

					Item i = new Item(name, id, value, maxStacks); // Build the item from the read values

					itemList.put(id, i); // Add the newly build item to the itemList hashmap
				break;

				case "consumable":
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
}
