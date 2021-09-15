package txengine.io;

import txengine.systems.conversation.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import txengine.systems.event.Event;
import txengine.systems.event.EventFactory;
import txengine.ui.component.LogUtils;

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
        HashMap<Integer, Conversation> conversationList = new HashMap<>();

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
            LogUtils.error(file.getName() + " appears to be corrupted. Please re-download it from https://github.com/TopperMcKek/TheAlley/blob/master/thealley/resources/conversations.json\n");
            e.printStackTrace();
            return null;
        }



        // Get the JSON array that contains all the items
        JSONArray items = (JSONArray) obj.get("conversations");
        Iterator<JSONObject> iterator = items.iterator(); // Create an iterator over the list of items

        // Loop through the conversation JSON objects
        while (iterator.hasNext()) {
            Conversation conversation;

            JSONObject rawConversation = iterator.next();
            Integer id = ((Long) rawConversation.get("id")).intValue();

            JSONArray rawLayerArray = (JSONArray) rawConversation.get("layers");

            Iterator<JSONObject> rawLayerIterator = rawLayerArray.iterator();

            ArrayList<ConversationLayer> layers = new ArrayList<>();
            while (rawLayerIterator.hasNext()) {
                ArrayList<ConversationLayer> conversationLayers = new ArrayList<>();

                JSONObject rawLayer = rawLayerIterator.next();
                JSONArray rawModulesArray = (JSONArray) rawLayer.get("modules");

                Iterator<JSONObject> modulesIterator = rawModulesArray.iterator();

                ConversationLayer layer;
                ConversationModule[] modules = new ConversationModule[rawModulesArray.size()];
                for (int i = 0; i < rawModulesArray.size(); i++) {

                    JSONObject rawModule = (JSONObject) rawModulesArray.get(i);
                    String npcText = (String) rawModule.get("npcText");
                    String[] options = getStringArray((JSONArray) rawModule.get("options"));
                    Event[][] events = getEvents((JSONArray) rawModule.get("events"));
                    Integer[] targets = getIntArray((JSONArray) rawModule.get("targets"));

                    ConversationModule module = new ConversationModule(npcText, options, events, targets);
                    modules[i] = module;
                }

                layer = new ConversationLayer(modules);
                layers.add(layer);
            }
            conversation = new Conversation(id, 0, layers);
            conversationList.put(id, conversation);
        }

        return conversationList;
    }

    private static Event[][] getEvents(JSONArray JSONActions) {
        Event[][] events = new Event[JSONActions.size()][];

        for (int i = 0; i < JSONActions.size(); i++) {
        //for (JSONArray jsonAction : (Iterable<JSONArray>) JSONActions) { //  Get the array of events and iterate through it
            Event[] eventArray = new Event[((JSONArray) JSONActions.get(i)).size()];

            for (int j = 0; j < ((JSONArray) JSONActions.get(i)).size(); j++) {
            //for (JSONObject rawEvent : (Iterable<JSONObject>) JSONActions[i]) { // Iterate through the JSON objects of Event Objects
                JSONObject rawEvent =  (JSONObject) ((JSONArray) JSONActions.get(i)).get(j);

                String className = (String) rawEvent.get("className");
                JSONArray rawProperties = (JSONArray) rawEvent.get("properties");
                String[] properties = getStringArray(rawProperties);

                Event e = EventFactory.build(className, properties);
                eventArray[j] = e;
            }

            events[i] = eventArray;

        }

        return events;
    }

    private static String[] getStringArray(JSONArray stringArray) {
        String[] array = new String[stringArray.size()];

        for (int i = 0; i < stringArray.size(); i++) {
            array[i] = (String) stringArray.get(i);
        }

        return array;
    }

    private static Integer[] getIntArray(JSONArray IntArray) {
        Integer[] array = new Integer[IntArray.size()];

        for (int i = 0; i < IntArray.size(); i++) {
            array[i] = ((Long) IntArray.get(i)).intValue();
        }

        return array;
    }
}
