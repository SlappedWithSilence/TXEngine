package txengine.systems.ability;

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
    public static Ability build(CombatEngine.TargetMode targetMode, String name, String description, String useText, List<AbstractMap.SimpleEntry<CombatEffect, CombatEngine.CombatPhase>> effects, int damage, List<AbstractMap.SimpleEntry<String, Integer>> resourceCosts) {
        return new Ability(targetMode, name, description, useText, effects, damage, resourceCosts);
    }


}
