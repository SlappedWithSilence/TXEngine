package mckeken.combat.ability;

import mckeken.combat.CombatEngine;
import mckeken.combat.CombatEntity;
import mckeken.combat.combatEffect.CombatEffect;
import mckeken.item.effect.Effect;

import java.util.List;

/*
 An ability is a "move" an entity can use during combat. An Ability may do the following:
 - Deal Damage
 - Remove Damage
 - Generate an Effect
 - Remove an Effect
 - Consume a resource
 - Restore a resource

 An ability has a discrete target mode. Of these modes, the following are possible:
 - Single Target
 - All entities
 - All friendly entities
 - All enemy entities

 An ability that targets multiple entities assigns its effects to all targeted entities.
 */
public abstract class Ability {
    CombatEngine.TargetMode targetMode;
    String name;
    List<CombatEffect> effects;
    int damage;

    public abstract boolean hasRequirements(CombatEntity caster); // Returns true if casting requirements are met, false otherwise

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CombatEffect> getEffects() {
        return effects;
    }

    public void setEffects(List<CombatEffect> effects) {
        this.effects = effects;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public CombatEngine.TargetMode getTargetMode() {
        return targetMode;
    }

    public void setTargetMode(CombatEngine.TargetMode targetMode) {
        this.targetMode = targetMode;
    }
}


