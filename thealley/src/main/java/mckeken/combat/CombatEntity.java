package mckeken.combat;

import mckeken.combat.ability.Ability;
import mckeken.combat.ability.AbilityManager;
import mckeken.combat.ability.AbilityUtils;
import mckeken.inventory.Inventory;
import mckeken.inventory.InventoryUtils;
import mckeken.item.Item;
import mckeken.main.Manager;

import java.util.*;

public class CombatEntity implements CombatAgency {

    String name;
    String openingDialog;
    String closingDialog;
    Inventory inventory;
    CombatResourceManager resourceManager;
    AbilityManager abilityManager;
    int speed; // Determines turn order

    public CombatEntity() {
        name = "CombatEntity";
        openingDialog = "Opening Dialog";
        closingDialog = "Closing Dialog";
        inventory = new Inventory();
        resourceManager = new CombatResourceManager();
        abilityManager = new AbilityManager();
        speed = 0;
    }

    public CombatEntity(String name, String openingDialog, String closingDialog, int inventorySize) {
        this.name = name;
        this.openingDialog = openingDialog;
        this.closingDialog = closingDialog;
        this.inventory = new Inventory(inventorySize);
        this.abilityManager = new AbilityManager();
        this.resourceManager = new CombatResourceManager();
        speed = 0;
    }

    public CombatEntity(String name, String openingDialog, String closingDialog, int inventorySize, Inventory inventory) {
        this.name = name;
        this.openingDialog = openingDialog;
        this.closingDialog = closingDialog;

        this.inventory = Objects.requireNonNullElseGet(inventory, () -> new Inventory(inventorySize));
        this.abilityManager = new AbilityManager();
        this.resourceManager = new CombatResourceManager();
        speed = 0;
    }

    // Full constructor. All inputs necessary to instantiate a full-fledged combatEntity are required for this constructor.
    public CombatEntity(String name, String openingDialog, String closingDialog, int inventorySize, Inventory inventory, AbilityManager abilityManager, CombatResourceManager resourceManager, int speed) {
        this.name = name;
        this.openingDialog = openingDialog;
        this.closingDialog = closingDialog;

        this.inventory = Objects.requireNonNullElseGet(inventory, () -> new Inventory(inventorySize));
        this.abilityManager = abilityManager;
        this.resourceManager = resourceManager;
        this.speed = speed;
    }

    // This returns a Combat choice using a standard AI. It will optimize for damage and try to prevent itself from being killed.
    @Override
    public AbstractMap.SimpleEntry<Ability, Item> makeChoice(Optional<CombatEngine> engine) {
        final double PRIMARY_RESOURCE_TOLERANCE = 0.25; // The lowest the entity's primary resource can be before it tries to restore it

        // Check for primary resource value. If it is too low, attempt to heal.
        if (this.getResourceManager().resourcePercentage(Manager.primaryResource) <= PRIMARY_RESOURCE_TOLERANCE) {
            ArrayList<Integer> healingItems = InventoryUtils.getHealingItems(this.getInventory());

            // If the entity possesses healing items in its inventory, use a random one
            if (healingItems.size() > 0) return new AbstractMap.SimpleEntry<>(null, Manager.itemList.get(healingItems.get(new Random().nextInt(healingItems.size() - 1)))); // TODO: Verify that this won't go out of bounds

            ArrayList<Ability> healingAbilities = AbilityUtils.getHealingAbilities(this.abilityManager);

            if (healingAbilities.size() > 0) return new AbstractMap.SimpleEntry<>(healingAbilities.get(new Random().nextInt(healingAbilities.size() - 1)), null);
        }


        return new AbstractMap.SimpleEntry<>(null, null);
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




}
