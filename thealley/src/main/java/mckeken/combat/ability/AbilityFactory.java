package mckeken.combat.ability;

import mckeken.combat.CombatEngine;
import mckeken.combat.CombatEntity;
import mckeken.combat.combatEffect.CombatEffect;
import mckeken.io.LogUtils;
import mckeken.item.effect.Effect;

import java.util.AbstractMap;
import java.util.List;

public class AbilityFactory {
    final private static String ABILITY_PACKAGE = "mckeken.combat.ability."; // The fully-qualified package name where Effects are found

    public AbilityFactory() {

    }

    // Returns an instance of an Effect that has the properties passed in the parameter
    public static Ability build(CombatEngine.TargetMode targetMode, String name, String description, List<AbstractMap.SimpleEntry<CombatEffect, CombatEngine.CombatPhase>> effects, int damage, List<AbstractMap.SimpleEntry<String, Integer>> resourceCosts) {
        return new Ability(targetMode, name, description, effects, damage, resourceCosts);
    }


}
