package mckeken.combat.ability;

import mckeken.combat.CombatEngine;
import mckeken.combat.CombatEntity;
import mckeken.combat.combatEffect.CombatEffect;
import mckeken.item.effect.Effect;

import java.util.AbstractMap;
import java.util.ArrayList;
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
public class Ability {
    CombatEngine.TargetMode targetMode;
    String name;
    String description;
    List<AbstractMap.SimpleEntry<CombatEffect, CombatEngine.CombatPhase>> effects;
    List<AbstractMap.SimpleEntry<String, Integer>> resourceCosts;
    int damage;


    AbstractMap.SimpleEntry<CombatEngine.EntityType, Integer> target;

    public Ability() {
        targetMode = CombatEngine.TargetMode.SINGLE;
        name = "Ability";
        description = "Generic Ability";
        effects = new ArrayList<>();
        damage = 0;
        target = new AbstractMap.SimpleEntry<>(CombatEngine.EntityType.FRIENDLY, 0);
        List<AbstractMap.SimpleEntry<String, Integer>> resourceCosts = new ArrayList<>();
    }

    // Full constructor. Shouldn't often be used, as targets are not determined at instantiation.
    public Ability(CombatEngine.TargetMode targetMode, String name, String description, List<AbstractMap.SimpleEntry<CombatEffect, CombatEngine.CombatPhase>> effects, int damage, AbstractMap.SimpleEntry<CombatEngine.EntityType, Integer> target, List<AbstractMap.SimpleEntry<String, Integer>> resourceCosts) {
        this.targetMode = targetMode;
        this.name = name;
        this.description = description;
        this.effects = effects;
        this.damage = damage;
        this.target = target;
        this.resourceCosts = resourceCosts;
    }

    // Nearly-full constructor. Should be most-often used. Target is not set as it is determined during run-time.
    public Ability(CombatEngine.TargetMode targetMode, String name, String description, List<AbstractMap.SimpleEntry<CombatEffect, CombatEngine.CombatPhase>> effects, int damage, List<AbstractMap.SimpleEntry<String, Integer>> resourceCosts) {
        this.targetMode = targetMode;
        this.name = name;
        this.description = description;
        this.effects = effects;
        this.damage = damage;
        this.resourceCosts = resourceCosts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AbstractMap.SimpleEntry<CombatEffect, CombatEngine.CombatPhase>> getEffects() {
        return effects;
    }

    public void setEffects(List<AbstractMap.SimpleEntry<CombatEffect, CombatEngine.CombatPhase>> effects) {
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

    public AbstractMap.SimpleEntry<CombatEngine.EntityType, Integer> getTarget() {
        return target;
    }

    public void setTarget(AbstractMap.SimpleEntry<CombatEngine.EntityType, Integer> target) {
        this.target = target;
    }

    public List<AbstractMap.SimpleEntry<String, Integer>> getResourceCosts() {
        return resourceCosts;
    }

    public void setResourceCosts(List<AbstractMap.SimpleEntry<String, Integer>> resourceCosts) {
        this.resourceCosts = resourceCosts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


