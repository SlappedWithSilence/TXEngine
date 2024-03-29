package txengine.systems.ability;

import txengine.structures.Pair;
import txengine.systems.integration.Requirement;
import txengine.ui.Out;
import txengine.ui.color.Colors;
import txengine.systems.combat.CombatEntity;
import txengine.ui.LogUtils;
import txengine.main.Manager;

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

    public AbilityManager(AbilityManager abilityManager) {
        abilityList = new ArrayList<>(abilityManager.abilityList);
        owner = null;
    }

    public void printAbilities() {
        ArrayList<Boolean> enabled = getEnabledList(owner); // get the list of enabled abilities

        if (enabled.size() != abilityList.size()) { // Check for potential issues with getEnabledList not functioning correctly
            Out.error("Something went wrong when validating Abilities for " + owner.getName() + ". ");
            Out.error("ArrayList sizes don't match! AbilityList (" + abilityList.size() + ") instead of " + "enabledList (" +enabled.size() + ")!");
            return;
        }

        for (int i = 0; i < abilityList.size(); i++) { // Iterate through the ability list

            System.out.print("[" + i + "] ");

            if (enabled.get(i)) {
                System.out.print(Colors.colored(abilityList.get(i).getName(), "WHITE")); // Print name in green if ability costs are satisfied
            } else {
                System.out.print(Colors.colored(abilityList.get(i).getName(), "GRAY"));   // Print name in red if any ability costs aren't satisfied
            }
            System.out.print("\t\t"+abilityList.get(i).getDamage() + " damage");
            System.out.println("\t"+resourceCostsAsString(abilityList.get(i))+"\t" + abilityList.get(i).targetMode.toString()); // Print individual resource costs of the current item

        }
    }

    // Teach the combat entity a new Ability by name reference
    public void learn(String abilityName) {
        if (abilityList.stream().noneMatch(ability -> ability.getName().equals(abilityName))) {
            abilityList.add(Manager.abilityHashMap.get(abilityName));
        }
    }

    // "Unlearn" any abilities with a matching name as 'abilityName'.
    public void unlearn(String abilityName) {
        abilityList.removeIf(a -> a.getName().equals(abilityName));
    }

    // Teach the combat entity a new Ability by direct reference
    public void learn(Ability ability) {
        abilityList.add(ability);
    }

    // Returns a list of booleans whose index corresponds to the Ability objects in abilityList
    private ArrayList<Boolean> getEnabledList(CombatEntity owner) {
        return new ArrayList<>(abilityList.stream().map( a -> owner.getResourceManager().testResource(a.getResourceCosts())).toList()); // Maps each ability to a boolean representing if its requirements can be met by the caster
    }

    public ArrayList<Ability> getSatisfiedAbilities() {
        return new ArrayList<>(abilityList.stream().filter(this::isSatisfied).toList());
    }

    public boolean isSatisfied(Ability ability) {
        return ability.getResourceCosts().stream().allMatch(pair -> owner.getResourceManager().testResource(pair.getKey(), pair.getValue()))
                &&
                Requirement.allMet(ability.getRequirements());
    }

    // Pay for the resource costs in a given ability
    public void payCosts(Ability ability) {
        ability.getResourceCosts().forEach(pair -> owner.getResourceManager().decrementResource(pair.getKey(), pair.getValue()));
    }

    // Returns a formatted string of colored resource costs
    public String resourceCostsAsString(Ability a) {
        StringBuilder s = new StringBuilder();

        for (Pair<String, Integer> cost : a.getResourceCosts()) {

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
