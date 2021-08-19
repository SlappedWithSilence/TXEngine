package mckeken.combat;

import mckeken.inventory.Inventory;

import java.util.Objects;

public class CombatEntity {
    String name;
    String openingDialog;
    String closingDialog;
    Inventory inventory;
    CombatResourceManager resourceManager;
    int speed; // Determines turn order

    public CombatEntity() {

    }

    public CombatEntity(String name, String openingDialog, String closingDialog, int inventorySize) {
        this.name = name;
        this.openingDialog = openingDialog;
        this.closingDialog = closingDialog;
        this.inventory = new Inventory(inventorySize);
    }

    public CombatEntity(String name, String openingDialog, String closingDialog, int inventorySize, Inventory inventory) {
        this.name = name;
        this.openingDialog = openingDialog;
        this.closingDialog = closingDialog;

        this.inventory = Objects.requireNonNullElseGet(inventory, () -> new Inventory(inventorySize));

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
}
