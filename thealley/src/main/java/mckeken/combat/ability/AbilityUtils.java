package mckeken.combat.ability;

import mckeken.main.Manager;

import java.util.ArrayList;
import java.util.stream.Stream;

public class AbilityUtils {

    // TODO: Test
    public static ArrayList<Ability> getHealingAbilities(AbilityManager am) {
        ArrayList<Ability> abilities = new ArrayList<>();

       Stream<Ability> abilityStream = am.getAbilityList().stream().filter(AbilityUtils::isHealingAbility);

        return new ArrayList<>(abilityStream.toList());
    }

    public static boolean isHealingAbility(Ability a) {
        return a.getEffects().stream().anyMatch(effectEntry ->  effectEntry.getKey().getProperties()[0].equals(Manager.primaryResource) &&
                                                                Integer.parseInt(effectEntry.getKey().getProperties()[1]) > 0);
    }

}
