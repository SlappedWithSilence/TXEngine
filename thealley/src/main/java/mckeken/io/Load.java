package mckeken.io;

import mckeken.item.effect.EffectFactory;
import mckeken.room.action.Action;
import mckeken.room.action.ActionFactory;
import mckeken.room.action.actions.conversation.Conversation;
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
		return false;
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
						ArrayList<Effect> effects = new ArrayList<Effect>(); 			// The list that will hold the effects
						JSONArray jsonEffects = (JSONArray) rawItem.get("effects");	 	// Grab the JSON array of effects
						Iterator<JSONObject> effectsIterator = jsonEffects.iterator();	// Generate an iterator for the JSONArray that holds the effect data

						while (effectsIterator.hasNext()) {								// Iterate through the JSONArray that holds the effect data
							JSONObject rawEffect = effectsIterator.next();
							String effectName = (String) rawEffect.get("className");	// Get the class name of the effect we're instantiating
							JSONArray propertiesArray = (JSONArray) rawEffect.get("properties");	// Get a sub-array of property integer values for the current effect

							String[] effectProperties = new String[ propertiesArray.size()];		// Create a java-array to store the effect's property values
							for (int i = 0; i < propertiesArray.size(); i++) {						// Iterate through the JSONArray that stores the property values
								String prop = ((String) propertiesArray.get(i));				// Collect a property values and convert it to an int.
								effectProperties[i] = prop;											// Store it in the array
							}

							effects.add(EffectFactory.build(effectName,  effectProperties));		// Generate an instance of the effect class and assign the collected properties to it, then add it to the arraylist
						}

						Consumable c = new Consumable(name, desc, id, value, maxStacks, effects); 	// Build the consumable from the read values

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

	public static HashMap<Integer, Conversation> loadConversations(File file) {
		HashMap<Integer, Conversation> conversationList = new HashMap<Integer, Conversation>();

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
			LogUtils.error(file.getName() + " appears to be corrupted. Please re-download it from https://github.com/TopperMcKek/TheAlley/blob/master/thealley/resources/items.json\n");
			e.printStackTrace();
			return null;
		}

		// Get the JSON array that contains all the items
		JSONArray items = (JSONArray) obj.get("Items");
		Iterator<JSONObject> iterator = items.iterator(); // Create an iterator over the list of items

		// Loop through the item JSON objects
		while (iterator.hasNext()) {
			JSONObject rawItem = iterator.next();
		}


		return conversationList;
	}

	// Loads rooms from disk
	// TODO Implement
	public static HashMap<Integer, Room> loadRooms(File file) {
		HashMap<Integer, Room> roomList = new HashMap<Integer, Room>();

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
			LogUtils.error("rooms.JSON appears to be corrupted. Please re-download it from https://github.com/TopperMcKek/TheAlley/blob/master/thealley/resources/items.json\n");
			e.printStackTrace();
			return null;
		}

		// Get the JSON array that contains all the items
		JSONArray rooms = (JSONArray) obj.get("Rooms");
		Iterator<JSONObject> iterator = rooms.iterator(); // Create an iterator over the list of items

		// Loop through the room JSON objects
		while (iterator.hasNext()) {
			JSONObject rawRoom = iterator.next();

			String roomName = (String) rawRoom.get("name");
			int id = ((Long) rawRoom.get("id")).intValue();
			String roomText = (String) rawRoom.get("text");

			ArrayList<Action> onFirstEnterActions = getActions((JSONArray) rawRoom.get("onFirstEnterActions"));
			ArrayList<Action> actions = getActions((JSONArray) rawRoom.get("actions"));

			Room room = new Room(id, roomName, roomText);
			room.setRoomActions(actions);
			room.setOnFirstEnterActions(onFirstEnterActions);

			roomList.put(id, room);

		}
		return roomList;
	}

	private static ArrayList<Action> getActions(JSONArray JSONActions) {
		ArrayList<Action> actions = new ArrayList<Action>();

		Iterator<JSONObject> iterator = JSONActions.iterator();

		while (iterator.hasNext()) {
			JSONObject rawAction = iterator.next();

			String className = 	(String) rawAction.get("className");
			String menuName = 	(String) rawAction.get("menuName");
			String text =		(String) rawAction.get("text");
			boolean enabled = 	(Boolean) rawAction.get("enabled");
			int unlockIndex = 	((Long) rawAction.get("unlockedIndex")).intValue();

			JSONArray propertiesArray = (JSONArray) rawAction.get("properties");	// Get a sub-array of property string values for the current action

			String[] actionProperties = new String[ propertiesArray.size()];		// Create a java-array to store the action's property values
			for (int i = 0; i < propertiesArray.size(); i++) {						// Iterate through the JSONArray that stores the property values
				String prop = (String) propertiesArray.get(i);						// Collect a property values and convert it to an string.
				actionProperties[i] = prop;											// Store it in the array
			}

			Action a = ActionFactory.build(className, menuName, text, enabled, unlockIndex, actionProperties);
			actions.add(a);
		}

		return actions;
	}

}
