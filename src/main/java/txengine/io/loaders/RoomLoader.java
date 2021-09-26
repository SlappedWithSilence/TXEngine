package txengine.io.loaders;

import txengine.io.LoadUtils;
import txengine.io.Loader;
import txengine.systems.integration.Requirement;
import txengine.systems.room.Room;
import txengine.systems.room.action.Action;
import txengine.systems.room.action.ActionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import txengine.ui.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class RoomLoader extends Loader {

    @Override
    public HashMap<Integer, Room> load(File file) {
        HashMap<Integer, Room> roomList = new HashMap<>();

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

            //ArrayList<Action> onFirstEnterActions = getActions((JSONArray) rawRoom.get("on_first_enter_actions"));
            ArrayList<Action> onFirstEnterActions = getActions(LoadUtils.optional(rawRoom,"on_first_enter_actions",JSONArray.class, new JSONArray()));

            ArrayList<Action> actions = getActions((JSONArray) rawRoom.get("actions"));

            Room room = new Room(id, roomName, roomText);
            room.setRoomActions(actions);
            room.setOnFirstEnterActions(onFirstEnterActions);

            roomList.put(id, room);

        }
        return roomList;
    }

    private static ArrayList<Action> getActions(JSONArray JSONActions) {
        ArrayList<Action> actions = new ArrayList<>();

        Iterator<JSONObject> iterator = JSONActions.iterator();

        while (iterator.hasNext()) {
            JSONObject rawAction = iterator.next();

            String className = 	(String) rawAction.get("class_name");
            String menuName = 	(String) rawAction.get("menu_name");
            String text =		(String) rawAction.get("text");
            boolean hidden = 	(Boolean) rawAction.get("hidden");

            // If the action doesn't have a hide_after_use field, supply a false value as a defaut
            boolean hideAfterUse = LoadUtils.optional(rawAction,"hide_after_use", Boolean.class, false);
            int unhideIndex = ((Long) rawAction.get("unlocked_index")).intValue();

            List<Requirement> requirements = LoadUtils.parseRequirements((JSONArray) rawAction.get("requirements"));

            JSONArray propertiesArray = (JSONArray) rawAction.get("properties");	// Get a sub-array of property string values for the current action

            // Catch missing optional values and supply defaults to them
            if (text == null) text = "";

            // Catch missing required values and print error messages
            if (className == null) LogUtils.error("Missing required field from Activity: class_name\n");
            if (menuName == null) LogUtils.error("Missing required field from Activity: menu_name\n");

            String[] actionProperties = new String[ propertiesArray.size()];		// Create a java-array to store the action's property values
            for (int i = 0; i < propertiesArray.size(); i++) {						// Iterate through the JSONArray that stores the property values
                String prop = (String) propertiesArray.get(i);						// Collect a property values and convert it to an string.
                actionProperties[i] = prop;											// Store it in the array
            }

            Action a = ActionFactory.build(className, menuName, text, hidden, unhideIndex, hideAfterUse, actionProperties, requirements);
            actions.add(a);
        }

        return actions;
    }
}
