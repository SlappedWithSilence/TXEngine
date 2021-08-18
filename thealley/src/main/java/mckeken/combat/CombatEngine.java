package mckeken.combat;

import mckeken.combat.combatEffect.CombatEffect;
import mckeken.item.effect.Effect;

import java.util.*;

public class CombatEngine {

    String primaryResourceName; // The name of the resources that triggers death when it hits zero

    // Possible Phases of a turn
    public enum CombatPhase {
        START_OF_COMBAT,
        TURN_START,
        PRE_ACTION,
        ACTION,
        POST_ACTION,
        END_OF_TURN
    }

    public enum EntityType {
        FRIENDLY,
        HOSTILE
    }

    public enum TargetMode {
        SINGLE,
        SINGLE_ENEMY,
        SINGLE_FRIENDLY,
        ALL,
        ALL_FRIENDLY,
        ALL_ENEMY;
    }

    CombatPhase[] helper = {CombatPhase.TURN_START, CombatPhase.PRE_ACTION, CombatPhase.ACTION, CombatPhase.POST_ACTION, CombatPhase.END_OF_TURN};
    // Master turn order. All turns follow this phase order by default.
    final LinkedList<CombatPhase> TURN_ORDER = new LinkedList<CombatPhase>(List.of(helper));

    HashMap<CombatEngine.EntityType, ArrayList<CombatEntity>> entities; // A collection of CombatEntities sorted by friendly or hostile
    HashMap<CombatEngine.EntityType, ArrayList<HashMap<CombatPhase, List<CombatEffect>>>> entityEffects; // A collection of sorted effects for all entities

    public CombatEngine() {

    }

    // Removes any effects with a duration of zero from all phases
    private void cleanup() {
            for (HashMap<CombatPhase, List<CombatEffect>> entityList : entityEffects.get(EntityType.FRIENDLY)) {
                for (CombatPhase phase : CombatPhase.values()) {
                    for (int i = 0; i < entityList.size(); i++) {
                        if (entityList.get(phase).get(i).getDuration() == 0) entityList.get(phase).removeIf(n -> n.getDuration() == 0);
                    }
                }
            }

            for (HashMap<CombatPhase, List<CombatEffect>> entityList : entityEffects.get(EntityType.HOSTILE)) {
                for (CombatPhase phase : CombatPhase.values()) {
                    for (int i = 0; i < entityList.size(); i++) {
                        if (entityList.get(phase).get(i).getDuration() == 0) entityList.get(phase).removeIf(n -> n.getDuration() == 0);
                    }
                }
            }
    }



}