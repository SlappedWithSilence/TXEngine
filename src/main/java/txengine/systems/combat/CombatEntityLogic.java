package txengine.systems.combat;

import txengine.structures.Pair;
import txengine.systems.ability.Ability;
import txengine.systems.ability.AbilityManager;
import txengine.systems.combat.combatEffect.CombatEffect;
import txengine.systems.inventory.Inventory;
import txengine.systems.item.Item;
import txengine.systems.item.Usable;
import txengine.main.Manager;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
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
        return a.getEffects().stream().anyMatch(effectEntry ->  effectEntry.getKey().getProperties()[0].equals(Manager.primaryResource)
                                                                &&
                                                                Integer.parseInt(effectEntry.getKey().getProperties()[1]) > 0);
    }

    // Returns a list of indexes that point to items that will heal you when used
    public static ArrayList<Integer> getHealingItems(Inventory inventory) {
        ArrayList<Integer> itemIDs = new ArrayList<>();

        ArrayList<Integer> usableIDS = new ArrayList<>(inventory.getItemIDs().stream().filter(integer -> Manager.itemManager.get_instance(integer) instanceof Usable).toList());

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
        if (!(Manager.itemManager.get_instance(i) instanceof Usable)) return false;

        return ((Usable) Manager.itemManager.get_instance(i)).getEffects().stream().anyMatch(effect -> effect.getProperties()[0].equals(Manager.primaryResource) &&
                Double.parseDouble(effect.getProperties()[1]) > 0);
    }

    public static List<Ability> getOffensiveAbilities(AbilityManager abilityManager) {
        return abilityManager.getAbilityList().stream().filter(abilityManager::isSatisfied).filter(CombatEntityLogic::isOffensiveAbility).toList();
    }

    public static boolean isOffensiveAbility(Ability a) {
        if (a.getTargetMode() == CombatEngine.TargetMode.SINGLE_FRIENDLY || a.getTargetMode() == CombatEngine.TargetMode.ALL_FRIENDLY || a.getTargetMode() == CombatEngine.TargetMode.SELF ) return false;

        if (a.getDamage() > 0) return true;


        return a.getEffects().stream().anyMatch(pair -> pair.getKey().getProperties()[0].equals(Manager.primaryResource) &&
                                                        Double.parseDouble(pair.getKey().getProperties()[1]) < 0);
    }

    public static boolean canKill(Ability ab, CombatEntity target) {

        if (ab.getDamage() >= target.getResourceManager().getResourceQuantity(Manager.primaryResource)) return true;

        int totalDamage = ab.getDamage();
        List<Pair<CombatEffect, CombatEngine.CombatPhase>> effects = ab.getEffects().stream().filter(pair -> pair.getKey().getProperties()[0].equals(Manager.primaryResource) && Double.parseDouble(pair.getKey().getProperties()[1]) < 0).toList();

        // TODO: Analyse effects and quantify them

        return false;
    }

    public static List<CombatEntity> getKillableEntities(Ability ability, List<CombatEntity> targets) {

        return targets.stream().filter(combatEntity -> canKill(ability, combatEntity)).toList();

    }

}
