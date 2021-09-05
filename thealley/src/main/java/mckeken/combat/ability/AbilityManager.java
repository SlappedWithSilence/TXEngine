package mckeken.combat.ability;

import mckeken.color.Colors;
import mckeken.combat.CombatEntity;
import mckeken.io.LogUtils;

import java.util.AbstractMap;
import java.util.ArrayList;

public class AbilityManager {

    ArrayList<Ability> abilityList;
    CombatEntity owner;

    public AbilityManager() {
        abilityList = new ArrayList<>();
    }

    public AbilityManager(ArrayList<Ability> abilityList) {
        this.abilityList = abilityList;
    }

    public void printAbilities() {
        ArrayList<Boolean> enabled = getEnabledList(owner); // get the list of enabled abilities

        if (enabled.size() != abilityList.size()) { // Check for potential issues with getEnabledList not functioning correctly
            LogUtils.error("Something went wrong when validating Abilities for " + owner.getName() + ". ");
            LogUtils.error("ArrayList sizes don't match! AbilityList (" + abilityList.size() + ") instead of " + "enabledList (" +enabled.size() + ")!");
            return;
        }

        for (int i = 0; i < abilityList.size(); i++) { // Iterate through the ability list

            System.out.print("[" + i + "] ");

            if (enabled.get(i)) {
                System.out.print(Colors.colored(abilityList.get(i).getName(), "WHITE")); // Print name in green if ability costs are satisfied
            } else {
                System.out.print(Colors.colored(abilityList.get(i).getName(), "GRAY"));   // Print name in red if any ability costs aren't satisfied
            }
            System.out.println("\t"+resourceCostsAsString(abilityList.get(i))); // Print individual resource costs of the current item

        }
    }

    // Returns a list of booleans whose index corresponds to the Ability objects in abilityList
    private ArrayList<Boolean> getEnabledList(CombatEntity owner) {
        return new ArrayList<>(abilityList.stream().map( a -> owner.getResourceManager().testResource(a.getResourceCosts())).toList()); // Maps each ability to a boolean representing if its requirements can be met by the caster
    }

    public ArrayList<Ability> getSatisfiedAbilities() {
        return new ArrayList<>(abilityList.stream().filter(this::isSatisfied).toList());
    }

    public boolean isSatisfied(Ability ability) {
        return ability.getResourceCosts().stream().allMatch(pair -> owner.getResourceManager().testResource(pair.getKey(), pair.getValue()));
    }

    // Returns a formatted string of colored resource costs
    public String resourceCostsAsString(Ability a) {
        StringBuilder s = new StringBuilder();

        for (AbstractMap.SimpleEntry<String, Integer> cost : a.getResourceCosts()) {

            String color; // The name of the color to set the cost to

            if (owner.getResourceManager().testResource(cost.getKey(), cost.getValue())) color = "Green";
            else color = "Red";

            s.append("[").append(cost.getKey()).append(": ").append(Colors.colored(cost.getValue().toString(), color)).append("]");
            s.append(" ");
        }

        return s.toString();
    }

    public int getAbilityQuantity() {
        return abilityList.size();
    }

    public ArrayList<Ability> getAbilityList() {
        return abilityList;
    }

    public void setAbilityList(ArrayList<Ability> abilityList) {
        this.abilityList = abilityList;
    }

    public CombatEntity getOwner() {
        return owner;
    }

    public void setOwner(CombatEntity owner) {
        this.owner = owner;
    }
}
