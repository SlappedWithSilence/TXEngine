package txengine.systems.ability;

import txengine.systems.combat.CombatEngine;
import txengine.systems.combat.CombatEntity;
import txengine.systems.combat.combatEffect.CombatEffect;
import txengine.systems.integration.Requirement;

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
    String useText; // The text that gets printed when the ability is used.
    List<AbstractMap.SimpleEntry<CombatEffect, CombatEngine.CombatPhase>> effects;
    List<AbstractMap.SimpleEntry<String, Integer>> resourceCosts;
    List<Requirement> requirements;
    int damage;

    CombatEntity target;

    enum SATISFIED {
        TRUE,
        REQUIREMENT_ERROR,
        RESOURCE_ERROR
    }

    public Ability() {
        targetMode = CombatEngine.TargetMode.SINGLE;
        name = "Ability";
        description = "Generic Ability";
        useText = " used a generic ability!";
        effects = new ArrayList<>();
        damage = 0;
        target = null;
        resourceCosts = new ArrayList<>();
        requirements = new ArrayList<>();
    }

    // Full constructor. Shouldn't often be used, as targets are not determined at instantiation.
    public Ability(CombatEngine.TargetMode targetMode, String name, String description, String useText, List<AbstractMap.SimpleEntry<CombatEffect, CombatEngine.CombatPhase>> effects, int damage, CombatEntity target, List<AbstractMap.SimpleEntry<String, Integer>> resourceCosts, List<Requirement> requirements) {
        this.targetMode = targetMode;
        this.name = name;
        this.description = description;
        this.useText = useText;
        this.effects = effects;
        this.damage = damage;
        this.target = target;
        this.resourceCosts = resourceCosts;
        this.requirements = requirements;
    }

    // Nearly-full constructor. Should be most-often used. Target is not set as it is determined during run-time.
    public Ability(CombatEngine.TargetMode targetMode, String name, String description, String useText, List<AbstractMap.SimpleEntry<CombatEffect, CombatEngine.CombatPhase>> effects, int damage, List<AbstractMap.SimpleEntry<String, Integer>> resourceCosts, List<Requirement> requirements) {
        this.targetMode = targetMode;
        this.name = name;
        this.description = description;
        this.useText = useText;
        this.effects = effects;
        this.damage = damage;
        this.resourceCosts = resourceCosts;
        this.requirements = requirements;
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

    public CombatEntity getTarget() {
        return target;
    }

    public void setTarget(CombatEntity target) {
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

    public String getUseText() {
        return useText;
    }

    public void setUseText(String useText) {
        this.useText = useText;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
    }
}


