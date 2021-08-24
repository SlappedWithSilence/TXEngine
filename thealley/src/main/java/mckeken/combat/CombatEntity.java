package mckeken.combat;

import mckeken.combat.ability.Ability;
import mckeken.combat.ability.AbilityManager;
import mckeken.combat.combatEffect.CombatEffect;
import mckeken.inventory.Inventory;
import mckeken.item.Item;
import mckeken.item.effect.Effect;
import mckeken.main.Manager;
import mckeken.room.action.actions.conversation.events.ItemEvent;

import java.util.AbstractMap;
import java.util.Objects;
import java.util.Optional;

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

    // This method will return a Combat choice using a standard AI. It will optimize for damage and try to prevent itself from being killed.
    @Override
    public AbstractMap.SimpleEntry<Ability, Item> makeChoice(Optional<CombatEngine> engine) {
        return null;
    }


}
