package txengine.systems.combat;

import com.rits.cloning.Cloner;
import txengine.systems.ability.Ability;
import txengine.systems.ability.AbilityManager;
import txengine.systems.combat.combatEffect.CombatEffect;
import txengine.systems.inventory.Inventory;
import txengine.systems.item.Equipment;
import txengine.systems.item.Item;
import txengine.main.Manager;
import txengine.ui.component.Components;

import java.util.*;

public class CombatEntity implements CombatAgency, Components.Tabable {

    /************************
     *   Member Variables   *
     ************************/

    String name;
    String openingDialog;
    String closingDialog;
    Inventory inventory;
    CombatResourceManager resourceManager;
    AbilityManager abilityManager;
    EquipmentManager equipmentManager;
    int speed; // Determines turn order
    int level;
    int xpYield;

    HashMap<CombatEngine.CombatPhase, List<CombatEffect>> combatEffects;

    /********************
     *   Constructors   *
     ********************/

    public CombatEntity() {
        name = "CombatEntity";
        openingDialog = "Opening Dialog";
        closingDialog = "Closing Dialog";
        inventory = new Inventory();
        resourceManager = new CombatResourceManager();
        abilityManager = new AbilityManager();
        abilityManager.setOwner(this);
        equipmentManager = new EquipmentManager();

        speed = 0;
        combatEffects = getPhaseMap();
        level = 1;
        xpYield = 10;
    }

    public CombatEntity(CombatEntity clone) {
        name = clone.name;
        openingDialog = clone.openingDialog;
        closingDialog = clone.closingDialog;
        inventory = new Inventory(clone.inventory);
        resourceManager = new CombatResourceManager(clone.resourceManager);
        abilityManager = new AbilityManager(clone.abilityManager);
        abilityManager.setOwner(this);
        equipmentManager = clone.equipmentManager;

        speed = clone.speed;
        combatEffects = getPhaseMap();
        level = clone.level;
        equipmentManager = new EquipmentManager(clone.equipmentManager);
        xpYield = clone.xpYield;
    }

    public CombatEntity(String name, String openingDialog, String closingDialog, int inventorySize) {
        this.name = name;
        this.openingDialog = openingDialog;
        this.closingDialog = closingDialog;
        this.inventory = new Inventory(inventorySize);
        this.abilityManager = new AbilityManager();
        abilityManager.setOwner(this);
        equipmentManager = new EquipmentManager();

        this.resourceManager = new CombatResourceManager();
        speed = 0;
        combatEffects = getPhaseMap();
        level = 1;
        xpYield = 10;
    }

    public CombatEntity(String name, String openingDialog, String closingDialog, int inventorySize, Inventory inventory) {
        this.name = name;
        this.openingDialog = openingDialog;
        this.closingDialog = closingDialog;

        this.inventory = Objects.requireNonNullElseGet(inventory, () -> new Inventory(inventorySize));
        this.abilityManager = new AbilityManager();
        abilityManager.setOwner(this);

        this.resourceManager = new CombatResourceManager();
        this.equipmentManager = new EquipmentManager();
        speed = 0;
        combatEffects = getPhaseMap();
        level = 1;
        xpYield = 10;
    }

    // Full constructor. All inputs necessary to instantiate a full-fledged combatEntity are required for this constructor.
    public CombatEntity(String name, String openingDialog, String closingDialog, int inventorySize, Inventory inventory, AbilityManager abilityManager, CombatResourceManager resourceManager, EquipmentManager equipmentManager, int speed, int level, int xpYield) {
        this.name = name;
        this.openingDialog = openingDialog;
        this.closingDialog = closingDialog;

        this.equipmentManager = equipmentManager;

        this.inventory = Objects.requireNonNullElseGet(inventory, () -> new Inventory(inventorySize));
        this.abilityManager = abilityManager;
        abilityManager.setOwner(this);
        this.resourceManager = resourceManager;
        this.speed = speed;
        combatEffects = getPhaseMap();

        this.level = level;
        this.xpYield = xpYield;
    }

    /************************
     *   Public Functions   *
     ************************/

    // This returns a Combat choice using a standard AI. It will optimize for damage and try to prevent itself from being killed.
    @Override
    public AbstractMap.SimpleEntry<Ability, Item> makeChoice(CombatEngine engine) {
        final double PRIMARY_RESOURCE_TOLERANCE = 0.25; // The lowest the entity's primary resource can be before it tries to restore it

        // Check for primary resource value. If it is too low, attempt to heal.
        if (this.getResourceManager().resourcePercentage(Manager.primaryResource) <= PRIMARY_RESOURCE_TOLERANCE) {
            ArrayList<Integer> healingItems = CombatEntityLogic.getHealingItems(this.getInventory());

            // If the entity possesses healing items in its inventory, use a random one
            if (healingItems.size() > 0) return new AbstractMap.SimpleEntry<>(null, Manager.itemHashMap.get(healingItems.get(new Random().nextInt(healingItems.size())))); // TODO: Verify that this won't go out of bounds

            ArrayList<Ability> healingAbilities = CombatEntityLogic.getHealingAbilities(this.abilityManager);

            if (healingAbilities.size() > 0) return new AbstractMap.SimpleEntry<>(healingAbilities.get(new Random().nextInt(healingAbilities.size() - 1)), null);
        }

        List<Ability> offensiveAbilities = CombatEntityLogic.getOffensiveAbilities(abilityManager);

        // Try and select an offensive ability to use
        for (Ability ab : offensiveAbilities) { // Iterate through all offensive abilities

            List<CombatEntity> killable = CombatEntityLogic.getKillableEntities(ab, engine.getValidTargets(ab)); // Get all entities that can be killed via the current ability

            if (killable.size() > 0) { // If there is at least one target that can be killed
                int randomIndex = new Random().nextInt(killable.size()); // Randomly select a target

                ab.setTarget(killable.get(randomIndex)); // Set it

                return new AbstractMap.SimpleEntry<Ability, Item>(ab, null); // Return the ability with its target already set
            }
        }

        // If we can't kill anything, use a random offensive ability with a random target
        if (offensiveAbilities.size() > 0) {
            int randomAbilityIndex = new Random().nextInt(offensiveAbilities.size());

            int randomTargetIndex = new Random().nextInt(engine.getValidTargets(offensiveAbilities.get(randomAbilityIndex)).size());

            Ability ability = offensiveAbilities.get(randomAbilityIndex);
            ability.setTarget(engine.getValidTargets(ability).get(randomTargetIndex));

            return new AbstractMap.SimpleEntry<Ability, Item>(ability, null);
        }


        // If we can't kill anything and have no offensive abilities, use a random ability
        if (abilityManager.getSatisfiedAbilities().size() > 0) {
            Ability ability = abilityManager.getSatisfiedAbilities().get(new Random().nextInt(abilityManager.getSatisfiedAbilities().size()));

            ability.setTarget(engine.getValidTargets(ability).get(new Random().nextInt(engine.getValidTargets(ability).size())));

            return new AbstractMap.SimpleEntry<>(ability, null);
        }

        // If we can't do any of the above, do nothing
        return new AbstractMap.SimpleEntry<>(null, null);
    }

    public boolean isDead() {
        return resourceManager.getResourceQuantity(Manager.primaryResource) <= 0;
    }

    // Add all the effects in a given ability to the entity's effects
    public void handleAbility(Ability ability) {

        Cloner cloner = new Cloner();
        for (AbstractMap.SimpleEntry<CombatEffect, CombatEngine.CombatPhase> ce : ability.getEffects()) { // Iterate through each effect in the ability
            combatEffects.get(ce.getValue()).add(cloner.deepClone(ce.getKey())); // Add a deep clone of effect to this entity's effect map
        }
    }

    public int takeDamage(int damage) {
        equipmentManager.getDamageResistance();

        resourceManager.decrementResource(Manager.primaryResource, Math.max(0,damage - equipmentManager.getDamageResistance())); // Damage this entity's primary resource by the ability's quantity

        return Math.max(0,damage - equipmentManager.getDamageResistance());
    }

    // Removes any effects with a duration of zero from all phases. An effect with duration of -1 should last forever, so it is ignored.
    public void cleanupEffects() {
        for (CombatEngine.CombatPhase phase : CombatEngine.CombatPhase.values()) {
            combatEffects.get(phase).stream().filter(n -> n.getDuration() == 0).forEach(combatEffect -> System.out.println(combatEffect.getCleanupMessage().replace("{OWNER}", name)));
            combatEffects.get(phase).removeIf(n -> n.getDuration() == 0);
        }
    }

    /*************************
     *    Helper Functions   *
     *************************/

    // Returns an empty hashmap used for storing CombatEffects
    private  HashMap<CombatEngine.CombatPhase, List<CombatEffect>> getPhaseMap() {
        HashMap<CombatEngine.CombatPhase, List<CombatEffect>> modelMap = new HashMap<>(); //  Create a master hashmap from which all the entity hashmaps will be copied
        for (CombatEngine.CombatPhase phase : CombatEngine.CombatPhase.values()) modelMap.put(phase, new ArrayList<>()); // Fill the model hashmap with an empty arraylist of effects for each phase

        return modelMap;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        String nameText = name;
        String pResourceText = Manager.primaryResource + ": [" + getResourceManager().getResources().get(Manager.primaryResource)[1]
                                                        + "/"
                                                        + getResourceManager().getResources().get(Manager.primaryResource)[1]
                                                        + "]";
        sb.append(nameText).append("\n");
        sb.append(pResourceText);

        return sb.toString();
    }

    @Override
    public List<String> getTabData() {
        String nameText = name;
        String levelText = "lvl: " + level;
        String pResourceText = Manager.primaryResource + ": [" + getResourceManager().getResources().get(Manager.primaryResource)[1]
                + "/"
                + getResourceManager().getResources().get(Manager.primaryResource)[0]
                + "]";

        return List.of(new String[]{nameText, levelText, pResourceText});
    }


    /***************************
     *   Getters and Setters   *
     ***************************/

    public EquipmentManager getEquipmentManager() {
        return equipmentManager;
    }

    public void setEquipmentManager(EquipmentManager equipmentManager) {
        this.equipmentManager = equipmentManager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpeningDialog() {
        return openingDialog;
    }

    public void setOpeningDialog(String openingDialog) {
        this.openingDialog = openingDialog;
    }

    public String getClosingDialog() {
        return closingDialog;
    }

    public void setClosingDialog(String closingDialog) {
        this.closingDialog = closingDialog;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public CombatResourceManager getResourceManager() {
        return resourceManager;
    }

    public void setResourceManager(CombatResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public AbilityManager getAbilityManager() {
        return abilityManager;
    }

    public void setAbilityManager(AbilityManager abilityManager) {
        this.abilityManager = abilityManager;
    }

    public HashMap<CombatEngine.CombatPhase, List<CombatEffect>> getCombatEffects() {
        return combatEffects;
    }

    public void setCombatEffects(HashMap<CombatEngine.CombatPhase, List<CombatEffect>> combatEffects) {
        this.combatEffects = combatEffects;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXpYield() {
        return xpYield;
    }

    public void setXpYield(int xpYield) {
        this.xpYield = xpYield;
    }
}
