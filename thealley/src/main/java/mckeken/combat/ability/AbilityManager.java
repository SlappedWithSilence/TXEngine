package mckeken.combat.ability;

import java.util.ArrayList;
import java.util.List;

public class AbilityManager {

    List<Ability> abilityList;

    public AbilityManager() {
        abilityList = new ArrayList<Ability>();
    }

    public AbilityManager(List<Ability> abilityList) {
        this.abilityList = abilityList;
    }

    public void printAbilities() {

    }

    public int getAbilityQuantity() {
        return abilityList.size();
    }

}
