package mckeken.room.action.actions;

import com.rits.cloning.Cloner;
import mckeken.combat.CombatEngine;
import mckeken.combat.CombatEntity;
import mckeken.main.Manager;
import mckeken.room.action.Action;

import java.util.ArrayList;
import java.util.Arrays;

public class CombatAction extends Action {
    CombatEngine combatEngine;
    CombatEngine.EntityType loadType;

    private final static String FRIENDLY_ENTITY_PROP_MARKER = "{FRIENDLY}";
    private final static String HOSTILE_ENTITY_PROP_MARKER  = "{HOSTILE}";

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
                hostiles.add(cloner.deepClone(Manager.combatEntityList.get(Integer.parseInt(s))));
            }
        }

        combatEngine = new CombatEngine(friendlies, hostiles);

        if (combatEngine.startCombat() ) System.out.println("You emerge victorious from combat!");
        else System.out.println("You failed to conquer your foes and lie defeated.");

        return unlockIndex;
    }
}
