package mckeken.io;

import mckeken.room.action.Action;
import mckeken.room.action.actions.conversation.Conversation;
import mckeken.room.action.actions.conversation.Event;
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

public class ConversationLoader implements Loader{


    @Override
    public HashMap<Integer, Conversation> load(File file) {
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
        JSONArray items = (JSONArray) obj.get("conversations");
        Iterator<JSONObject> iterator = items.iterator(); // Create an iterator over the list of items

        // Loop through the item JSON objects
        while (iterator.hasNext()) {
            JSONObject rawItem = iterator.next();
        }

        return conversationList;
    }

    private static ArrayList<Event> getEvents(JSONArray JSONActions) {
        ArrayList<Event> events = new ArrayList<Event>();


        return events;
    }
}
