package mckeken.combat;

import mckeken.combat.ability.Ability;
import mckeken.combat.combatEffect.CombatEffect;
import mckeken.combat.combatEffect.combatEffects.AddPhaseEffect;
import mckeken.combat.combatEffect.combatEffects.DispelCombatEffect;
import mckeken.combat.combatEffect.combatEffects.RemovePhaseEffect;
import mckeken.io.LogUtils;
import mckeken.item.Item;
import mckeken.item.Usable;
import mckeken.main.Manager;

import java.util.*;

public class CombatEngine {

    /*********************
     *   Helper Classes  *
     *********************/

    // An object that implements EndCondition calculates whether a given CombatEngine instance should end combat.
    // Any CombatEngine may contain an arbitrary number of EndConditions. Each one is checked after each phase. If one returns true, combat ends.
    interface EndCondition {

        enum gameState {
            WIN,
            LOSS
        }

        // Arbitrarily calculates if combat should end. This functions returns a pair of values.
        // The key is whether combat should end, and the value represents if the player has won combat. WIN signals victory, LOSS signals defeat.
        // Only a pair with a key value of true will be evaluated. If multiple end-conditions are satisfied simultaneously, the first one in the endConditions list is chosen.
        AbstractMap.SimpleEntry<Boolean, gameState> satisfied(CombatEngine engine);
    }

    /*******************
     *   Helper Enums  *
     *******************/

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
        ALL_ENEMY,
        SELF
    }

    /********************
     * Member Variables *
     ********************/

    String primaryResourceName; // The name of the resources that triggers death when it hits zero

    // Master turn order. All turns follow this phase order by default.
    final ArrayList<CombatPhase> PHASE_ORDER = new ArrayList<>(List.of(CombatPhase.values()));

    ArrayList<AbstractMap.SimpleEntry<CombatEngine.EntityType, Integer>> TURN_ORDER;

    HashMap<CombatEngine.EntityType, ArrayList<CombatEntity>> entities; // A collection of CombatEntities sorted by friendly or hostile

    Iterator<AbstractMap.SimpleEntry<CombatEngine.EntityType, Integer>> entityIterator;

    List<EndCondition> endConditions;

    EndCondition.gameState endState = null;

    EntityType currentTurnType = null;

    final String TARGET_NAME_PLACEHOLDER = "{TARGET}";
    final String CASTER_NAME_PLACEHOLDER = "{CASTER}";
    final String OWNER_NAME_PLACEHOLDER = "{OWNER}";

    /****************
     * Constructors *
     ****************/

    public CombatEngine() {
        TURN_ORDER = getTurnOrder();
        entities = new HashMap<>();
        //entityEffects = new HashMap<>();
        entityIterator = TURN_ORDER.iterator();
        primaryResourceName = Manager.player.getResourceManager().resources.firstKey();
        endConditions = new ArrayList<>();
        endConditions.add(getDefaultEndCondition());
    }

    public CombatEngine(ArrayList<CombatEntity> friendlyEntities, ArrayList<CombatEntity> hostileEntities) {
        entities = new HashMap<>();

        // Set up entities
        entities.put(EntityType.FRIENDLY, friendlyEntities);
        entities.put(EntityType.HOSTILE, hostileEntities);

        primaryResourceName = Manager.primaryResource;

        endConditions = new ArrayList<>();
        endConditions.add(getDefaultEndCondition());
    }

    public CombatEngine(ArrayList<CombatEntity> friendlyEntities, ArrayList<CombatEntity> hostileEntities, String primaryResourceName) {
        entities = new HashMap<>();

        // Set up entities
        entities.put(EntityType.FRIENDLY, friendlyEntities);
        entities.put(EntityType.HOSTILE, hostileEntities);

        this.primaryResourceName = primaryResourceName;

        endConditions = new ArrayList<>();
        endConditions.add(getDefaultEndCondition());
    }

    public CombatEngine(ArrayList<CombatEntity> friendlyEntities, ArrayList<CombatEntity> hostileEntities, String primaryResourceName, List<EndCondition> endConditions) {
        entities = new HashMap<>();

        // Set up entities
        entities.put(EntityType.FRIENDLY, friendlyEntities);
        entities.put(EntityType.HOSTILE, hostileEntities);

        this.primaryResourceName = primaryResourceName;

        this.endConditions = new ArrayList<>(endConditions);
    }

    /********************
     * Public Functions *
     ********************/


    public boolean startCombat() {
        entities.get(EntityType.FRIENDLY).add(Manager.player);
        TURN_ORDER = getTurnOrder();

        LogUtils.error("Starting combat\n"); //TODO: Remove
        while (endState == null) {
            turnCycle();
        }


        if (endState == EndCondition.gameState.WIN) return true;
        else if (endState == EndCondition.gameState.LOSS) return false;

        return false;
    }

    public final ArrayList<CombatEntity> getEntities(EntityType type) {
        return entities.get(type);
    }

    /*********************
     * Private Functions *
     *********************/

    // Returns a default EndCondition that checks for two things:
    // - The player's death (loss)
    // - The death of all enemies (win)
    private EndCondition getDefaultEndCondition() {
        return engine -> {
            if (Manager.player.getResourceManager().getResourceQuantity(primaryResourceName) <= 0) {
                return new AbstractMap.SimpleEntry<>(true, EndCondition.gameState.LOSS);
            }

            if (entities.get(EntityType.HOSTILE).stream().allMatch(n -> n.resourceManager.getResourceQuantity(primaryResourceName) <= 0)) { // If all enemies are dead
                return new AbstractMap.SimpleEntry<>(true, EndCondition.gameState.WIN); // Return a win
            }

            return new AbstractMap.SimpleEntry<>(false, null);
        };
    }

    public void addEndCondition(EndCondition condition) {
        endConditions.add(condition);
    }

    // TODO: Rewrite this late-term-abortion of a function
    // TODO: Change to private
    public ArrayList<AbstractMap.SimpleEntry<CombatEngine.EntityType, Integer>> getTurnOrder() {
        ArrayList<AbstractMap.SimpleEntry<CombatEngine.EntityType, Integer>> turnOrder = new ArrayList<>();

        for (int i = 0; i < entities.get(EntityType.FRIENDLY).size(); i++) { // Iterate through the friendly entities (i=index in the list of friendly entities that we want to add to the turnOrder)
            if (turnOrder.isEmpty()) { // If the turn order is empty, just add the current entity
                turnOrder.add(new AbstractMap.SimpleEntry<>(EntityType.FRIENDLY, i));
            } else { // If the turn order isn't empty, iterate until you find a slower entity, then insert directly in front of them
                for (int j = 0; j <= turnOrder.size(); j++) { // Iterate through each entity already in the turn order (j = index in the turnOrder list)
                    if (j == turnOrder.size()) {
                        turnOrder.add(new AbstractMap.SimpleEntry<>(EntityType.FRIENDLY, i)); // If the for loop has reached an end without finding any slower entities, append to the end of the list
                        break;
                    }

                    CombatEntity entityAlreadySorted = entities.get(turnOrder.get(j).getKey()).get(turnOrder.get(j).getValue());

                    if (entityAlreadySorted.speed < entities.get(EntityType.FRIENDLY).get(i).speed) { // If the entity currently being observed from the turnOrder list is slower than the current entity, insert the current entity at this index
                        turnOrder.add(j, new AbstractMap.SimpleEntry<>(EntityType.FRIENDLY, i));
                        break;
                    }
                }

            }
        }

        for (int i = 0; i < entities.get(EntityType.HOSTILE).size(); i++) { // Iterate through the hostile entities (i=index in the list of hostile entities that we want to add to the turnOrder)
            if (turnOrder.isEmpty()) { // If the turn order is empty, just add the current entity
                turnOrder.add(new AbstractMap.SimpleEntry<>(EntityType.HOSTILE, i));
            } else { // If the turn order isn't empty, iterate until you find a slower entity, then insert directly in front of them
                for (int j = 0; j <= turnOrder.size(); j++) { // Iterate through each entity already in the turn order (j = index in the turnOrder list)
                    if (j == turnOrder.size()) {
                        turnOrder.add(new AbstractMap.SimpleEntry<>(EntityType.HOSTILE, i)); // If the for loop has reached an end without finding any slower entities, append to the end of the list
                        break;
                    }

                    CombatEntity entityAlreadySorted = entities.get(turnOrder.get(j).getKey()).get(turnOrder.get(j).getValue());

                    if (entityAlreadySorted.speed < entities.get(EntityType.HOSTILE).get(i).speed) { // If the entity currently being observed from the turnOrder list is slower than the current entity, insert the current entity at this index
                        turnOrder.add(j, new AbstractMap.SimpleEntry<>(EntityType.HOSTILE, i));
                        break;
                    }
                }

            }
        }

        return turnOrder;
    }

    private AbstractMap.SimpleEntry<Boolean, EndCondition.gameState> endConditionReached() {
        for (EndCondition condition : endConditions) {
            if (condition.satisfied(this).getKey()) {
                return condition.satisfied(this);
            }
        }

        return null;
    }

    // Returns the entity of the specified type at the given index
    private CombatEntity lookUpEntity(EntityType type, int index) {
        return entities.get(type).get(index);
    }

    // Get the valid targets for an entity using an ability
    public List<CombatEntity> getValidTargets(Ability ability) {
        switch (ability.getTargetMode()) {
            case SINGLE:
                ArrayList<CombatEntity> arr = new ArrayList<>(entities.get(EntityType.FRIENDLY));
                arr.addAll(entities.get(EntityType.HOSTILE).stream().filter(entity -> !entity.isDead()).toList());
                return arr;
            case SINGLE_ENEMY:
                if (currentTurnType == EntityType.HOSTILE) return entities.get(EntityType.FRIENDLY).stream().filter(entity -> !entity.isDead()).toList();
                if (currentTurnType == EntityType.FRIENDLY) return entities.get(EntityType.HOSTILE).stream().filter(entity -> !entity.isDead()).toList();
                break;
            case SINGLE_FRIENDLY:
                if (currentTurnType == EntityType.HOSTILE)  return entities.get(EntityType.HOSTILE).stream().filter(entity -> !entity.isDead()).toList();
                if (currentTurnType == EntityType.FRIENDLY) return entities.get(EntityType.FRIENDLY).stream().filter(entity -> !entity.isDead()).toList();
                break;
            default:
                return null;
        }

        return null;
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
    private void turn(EntityType turnType, int index) {
        System.out.println();

        currentTurnType = turnType; // Set the global variable so that other functions can tell what turn type it is

        ArrayList<CombatPhase> phaseOrder = PHASE_ORDER; // Establish initial phase order from master list
        for (int i = 0; i < phaseOrder.size(); i++) { // Iterate through the phase order by index. This is to allow for phases to be added or removed.

            // If the current phase is the ACTION phase, then run the get-action logic
            if (phaseOrder.get(i) == CombatPhase.ACTION) {
                AbstractMap.SimpleEntry<Ability, Item> combatAction = lookUpEntity(turnType, index).makeChoice(this);

                if (combatAction.getKey() != null) { // If the entity chose to use an ability

                    lookUpEntity(turnType, index).getAbilityManager().payCosts(combatAction.getKey()); // Make the user pay the costs for their ability

                    switch (combatAction.getKey().getTargetMode()) { // Handle abilities based on the target type
                        case ALL: // Apply the ability to all entities.
                            for (CombatEntity e : entities.get(EntityType.FRIENDLY)) e.handleAbility(combatAction.getKey());
                            for (CombatEntity e : entities.get(EntityType.HOSTILE))  e.handleAbility(combatAction.getKey());

                            System.out.println(combatAction.getKey().getUseText().replace(CASTER_NAME_PLACEHOLDER, lookUpEntity(turnType, index).getName()));
                            break;
                        case SELF: // Apply the ability to the entity whose turn it is
                            entities.get(turnType).get(index).handleAbility(combatAction.getKey());

                            System.out.println(combatAction.getKey().getUseText().replace(CASTER_NAME_PLACEHOLDER, lookUpEntity(turnType, index).getName()));
                            break;
                        case SINGLE: // Apply the ability to the target
                        case SINGLE_ENEMY: // Apply the ability to the target
                        case SINGLE_FRIENDLY: // Apply the ability to the target
                            combatAction.getKey().getTarget().handleAbility(combatAction.getKey());

                            System.out.println(combatAction.getKey().getUseText().replace(CASTER_NAME_PLACEHOLDER, lookUpEntity(turnType, index).getName()).replace(TARGET_NAME_PLACEHOLDER, combatAction.getKey().getTarget().getName()));
                            break;
                        case ALL_ENEMY: // Apply the ability to all enemies, relative to the current entity's type. A hostile entity (relative to the player) would apply this type of ability to all friendly entities (relative to the player)
                            if (turnType == EntityType.FRIENDLY) for (CombatEntity e : entities.get(EntityType.HOSTILE))  e.handleAbility(combatAction.getKey());
                            else                                 for (CombatEntity e : entities.get(EntityType.FRIENDLY)) e.handleAbility(combatAction.getKey());

                            System.out.println(combatAction.getKey().getUseText().replace(CASTER_NAME_PLACEHOLDER, lookUpEntity(turnType, index).getName()));
                            break;
                        case ALL_FRIENDLY:
                            if (turnType == EntityType.FRIENDLY) for (CombatEntity e : entities.get(EntityType.FRIENDLY)) e.handleAbility(combatAction.getKey());
                            else                                 for (CombatEntity e : entities.get(EntityType.HOSTILE))  e.handleAbility(combatAction.getKey());

                            System.out.println(combatAction.getKey().getUseText().replace(CASTER_NAME_PLACEHOLDER, lookUpEntity(turnType, index).getName()));
                            break;
                        default:
                    }

                }

                if (combatAction.getValue() != null) { // If the user wants to use an item, use it.
                    if (combatAction.getValue() instanceof Usable) ((Usable) combatAction.getValue()).use(lookUpEntity(turnType, index));
                    else System.out.println("You tried to use " + combatAction.getValue().getName() + " but it did nothing."); // If the entity selected an Item that can't be used, say so
                }

                if (combatAction.getKey() == null && combatAction.getValue() == null) { // If for some reason an entity can't choose anything, skip then.
                    System.out.println(lookUpEntity(turnType, index).getName() + " was unable to act and lost the initiative.");
                }

            }

            // Perform phase effects and handle the special effect classes their own way.
            for (CombatEffect combatEffect : lookUpEntity(turnType, index).getCombatEffects().get(phaseOrder.get(i))) {
                switch (combatEffect.getClass().getSimpleName()) {
                    // TODO: Test
                    case "RemovePhaseEffect" -> { // Handles the removal of a phase. Only works if the phase hasn't already occurred.

                        CombatPhase phaseToSkip = ((RemovePhaseEffect) combatEffect).getPhase(); // Get the phase we need to skip
                        if (phaseOrder.indexOf(phaseToSkip) > i) { // Check if it hasn't happened yet
                            phaseOrder.remove(phaseToSkip); // If it hasn't happened yet, remove its first occurrence
                        }
                    }
                    // TODO: Test
                    case "AddPhaseEffect" -> { // Handles the adding of a new phase to the current turn. Any phase may be added, and it is always added after the current phase

                        CombatPhase phaseToAdd = ((AddPhaseEffect) combatEffect).getPhase(); // Get the phase we need to add
                        if (i + 1 >= phaseOrder.size()) { // Check if we are currently in the last phase
                            phaseOrder.add(phaseToAdd); // Append to list
                        } else { // If we aren't in the last phase
                            phaseOrder.add(i + 1, phaseToAdd); // Insert new phase right after current phase
                        }
                    }

                    // TODO: Test
                    case "DispelCombatEffect" -> { // Specially handle a DispelCombatEffect

                        String className = ((DispelCombatEffect) combatEffect).getDispelInstanceOf(); // Get the name of the class of CombatEffect that we need to remove
                        for (CombatPhase phase : CombatPhase.values()) { // For each combat phase
                            lookUpEntity(turnType, index).getCombatEffects().get(phase).removeIf(n -> n.getClass().getSimpleName().equals(className)); // If the entity whose turn it currently is has any instances of the aforementioned CombatEffect class, remove them.
                        }
                    }
                    default -> // If the effect is a standard effect, just execute it.
                            combatEffect.perform(entities.get(turnType).get(index));
                }

                if (combatEffect.getDuration() > 0) combatEffect.setDuration(combatEffect.getDuration() - 1); // Decrement the duration if the duration is greater than 0.

                if (!combatEffect.getTriggerMessage().equals("")) {
                    System.out.println(combatEffect.getTriggerMessage().replace(OWNER_NAME_PLACEHOLDER, lookUpEntity(turnType, index).getName()));
                }
            }

            if (endConditionReached() != null) { // If combat needs to end
                endState = endConditionReached().getValue(); // set the global endstate value to true if it was a win, false it was a loss.
            }
            lookUpEntity(turnType, index).cleanupEffects();
        }
    }



}