package txengine.io.loaders;

import txengine.io.Load;
import txengine.io.LoadUtils;
import txengine.io.Loader;
import txengine.systems.conversation.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import txengine.systems.event.Event;
import txengine.systems.event.EventFactory;
import txengine.ui.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ConversationLoader extends Loader {


    @Override
    public HashMap<Integer, Conversation> load(File file) {
        HashMap<Integer, Conversation> conversationList = new HashMap<>();

        // Read the JSON storage file
        JSONParser parser = new JSONParser();

        JSONObject root;

        try {
            root = (JSONObject) parser.parse(new FileReader(file));
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


        // Get the JSON array that contains all the conversations
        JSONArray rawConversations = (JSONArray) root.get("conversations");

        // Iterate through each conversation
        for (Object obj : rawConversations) {
            JSONObject rawConversation = (JSONObject) obj;

            int id = LoadUtils.asInt(rawConversation, "id");

            List<ConversationModule> modules = new ArrayList<>();

            for (Object obj2 : (JSONArray) rawConversation.get("modules")) {
                JSONObject rawModule = (JSONObject) obj2;
                modules.add(parseModule(rawModule));
            }

            conversationList.put(id, new Conversation(id, modules));
        }

        return conversationList;
    }

    private ConversationModule parseModule(JSONObject rawModule) {
        int id = LoadUtils.asInt(rawModule, "id");
        String npcText = LoadUtils.asString(rawModule, "npc_text");
        int width = ((JSONArray) rawModule.get("options")).size();
        String[] options = new String[width];
        Event[][] events = new Event[width][];
        Integer[] targets = new Integer[width];

        for (int i = 0; i < width; i++) {
            JSONObject rawOption = (JSONObject) ((JSONArray) rawModule.get("options")).get(i);
            options[i] = LoadUtils.asString(rawOption, "menu_text");
            events[i] = new Event[((JSONArray) rawOption.get("events")).size()];
            events[i] = LoadUtils.parseEvents((JSONArray) rawOption.get("events")).toArray(new Event[0]);
            targets[i] = LoadUtils.asInt(rawOption, "target");
        }

        return new ConversationModule(id, npcText, options, events, targets);
    }
}
