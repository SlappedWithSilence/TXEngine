package txengine.systems.ability;

import txengine.structures.Pair;
import txengine.systems.combat.CombatEngine;
import txengine.systems.combat.combatEffect.CombatEffect;
import txengine.systems.integration.Requirement;

import java.util.AbstractMap;
import java.util.List;

public class AbilityFactory {
    final private static String ABILITY_PACKAGE = "mckeken.systems.combat.ability."; // The fully-qualified package name where Effects are found

    public AbilityFactory() {

    }

    // Returns an instance of an Effect that has the properties passed in the parameter
    public static Ability build(CombatEngine.TargetMode targetMode,
                                String name,
                                String description,
                                String useText,
                                List<Pair<CombatEffect, CombatEngine.CombatPhase>> effects,
                                int damage,
                                List<Pair<String, Integer>> resourceCosts,
                                List<Requirement> requirements) {
        return new Ability(targetMode, name, description, useText, effects, damage, resourceCosts, requirements);
    }


}
