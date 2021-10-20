package txengine.systems.item;

import com.rits.cloning.Cloner;
import txengine.structures.Pair;
import txengine.systems.integration.Requirement;
import txengine.systems.combat.CombatEngine;
import txengine.systems.combat.combatEffect.CombatEffect;
import txengine.ui.component.Components;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Equipment extends Item implements Components.Tabable {

    public enum EquipmentType {
        WEAPON,
        // Armor
        HEAD,
        CHEST,
        HANDS,
        LEGS,
        FEET,
        // Jewelry
        RING,
        NECKLACE
    }

    /* Member Variables */
    private List<Pair<CombatEffect, CombatEngine.CombatPhase>> preCombatEffects;
    private EquipmentType type;
    private List<Pair<String, Float>> tagResistances;
    private List<Requirement> equipRequirements;
    private List<String> abilityNames;
    private int damageBuff;
    private int damageResistance;

    /* Constructors */
    public Equipment() {
        super();
        preCombatEffects = new ArrayList<>();
        tagResistances = new ArrayList<>();
        abilityNames = new ArrayList<>();
    }

    // Value constructor, no effects or resistances, or requirements
    public Equipment(String name, String description, int id, int value, int maxStacks, EquipmentType type) {
        super(name, description, id, value, maxStacks);
        preCombatEffects = new ArrayList<>();
        this.type = type;
        tagResistances = new ArrayList<>();
        equipRequirements = new ArrayList<>();
        abilityNames = new ArrayList<>();
    }

    // Value constructor, effects
    public Equipment(String name, String description, int id, int value, int maxStacks, List<Pair<CombatEffect, CombatEngine.CombatPhase>> preCombatEffects,  List<Pair<String, Float>> tagResistances, List<Requirement> equipRequirements, List<String> abilityNames, EquipmentType type, int damage, int defense) {
        super(name, description, id, value, maxStacks);
        setPreCombatEffects(preCombatEffects);
        this.tagResistances = tagResistances;
        this.type = type;
        this.damageBuff = damage;
        this.damageResistance = defense;
        this.equipRequirements = equipRequirements;
        this.abilityNames = abilityNames;
    }

    public Equipment(Equipment equipment) {
        super(equipment);

        Cloner cloner = new Cloner();

        this.preCombatEffects = new ArrayList<>(equipment.preCombatEffects);
        this.type = equipment.type;
        this.damageResistance = equipment.damageResistance;
        this.damageBuff = equipment.damageBuff;
        this.tagResistances = new ArrayList<>(equipment.tagResistances);
        this.equipRequirements = new ArrayList<>(equipment.equipRequirements);
        this.abilityNames = new ArrayList<>(equipment.abilityNames);
    }
    /* Member Functions*/
    @Override
    public String inspect() {

        return getDescription() + "\n" +
                "[Damage: +" + damageBuff + "]" + " [Defense: +" + damageResistance + "]";
    }

    @Override
    public Collection<String> getTabData() {
        List<String> data = new ArrayList<>();

        data.add(getName());
        data.add("Type: " + type.toString());
        data.add(getDescription());
        data.add("Attack: " + damageBuff);
        data.add("Defense: " + damageResistance);
        data.add("Abilities: " + abilityNames.stream().reduce("", (partialString, name) -> partialString + name));

        return data;
    }

        /* Accessor Methods */

    public List<String> getAbilityNames() {
        return abilityNames;
    }

    public void setAbilityNames(List<String> abilityNames) {
        this.abilityNames = abilityNames;
    }

    public final List<Pair<CombatEffect, CombatEngine.CombatPhase>> getPreCombatEffects() {
        return preCombatEffects;
    }

    public void setPreCombatEffects(List<Pair<CombatEffect, CombatEngine.CombatPhase>> preCombatEffects) {
        this.preCombatEffects = new ArrayList<>(preCombatEffects);
    }

    public EquipmentType getType() {
        return type;
    }

    public void setType(EquipmentType type) {
        this.type = type;
    }

    public List<Pair<String, Float>> getTagResistances() {
        return tagResistances;
    }

    public void setTagResistances(List<Pair<String, Float>> tagResistances) {
        this.tagResistances = tagResistances;
    }

    public int getDamageBuff() {
        return damageBuff;
    }

    public void setDamageBuff(int damageBuff) {
        this.damageBuff = damageBuff;
    }

    public int getDamageResistance() {
        return damageResistance;
    }

    public void setDamageResistance(int damageResistance) {
        this.damageResistance = damageResistance;
    }

    public List<Requirement> getEquipRequirements() {
        return equipRequirements;
    }

    public void setEquipRequirements(List<Requirement> equipRequirements) {
        this.equipRequirements = equipRequirements;
    }
}
