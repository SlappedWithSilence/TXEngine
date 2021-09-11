package txengine.systems.room.action.actions;

import com.rits.cloning.Cloner;
import txengine.systems.combat.CombatEngine;
import txengine.systems.combat.CombatEntity;
import txengine.systems.inventory.Inventory;
import txengine.io.LogUtils;
import txengine.main.Manager;
import txengine.systems.room.action.Action;
import txengine.util.Utils;

import java.util.ArrayList;

public class CombatAction extends Action {
    CombatEngine combatEngine;
    LoadType loadType;

    private final static String FRIENDLY_ENTITY_PROP_MARKER = "{FRIENDLY}";
    private final static String HOSTILE_ENTITY_PROP_MARKER  = "{HOSTILE}";
    private final static String LOOT_DATA_MARKER = "{LOOT}";
    private final static String ITEM_SEPARATER = ",";

    private ArrayList<Integer> lootIds;        // The ids of the items to give to the player when they win
    private ArrayList<Integer> lootQuantities; // The quantities of each item to give to the player

    private enum LoadType {
        HOSTILE,
        FRIENDLY,
        LOOT
    }

    @Override
    public int perform() {
        Cloner cloner = new Cloner();
        ArrayList<CombatEntity> friendlies = new ArrayList<>();
        ArrayList<CombatEntity> hostiles = new ArrayList<>();
        lootIds = new ArrayList<>();
        lootQuantities = new ArrayList<>();

        for (String s:
             properties) {

            // Set load type to friendly when encountering a friendly entity marker in the properties
            if (loadType == null || s.equals(FRIENDLY_ENTITY_PROP_MARKER)) {
                loadType = LoadType.FRIENDLY;
            }
            // Set the load type to loot when encountering a loot entity marker in the properties
            else if (s.equals(LOOT_DATA_MARKER)) {
                loadType = LoadType.LOOT;
            }
            // Set the load type to hostile when encountering a hostile entity marker in the properties
            else if (s.equals(HOSTILE_ENTITY_PROP_MARKER)) {
                loadType = LoadType.HOSTILE;
            }
            // If load type is set to friendly, add the entity with id value 's' to the friendly array
            else if (loadType == LoadType.FRIENDLY) {
                friendlies.add(cloner.deepClone(Manager.combatEntityList.get(Integer.parseInt(s))));
            }
            // If load type is set to hostile, add the entity with id value 's' to the hostile array
            else if (loadType == LoadType.HOSTILE) {
                hostiles.add(new CombatEntity(Manager.combatEntityList.get(Integer.parseInt(s))));
            // If the load type is set to Loot, process 's' as a pair of ints and add them to the loot arrays
            } else if (loadType == LoadType.LOOT) {
                if (s.contains(ITEM_SEPARATER)) { // Make sure that the value pair is formatted correctly
                   int[] lootProperties = Utils.parseInts(s, ITEM_SEPARATER);

                    // Add the item id and its quantity to their respective arrays
                    lootIds.add(lootProperties[0]);
                    lootQuantities.add(lootProperties[1]);
                }
            }
        }

        combatEngine = new CombatEngine(friendlies, hostiles);

        if (combatEngine.startCombat() ) {
            System.out.println("You emerge victorious from combat!");
            printLoot();
            giveLoot(lootIds, lootQuantities);
        }
        else System.out.println("You failed to conquer your foes and lie defeated.");

        return unhideIndex;
    }

    private void printLoot() {
        Inventory i = new Inventory(lootIds, lootQuantities);

        LogUtils.header("Loot");
        System.out.println("You notice some objects left behind on the battlefield and collect them.");
        i.printItems();
        LogUtils.getAnyKey();
    }


    private void giveLoot(ArrayList<Integer> ids, ArrayList<Integer> quantities) {
        if (ids.size() != quantities.size()) {
            LogUtils.error("Something went wrong while distributing loot to the player!\n");
            return;
        }

        for (int i = 0; i < ids.size(); i++) {
            Manager.player.getInventory().addItem(ids.get(i), quantities.get(i));
        }
    }

}
