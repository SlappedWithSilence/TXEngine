package txengine.io;

import txengine.main.Manager;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class CombatEntityLoader implements Loader {

    @Override
    public HashMap<Integer, CombatEntity> load(File file) {
        HashMap<Integer, CombatEntity> entityList = new HashMap<>();  // Store the item instances mapped to their IDs. Requires a collision checker

        // Read the JSON storage file
        JSONParser parser = new JSONParser();

        JSONObject obj;

        try {
            obj = (JSONObject) parser.parse(new FileReader(file));
        } catch (FileNotFoundException e) {
            LogUtils.error("Attempted to load from file: " + file.getAbsolutePath());
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
            Inventory inventory = buildInventory((JSONArray) rawEntity.get("inventory_ids"),
                                                 (JSONArray) rawEntity.get("inventory_quantities"));

            CombatResourceManager resourceManager = buildResourceManager((JSONArray) rawEntity.get("resources"));
            AbilityManager abilityManager = buildAbilityManager((JSONArray) rawEntity.get("abilities"));
            int speed = ((Long) rawEntity.get("speed")).intValue();
            int level = ((Long) rawEntity.get("level")).intValue();

            int id = ((Long) rawEntity.get("id")).intValue();

            EquipmentManager equipmentManager = new EquipmentManager(); // Make a new equipment manager

            for (int i : LoadUtils.getIntArray((JSONArray) rawEntity.get("equipment_ids"))) {
                Item item = Manager.itemHashMap.get(i);

                if (!(item instanceof Equipment)) {
                    LogUtils.error("Error loading entity with id: " + id + "! A non-equipment item was found in the equipment_ids array!");
                } else {
                    Equipment.EquipmentType type = ((Equipment) item).getType();

                    equipmentManager.setSlot(type, i);
                }
            }

            CombatEntity combatEntity = new CombatEntity(name, openingDialog, closingDialog, 100, inventory,abilityManager, resourceManager, equipmentManager,speed, level);

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
    private Inventory buildInventory(JSONArray rawIDs, JSONArray rawQuantities) {
        Inventory inventory = new Inventory();

        Iterator<JSONObject> idIterator = rawIDs.iterator();
        Iterator<JSONObject> quantityIterator = rawQuantities.iterator();

        for (int i = 0; i < rawIDs.size(); i++) {
            int id = ((Long) rawIDs.get(i)).intValue();
            int quantity = ((Long) rawQuantities.get(i)).intValue();

            inventory.addItem(id, quantity);
        }

        return inventory;
    }

    // Return a new Resource manager containing the resources described in the JSON array
    private CombatResourceManager buildResourceManager(JSONArray rawResource) {
        CombatResourceManager resourceManager = new CombatResourceManager();

        for (int i = 0; i < rawResource.size(); i++) {
            String resourceName = ((String) rawResource.get(i));
            i++;
            int maxValue =  ((Long) rawResource.get(i)).intValue();
            i++;
            int currentValue = ((Long) rawResource.get(i)).intValue();

            resourceManager.getResources().put(resourceName, new Integer[]{maxValue, currentValue});
        }

        return resourceManager;
    }
}
