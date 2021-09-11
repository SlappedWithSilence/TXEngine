package txengine.io;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class CombatResourceLoader implements Loader{

    public CombatResourceLoader() {

    }

    @Override
    public HashMap<String, Integer[]> load(File file) {
        HashMap<String, Integer[]> playerResources = new HashMap<>();

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
        JSONArray rawResources = (JSONArray) obj.get("combatResources");
        Iterator<JSONObject> iterator = rawResources.iterator(); // Create an iterator over the list of items

        // Loop through the conversation JSON objects
        while (iterator.hasNext()) {
            JSONObject rawResourceBundle = iterator.next();
            String resourceName = (String) rawResourceBundle.get("resourceName");
            int value = ((Long) rawResourceBundle.get("value")).intValue();
            int maxValue = ((Long) rawResourceBundle.get("maxValue")).intValue();

            playerResources.put(resourceName, new Integer[]{maxValue, value});
        }

        return playerResources;
    }
}
