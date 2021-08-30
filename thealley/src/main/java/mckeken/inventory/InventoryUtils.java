package mckeken.inventory;

import mckeken.item.Usable;
import mckeken.main.Manager;

import java.util.ArrayList;

public class InventoryUtils {

    // Returns a list of indexes that point to items that will heal you when used
    public static ArrayList<Integer> getHealingItems(Inventory inventory) {
        ArrayList<Integer> itemIDs = new ArrayList<>();

        ArrayList<Integer> usableIDS = new ArrayList<>(inventory.getItemIDs().stream().filter(integer -> Manager.itemList.get(integer) instanceof Usable).toList());

        if (usableIDS.size() == 0) return itemIDs;

        // Filter for any usable item that contains an effect that increases the value of the player's primary resource.
        // This filter breaks if any effects are introduced that have resource names as the first property and anything else as the second value.
        return new ArrayList<>(usableIDS.stream().filter(integer -> ((Usable) Manager.itemList.get(integer)).getEffects().stream().anyMatch(effect -> effect.getProperties()[0].equals(Manager.primaryResource) &&
                                                                                                                                                        Integer.parseInt(effect.getProperties()[1]) > 0)).toList());
    }

}
