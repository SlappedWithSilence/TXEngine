package mckeken.combat;

import mckeken.combat.ability.Ability;
import mckeken.combat.ability.AbilityManager;
import mckeken.inventory.Inventory;
import mckeken.item.Item;
import mckeken.item.Usable;
import mckeken.main.Manager;

import java.util.ArrayList;
import java.util.stream.Stream;

public class CombatEntityLogic {

    // TODO: Test
    public static ArrayList<Ability> getHealingAbilities(AbilityManager am) {
        ArrayList<Ability> abilities = new ArrayList<>();

        // Filter all the currently-satisfied abilities down to only the healing abilities
        Stream<Ability> abilityStream = am.getSatisfiedAbilities().stream().filter(CombatEntityLogic::isHealingAbility);

        return new ArrayList<>(abilityStream.toList());
    }

    // Check if the given ability
    public static boolean isHealingAbility(Ability a) {
        return a.getEffects().stream().anyMatch(effectEntry ->  effectEntry.getKey().getProperties()[0].equals(Manager.primaryResource) &&
                Integer.parseInt(effectEntry.getKey().getProperties()[1]) > 0);
    }

    // Returns a list of indexes that point to items that will heal you when used
    public static ArrayList<Integer> getHealingItems(Inventory inventory) {
        ArrayList<Integer> itemIDs = new ArrayList<>();

        ArrayList<Integer> usableIDS = new ArrayList<>(inventory.getItemIDs().stream().filter(integer -> Manager.itemList.get(integer) instanceof Usable).toList());

        if (usableIDS.size() == 0) return itemIDs;

        // Filter for any usable item that contains an effect that increases the value of the player's primary resource.
        // This filter breaks if any effects are introduced that have resource names as the first property and anything else as the second value.
        return new ArrayList<>(usableIDS.stream().filter(CombatEntityLogic::isHealingItem).toList());
    }

    public static boolean isHealingItem(Item i) {
        if (!(i instanceof Usable)) return false;

        return ((Usable) i).getEffects().stream().anyMatch(effect -> effect.getProperties()[0].equals(Manager.primaryResource) &&
                Integer.parseInt(effect.getProperties()[1]) > 0);
    }

    public static boolean isHealingItem(int i) {
        if (!(Manager.itemList.get(i) instanceof Usable)) return false;

        return ((Usable) Manager.itemList.get(i)).getEffects().stream().anyMatch(effect -> effect.getProperties()[0].equals(Manager.primaryResource) &&
                Integer.parseInt(effect.getProperties()[1]) > 0);
    }

}
