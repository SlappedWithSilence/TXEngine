package txengine.systems.combat;

import txengine.systems.inventory.Inventory;

import java.util.Objects;

public class Enemy {
    String name;
    String openingDialog;
    String closingDialog;
    Inventory inventory;

    public Enemy() {

    }

    public Enemy(String name, String openingDialog, String closingDialog, int inventorySize) {
        this.name = name;
        this.openingDialog = openingDialog;
        this.closingDialog = closingDialog;
        this.inventory = new Inventory(inventorySize);
    }

    public Enemy(String name, String openingDialog, String closingDialog, int inventorySize, Inventory inventory) {
        this.name = name;
        this.openingDialog = openingDialog;
        this.closingDialog = closingDialog;

        this.inventory = Objects.requireNonNullElseGet(inventory, () -> new Inventory(inventorySize));

    }


}
