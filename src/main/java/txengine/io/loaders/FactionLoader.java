package txengine.io.loaders;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import txengine.io.LoadUtils;
import txengine.io.Loader;
import txengine.systems.reputation.Faction;
import txengine.ui.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class FactionLoader extends Loader {

    @Override
    public HashMap<String, Faction> load(File file) {
        HashMap<String, Faction> factionHashMap = new HashMap<>();

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

        JSONArray rawFactions = (JSONArray) root.get("factions");

        for (Object o : rawFactions) {
            JSONObject rawFaction = (JSONObject) o;

            String name = LoadUtils.asString(rawFaction, "name");
            int levelUpXP = LoadUtils.asInt(rawFaction, "max_xp");
            double levelUpRatio = LoadUtils.asDouble(rawFaction, "xp_ratio");

            factionHashMap.put(name, new Faction(name, 1, 0, levelUpXP, (float) levelUpRatio));
        }

        return factionHashMap;
    }
}
