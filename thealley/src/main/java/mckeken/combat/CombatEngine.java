package mckeken.combat;

import mckeken.combat.combatEffect.combatEffects.AddPhaseEffect;
import mckeken.combat.combatEffect.CombatEffect;
import mckeken.combat.combatEffect.combatEffects.DispelCombatEffect;
import mckeken.combat.combatEffect.combatEffects.RemovePhaseEffect;

import java.util.*;

public class CombatEngine {



    // Possible Phases of a turn. Note that the order listed here is interpreted as the phase order.
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

    String primaryResourceName; // The name of the resources that triggers death when it hits zero

    // Master turn order. All turns follow this phase order by default.
    final ArrayList<CombatPhase> PHASE_ORDER = new ArrayList<>(List.of(CombatPhase.values()));

    ArrayList<AbstractMap.SimpleEntry<CombatEngine.EntityType, Integer>> TURN_ORDER;

    HashMap<CombatEngine.EntityType, ArrayList<CombatEntity>> entities; // A collection of CombatEntities sorted by friendly or hostile
    HashMap<CombatEngine.EntityType, ArrayList<HashMap<CombatPhase, List<CombatEffect>>>> entityEffects; // A collection of sorted effects for all entities

    Iterator<AbstractMap.SimpleEntry<CombatEngine.EntityType, Integer>> entityIterator;

    public CombatEngine() {
        TURN_ORDER = getTurnOrder();
        entities = new HashMap<>();
        entityEffects = new HashMap<>();
        entityIterator = TURN_ORDER.iterator();
    }

    public CombatEngine(ArrayList<CombatEntity> friendlyEntities, ArrayList<CombatEntity> hostileEntities) {
        entities = new HashMap<>();
        entityEffects = new HashMap<>();

        // Set up entities
        entities.put(EntityType.FRIENDLY, friendlyEntities);
        entities.put(EntityType.HOSTILE, hostileEntities);

        // Set up phases
        entityEffects.put(EntityType.FRIENDLY, new ArrayList<HashMap<CombatPhase, List<CombatEffect>>>());
        entityEffects.put(EntityType.HOSTILE, new ArrayList<HashMap<CombatPhase, List<CombatEffect>>>());

        HashMap<CombatPhase, List<CombatEffect>> modelMap = new HashMap<>(); //  Create a master hashmap from which all the entity hashmaps will be copied
        for (CombatPhase phase : CombatPhase.values()) modelMap.put(phase, new ArrayList<>()); // Fill the model hashmap with an empty arraylist of effects for each phase

        for (CombatEntity entity : entities.get(EntityType.FRIENDLY)) entityEffects.get(EntityType.FRIENDLY).add(new HashMap<>(modelMap)); // For each friendly entity, create a phase hashmap
        for (CombatEntity entity : entities.get(EntityType.HOSTILE)) entityEffects.get(EntityType.HOSTILE).add(new HashMap<>(modelMap));   // For each hostile entity, create a phase hashmap

    }
    // *************************
    // **** Helper functions ***
    // *************************

    // TODO: Rewrite this late-term-abortion of a function
    // TODO: Change to private
    public ArrayList<AbstractMap.SimpleEntry<CombatEngine.EntityType, Integer>> getTurnOrder() {
        ArrayList<AbstractMap.SimpleEntry<CombatEngine.EntityType, Integer>> turnOrder = new ArrayList<AbstractMap.SimpleEntry<CombatEngine.EntityType, Integer>>();

        for (int i = 0; i < entities.get(EntityType.FRIENDLY).size(); i++) { // Iterate through the friendly entities (i=index in the list of friendly entities that we want to add to the turnOrder)
            if (turnOrder.isEmpty()) { // If the turn order is empty, just add the current entity
                turnOrder.add(new AbstractMap.SimpleEntry<CombatEngine.EntityType, Integer>(EntityType.FRIENDLY, i));
            } else { // If the turn order isn't empty, iterate until you find a slower entity, then insert directly in front of them
                for (int j = 0; j <= turnOrder.size(); j++) { // Iterate through each entity already in the turn order (j = index in the turnOrder list)
                    if (j == turnOrder.size()) {
                        turnOrder.add(new AbstractMap.SimpleEntry<CombatEngine.EntityType, Integer>(EntityType.FRIENDLY, i)); // If the for loop has reached an end without finding any slower entitys, append to the end of the list
                        break;
                    }

                    CombatEntity entityAlreadySorted = entities.get(turnOrder.get(j).getKey()).get(turnOrder.get(j).getValue());

                    if (entityAlreadySorted.speed < entities.get(EntityType.FRIENDLY).get(i).speed) { // If the entity currently being observed from the turnOrder list is slower than the current entity, insert the current entity at this index
                        turnOrder.add(j, new AbstractMap.SimpleEntry<CombatEngine.EntityType, Integer>(EntityType.FRIENDLY, i));
                        break;
                    }
                }

            }
        }

        for (int i = 0; i < entities.get(EntityType.HOSTILE).size(); i++) { // Iterate through the hostile entities (i=index in the list of hostile entities that we want to add to the turnOrder)
            if (turnOrder.isEmpty()) { // If the turn order is empty, just add the current entity
                turnOrder.add(new AbstractMap.SimpleEntry<CombatEngine.EntityType, Integer>(EntityType.HOSTILE, i));
            } else { // If the turn order isn't empty, iterate until you find a slower entity, then insert directly in front of them
                for (int j = 0; j <= turnOrder.size(); j++) { // Iterate through each entity already in the turn order (j = index in the turnOrder list)
                    if (j == turnOrder.size()) {
                        turnOrder.add(new AbstractMap.SimpleEntry<CombatEngine.EntityType, Integer>(EntityType.HOSTILE, i)); // If the for loop has reached an end without finding any slower entities, append to the end of the list
                        break;
                    }

                    CombatEntity entityAlreadySorted = entities.get(turnOrder.get(j).getKey()).get(turnOrder.get(j).getValue());

                    if (entityAlreadySorted.speed < entities.get(EntityType.HOSTILE).get(i).speed) { // If the entity currently being observed from the turnOrder list is slower than the current entity, insert the current entity at this index
                        turnOrder.add(j, new AbstractMap.SimpleEntry<CombatEngine.EntityType, Integer>(EntityType.HOSTILE, i));
                        break;
                    }
                }

            }
        }

        return turnOrder;
    }

    // Returns the entity of the specified type at the given index
    private CombatEntity lookUpEntity(EntityType type, int index) {
        return entities.get(type).get(index);
    }

    // Perform one full turn cycle
    private void turnCycle() {
        entityIterator = TURN_ORDER.iterator();
        while (entityIterator.hasNext()) { // While someone hasn't taken their turn yet
            AbstractMap.SimpleEntry<CombatEngine.EntityType, Integer> currentEntity = entityIterator.next(); // Get the next entity
            if (lookUpEntity(currentEntity.getKey(), currentEntity.getValue()).resourceManager.getResourceQuantity(primaryResourceName) >= 0) { // If the entity isn't dead
                turn(currentEntity.getKey(), currentEntity.getValue()); // Take the turn
            }

        }

    }

    // Take a turn for a single entity
    private void turn(EntityType type, int index) {
        ArrayList<CombatPhase> phaseOrder = PHASE_ORDER; // Establish initial phase order from master list
        for (int i = 0; i < phaseOrder.size(); i++) { // Iterate through the phase order by index. This is to allow for phases to be added or removed.

            // Perform phase effects and handle the special effect classes their own way.
            for (CombatEffect combatEffect : entityEffects.get(type).get(index).get(phaseOrder.get(i))) {
                switch (combatEffect.getClass().getSimpleName()) {
                    // TODO: Test
                    case "RemovePhaseEffect": // Handles the removal of a phase. Only works if the phase hasn't already occurred.

                        CombatPhase phaseToSkip = ((RemovePhaseEffect) combatEffect).getPhase(); // Get the phase we need to skip
                        if (phaseOrder.indexOf(phaseToSkip) > i) { // Check if it hasn't happened yet
                            phaseOrder.remove(phaseToSkip); // If it hasn't happened yet, remove its first occurrence
                        }

                        break;
                    // TODO: Test
                    case "AddPhaseEffect": // Handles the adding of a new phase to the current turn. Any phase may be added, and it is always added after the current phase

                        CombatPhase phaseToAdd = ((AddPhaseEffect) combatEffect).getPhase(); // Get the phase we need to add
                        if (i+1 >= phaseOrder.size()) { // Check if we are currently in the last phase
                            phaseOrder.add(phaseToAdd); // Append to list
                        } else { // If we aren't in the last phase
                            phaseOrder.add(i+1, phaseToAdd); // Insert new phase right after current phase
                        }


                        break;

                        // TODO: Test
                    case "DispelCombatEffect":

                        String className = ((DispelCombatEffect) combatEffect).getDispelInstanceOf();

                        for (CombatPhase phase : CombatPhase.values()) {
                            entityEffects.get(type).get(index).get(phase).removeIf(n -> n.getClass().getSimpleName().equals(className));
                        }

                        break;
                    default:
                        // TODO: Implement
                        // execute effect code

                }

                if (combatEffect.getDuration() > 0) combatEffect.setDuration(combatEffect.getDuration() - 1); // Decrement the duration if the duration is greater than 0.
                cleanup(phaseOrder.get(i)); //  Cleanup any dead effects in the current phase
            }

        }




    }



    private void performPhaseEffects(EntityType type, int index, CombatPhase phase) {

    }



    // Removes any effects with a duration of zero from all phases
    private void cleanup() {
        for (HashMap<CombatPhase, List<CombatEffect>> entityEffectList : entityEffects.get(EntityType.FRIENDLY)) {
            for (CombatPhase phase : CombatPhase.values()) {
                for (int i = 0; i < entityEffectList.size(); i++) {
                    if (entityEffectList.get(phase).get(i).getDuration() == 0) entityEffectList.get(phase).removeIf(n -> n.getDuration() == 0);
                }
            }
        }

        for (HashMap<CombatPhase, List<CombatEffect>> entityEffectList : entityEffects.get(EntityType.HOSTILE)) {
            for (CombatPhase phase : CombatPhase.values()) {
                for (int i = 0; i < entityEffectList.size(); i++) {
                    if (entityEffectList.get(phase).get(i).getDuration() == 0) entityEffectList.get(phase).removeIf(n -> n.getDuration() == 0);
                }
            }
        }
    }

    private void cleanup(CombatPhase phase) {
        for (HashMap<CombatPhase, List<CombatEffect>> map : entityEffects.get(EntityType.FRIENDLY)) {
            map.get(phase).removeIf(n -> n.getDuration() == 0);
        }

        for (HashMap<CombatPhase, List<CombatEffect>> map : entityEffects.get(EntityType.HOSTILE)) {
            map.get(phase).removeIf(n -> n.getDuration() == 0);
        }
    }

}