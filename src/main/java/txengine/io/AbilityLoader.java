package txengine.io;

import txengine.systems.combat.CombatEngine;
import txengine.systems.ability.Ability;
import txengine.systems.ability.AbilityFactory;
import txengine.systems.combat.combatEffect.CombatEffect;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import txengine.ui.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static txengine.io.LoadUtils.parseCombatEffects;
import static txengine.io.LoadUtils.parseResourceCosts;

public class AbilityLoader {

    AbilityLoader() {

    }

    public static HashMap<String, Ability> load(File file) {

        HashMap<String, Ability> abilityHashMap = new HashMap<>();

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
        JSONArray rawAbilities= (JSONArray) obj.get("Abilities");
        Iterator<JSONObject> iterator = rawAbilities.iterator(); // Create an iterator over the list of abilities

        String targetMode;
        String name;
        String description;
        String useText;
        int damage;
        List<AbstractMap.SimpleEntry<CombatEffect, CombatEngine.CombatPhase>> effects = new ArrayList<>();
        ArrayList<AbstractMap.SimpleEntry<String, Integer>> resourceCosts = new ArrayList<>();

        while (iterator.hasNext()) {
            JSONObject rawAbility = iterator.next();
            targetMode = ((String) rawAbility.get("target_mode")).toUpperCase(Locale.ROOT);
            name = (String) rawAbility.get("name");
            description = (String) rawAbility.get("description");
            useText = ((String) rawAbility.get("use_text"));
            damage = ((Long) rawAbility.get("damage")).intValue();
            resourceCosts = parseResourceCosts((JSONArray) rawAbility.get("resource_cost"));
            effects = parseCombatEffects((JSONArray) rawAbility.get("effects"));

            abilityHashMap.put(name, AbilityFactory.build(CombatEngine.TargetMode.valueOf(targetMode), name, description, useText, effects, damage, resourceCosts));
        }

        return abilityHashMap;
    }






}
