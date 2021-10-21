package txengine.io.loaders;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import txengine.io.LoadUtils;
import txengine.structures.Pair;
import txengine.systems.event.Event;
import txengine.systems.integration.Requirement;
import txengine.systems.crafting.Recipe;
import txengine.systems.crafting.RecipeFactory;
import txengine.ui.LogUtils;
import txengine.ui.Out;
import txengine.util.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RecipeLoader {

    public static HashMap<Integer, Recipe> load(File file) {
        HashMap<Integer, Recipe> recipeHashMap = new HashMap<>();

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
            Out.error("rooms.JSON appears to be corrupted. Please re-download it from https://github.com/TopperMcKek/TheAlley/blob/master/thealley/resources/items.json\n");
            e.printStackTrace();
            return null;
        }

        // Get the JSON array that contains all the items
        JSONArray recipes = (JSONArray) obj.get("recipes");
        Iterator<JSONObject> iterator = recipes.iterator(); // Create an iterator over the list of items

        // Loop through the room JSON objects
        while (iterator.hasNext()) {
            JSONObject rawRecipe = iterator.next();

            String[] rawIngredients = LoadUtils.getStringArray((JSONArray) rawRecipe.get("ingredients"));
            String[] rawProducts = LoadUtils.getStringArray((JSONArray) rawRecipe.get("products"));
            List<Requirement> requirementList = LoadUtils.parseRequirements((JSONArray) rawRecipe.get("requirements"));

            int id = ((Long) rawRecipe.get("id")).intValue();

            List<Pair<Integer, Integer>> ingredients = Arrays.stream(rawIngredients).map(s -> { int[] vals = Utils.parseInts(s,",");
                                                                                                                    return new Pair<>(vals[0], vals[1]);
                                                                                                                 }).toList();
            List<Pair<Integer, Integer>> products = Arrays.stream(rawProducts).map(s -> { int[] vals = Utils.parseInts(s,",");
                return new Pair<>(vals[0], vals[1]);
            }).toList();

            List<Event> events = LoadUtils.parseEvents((JSONArray) rawRecipe.get("events"));
            Recipe r = RecipeFactory.build(ingredients, products, requirementList, events);
            r.setId(id);
            recipeHashMap.put(id, r);
        }

        return recipeHashMap;
    }

}
