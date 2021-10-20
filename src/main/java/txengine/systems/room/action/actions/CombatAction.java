package txengine.systems.room.action.actions;

import com.rits.cloning.Cloner;
import txengine.io.LoadUtils;
import txengine.io.load.PropertyTags;
import txengine.structures.Pair;
import txengine.systems.combat.CombatEngine;
import txengine.systems.combat.CombatEntity;
import txengine.systems.inventory.Inventory;
import txengine.ui.component.Components;
import txengine.ui.LogUtils;
import txengine.main.Manager;
import txengine.systems.room.action.Action;
import txengine.util.Utils;

import java.util.*;

public class CombatAction extends Action {
    CombatEngine combatEngine;
    LoadType loadType;

    private final static String FRIENDLY_ENTITY_PROP_MARKER = "{FRIENDLY}";
    private final static String HOSTILE_ENTITY_PROP_MARKER  = "{HOSTILE}";
    private final static String LOOT_DATA_MARKER = "{LOOT}";
    private final static String ITEM_SEPARATER = ",";

    private ArrayList<Integer> lootIds;        // The ids of the items to give to the player when they win
    private ArrayList<Integer> lootQuantities; // The quantities of each item to give to the player

    private boolean hideOnWin = false;

    private enum LoadType {
        HOSTILE,
        FRIENDLY,
        LOOT
    }

    public CombatAction() {
        super();
    }

    public CombatAction(List<Integer> friendlyIDs, List<Integer> hostileIDs, List<Pair<Integer, Integer>> loot) {
        StringBuilder sb = new StringBuilder();
        List<String> tempProperties = new ArrayList<>();
        tempProperties.add(FRIENDLY_ENTITY_PROP_MARKER);
        for (Integer i : friendlyIDs) tempProperties.add(i.toString());
        tempProperties.add(HOSTILE_ENTITY_PROP_MARKER);
        for (Integer i : hostileIDs) tempProperties.add(i.toString());
        tempProperties.add(LOOT_DATA_MARKER);
        for (Pair<Integer, Integer> item : loot) {
            Integer id = item.getKey();
            Integer quantity = item.getValue();
            tempProperties.add(id+","+quantity);
        }
        setProperties(tempProperties.toArray(new String[0]));
    }

    @Override
    public int perform() {
        ArrayList<CombatEntity> friendlies = new ArrayList<>();
        ArrayList<CombatEntity> hostiles = new ArrayList<>();
        if (lootIds== null) lootIds = new ArrayList<>();
        if (lootQuantities == null)  lootQuantities = new ArrayList<>();

        Map<String, List<String>> markedProperties = PropertyTags.getMarkedProperties(properties);
        for (String s : markedProperties.get(PropertyTags.friendlyMarker)) friendlies.add(new CombatEntity(Manager.combatEntityHashMap.get(Integer.parseInt(s))));
        for (String s : markedProperties.get(PropertyTags.hostileMarker)) hostiles.add(new CombatEntity(Manager.combatEntityHashMap.get(Integer.parseInt(s))));
        for (String s : markedProperties.get(PropertyTags.lootMarker)) {
            int[] values = Utils.parseInts(s, ",");
            lootIds.add(values[0]);
            lootQuantities.add(values[1]);
        }

        combatEngine = new CombatEngine(friendlies, hostiles);

        if (combatEngine.startCombat() ) {
            System.out.println("You emerge victorious from combat!");
            printLoot();
            giveLoot(lootIds, lootQuantities);
            if (hideOnWin) hidden = true;
        }
        else System.out.println("You failed to conquer your foes and lie defeated.");

        return unhideIndex;
    }

    public boolean isHideOnWin() {
        return hideOnWin;
    }

    public void setHideOnWin(boolean hideOnWin) {
        this.hideOnWin = hideOnWin;
    }

    private void printLoot() {
        Inventory i = new Inventory(lootIds, lootQuantities);

        Components.header("Loot");
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

    public void addLoot(int id) {
        if (lootIds == null) lootIds = new ArrayList<>();
        if (lootQuantities == null)  lootQuantities = new ArrayList<>();
        lootIds.add(id);
        lootQuantities.add(1);
    }

    public void addLoot(int id, int quantity) {
        if (lootIds== null) lootIds = new ArrayList<>();
        if (lootQuantities == null)  lootQuantities = new ArrayList<>();
        lootIds.add(id);
        lootQuantities.add(quantity);
    }



}
