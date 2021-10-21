package txengine.io.loaders;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import txengine.io.LoadUtils;
import txengine.io.Loader;
import txengine.systems.event.Event;
import txengine.systems.skill.Skill;
import txengine.ui.LogUtils;
import txengine.ui.Out;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class SkillLoader extends Loader {

    @Override
    public HashMap<String, Skill> load(File file) {
        HashMap<String, Skill> skills = new HashMap<>();

        // Read the JSON storage file
        JSONParser parser = new JSONParser();

        JSONObject obj;

        try {
            obj = (JSONObject) parser.parse(new FileReader(file));
        } catch (FileNotFoundException e) {
            Out.error("Attempted to load from file: " + file.getAbsolutePath());
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
        JSONArray items = (JSONArray) obj.get("skills");
        Iterator<JSONObject> iterator = items.iterator(); // Create an iterator over the list of items

        // Loop through the conversation JSON objects
        while (iterator.hasNext()) {
            JSONObject rawSkill = iterator.next();

            String name = (String) rawSkill.get("name");
            String desc = (String) rawSkill.get("description");
            int level = ((Long) rawSkill.get("level")).intValue();
            int xp = ((Long) rawSkill.get("xp")).intValue();
            int levelUpXP = ((Long) rawSkill.get("level_up_xp")).intValue();
            float levelRatio = ((Double) rawSkill.get("level_ratio")).floatValue();

            TreeMap<Integer, List<Event>> levelEvents = parseLevelEvents((JSONArray) rawSkill.get("level_up_events"));

            Skill s = new Skill(name, desc, level, xp, levelUpXP, levelRatio, levelEvents);

            skills.put(name, s);
            Out.info("Skill added: " + s.getName(), null);
        }

        return skills;
    }

    private TreeMap<Integer, List<Event>> parseLevelEvents(JSONArray jsonArray) {
        TreeMap<Integer, List<Event>> levelEvents = new TreeMap<>();

        for (Object o : jsonArray) {

            JSONObject rawPair = (JSONObject) o;

            int level = ((Long) rawPair.get("level")).intValue();
            List<Event> events = LoadUtils.parseEvents((JSONArray) rawPair.get("events"));

            levelEvents.put(level, events);
        }

        return levelEvents;
    }
}
