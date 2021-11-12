package txengine.io.loaders;

import txengine.io.LoadUtils;
import txengine.io.Loader;
import txengine.main.Manager;
import txengine.structures.Pair;
import txengine.systems.combat.CombatEntity;
import txengine.systems.combat.CombatResourceManager;
import txengine.systems.ability.AbilityManager;
import txengine.systems.combat.EquipmentManager;
import txengine.systems.inventory.Inventory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import txengine.systems.item.Equipment;
import txengine.systems.item.Item;
import txengine.ui.LogUtils;
import txengine.ui.Out;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CombatEntityLoader extends Loader {

    @Override
    public HashMap<Integer, CombatEntity> load(File file) {
        HashMap<Integer, CombatEntity> entityList = new HashMap<>();  // Store the item instances mapped to their IDs. Requires a collision checker

        // Read the JSON storage file
        JSONParser parser = new JSONParser();

        JSONObject obj;

        try {
            obj = (JSONObject) parser.parse(new FileReader(file));
        } catch (FileNotFoundException e) {
            Out.error("Attempted to load from file: " + file.getAbsolutePath());
            e.printStackTrace();
            return null;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }

        // Get the JSON array that contains all the items
        JSONArray entities = (JSONArray) obj.get("entities");
        Iterator<JSONObject> iterator = entities.iterator(); // Create an iterator over the list of items

        // Loop through the item JSON objects
        while (iterator.hasNext()) {
            JSONObject rawEntity = iterator.next();

            String name = ((String) rawEntity.get("name"));
            String openingDialog = ((String) rawEntity.get("opening_dialog"));
            String closingDialog = ((String) rawEntity.get("closing_dialog"));
            Inventory inventory = buildInventory((JSONArray) rawEntity.get("inventory"));

            CombatResourceManager resourceManager = buildResourceManager((JSONArray) rawEntity.get("resources"));
            AbilityManager abilityManager = buildAbilityManager((JSONArray) rawEntity.get("abilities"));
            int speed = ((Long) rawEntity.get("speed")).intValue();
            int level = ((Long) rawEntity.get("level")).intValue();

            int xpYield = ((Long) rawEntity.get("combat_xp_yield")).intValue();

            int id = ((Long) rawEntity.get("id")).intValue();

            EquipmentManager equipmentManager = new EquipmentManager(); // Make a new equipment manager

            for (int i : LoadUtils.getIntArray((JSONArray) rawEntity.get("equipment_ids"))) {
                Item item = Manager.itemManager.get_instance(i);

                if (!(item instanceof Equipment)) {
                    Out.error("Error loading entity with id: " + id + "! A non-equipment item was found in the equipment_ids array!");
                } else {
                    Equipment.EquipmentType type = ((Equipment) item).getType();

                    equipmentManager.setSlot(type, i);
                }
            }

            CombatEntity combatEntity = new CombatEntity(name, openingDialog, closingDialog, 100, inventory,abilityManager, resourceManager, equipmentManager,speed, level, xpYield);

            entityList.put(id, combatEntity);
        }

        return entityList;
    }

    private AbilityManager buildAbilityManager(JSONArray rawAbilities) {
        AbilityManager abilityManager = new AbilityManager();

        for (int i = 0; i < rawAbilities.size(); i++) {
            abilityManager.learn((String) rawAbilities.get(i));
        }

        return abilityManager;
    }

    // Process the inventory ID and quantity arrays and produce a new inventory accordingly
    private Inventory buildInventory(JSONArray rawItemPair) {
        Inventory inventory = new Inventory();

       List<Pair<Integer, Integer>> arr = LoadUtils.parseIntPairs(rawItemPair);

        for (Pair<Integer, Integer> pair : arr) inventory.addItem(pair.getKey(), pair.getValue());

        return inventory;
    }

    // Return a new Resource manager containing the resources described in the JSON array
    private CombatResourceManager buildResourceManager(JSONArray rawResource) {
        CombatResourceManager resourceManager = new CombatResourceManager();

        List<Pair<String, Integer>> pairs = LoadUtils.parseStringIntPairs(rawResource);

        for (Pair<String, Integer> pair : pairs) resourceManager.registerResource(pair.getKey(), pair.getValue(), pair.getValue());

        return resourceManager;
    }
}
