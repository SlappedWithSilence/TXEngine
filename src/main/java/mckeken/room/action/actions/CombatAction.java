package mckeken.room.action.actions;

import com.rits.cloning.Cloner;
import mckeken.combat.CombatEngine;
import mckeken.combat.CombatEntity;
import mckeken.io.LogUtils;
import mckeken.main.Manager;
import mckeken.room.action.Action;

import java.util.ArrayList;
import java.util.List;

public class CombatAction extends Action {
    CombatEngine combatEngine;
    CombatEngine.EntityType loadType;

    private final static String FRIENDLY_ENTITY_PROP_MARKER = "{FRIENDLY}";
    private final static String HOSTILE_ENTITY_PROP_MARKER  = "{HOSTILE}";

    private ArrayList<Integer> lootIds;        // The ids of the items to give to the player when they win
    private ArrayList<Integer> lootQuantities; // The quantities of each item to give to the player

    @Override
    public int perform() {
        Cloner cloner = new Cloner();
        ArrayList<CombatEntity> friendlies = new ArrayList<>();
        ArrayList<CombatEntity> hostiles = new ArrayList<>();

        for (String s:
             properties) {
            if (loadType == null || s.equals(FRIENDLY_ENTITY_PROP_MARKER)) {
                loadType = CombatEngine.EntityType.FRIENDLY;
            }
            else if (s.equals(HOSTILE_ENTITY_PROP_MARKER)) {
                loadType = CombatEngine.EntityType.HOSTILE;
            }
            else if (loadType == CombatEngine.EntityType.FRIENDLY) {
                friendlies.add(cloner.deepClone(Manager.combatEntityList.get(Integer.parseInt(s))));
            }
            else if (loadType == CombatEngine.EntityType.HOSTILE) {
                hostiles.add(new CombatEntity(Manager.combatEntityList.get(Integer.parseInt(s))));
            }
        }

        combatEngine = new CombatEngine(friendlies, hostiles);

        if (combatEngine.startCombat() ) {
            System.out.println("You emerge victorious from combat!");
            getLootData(lootIds, lootQuantities, List.of(properties));
            giveLoot(lootIds, lootQuantities);
        }
        else System.out.println("You failed to conquer your foes and lie defeated.");

        return unlockIndex;
    }

    private void getLootData(ArrayList<Integer> ids, ArrayList<Integer> quantities, final List<String> rawData) {
        if (rawData.size()%2 != 0) {
            LogUtils.error("Something went wrong while loading item data for a combat event!\n");
            return;
        }

        for (int i = 0; i < rawData.size(); i+=2) {
            ids.add(Integer.parseInt(rawData.get(i)));
            quantities.add(Integer.parseInt(rawData.get(i+1)));
        }
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
