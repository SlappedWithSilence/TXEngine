package txengine.systems.combat;

import com.rits.cloning.Cloner;
import txengine.systems.integration.Requirement;
import txengine.ui.LogUtils;
import txengine.main.Manager;
import txengine.systems.combat.combatEffect.CombatEffect;
import txengine.systems.item.Equipment;
import txengine.systems.item.Item;

import java.util.*;

public class EquipmentManager {

    /* Member Variables */

    private HashMap<Equipment.EquipmentType, Integer> equipmentMap;

    /* Constructors */
    public EquipmentManager() {
        initialize();
    }

    public EquipmentManager(HashMap<Equipment.EquipmentType, Integer> equipmentMap) {
        initialize();
        Cloner clone = new Cloner();
        this.equipmentMap = clone.deepClone(equipmentMap);
    }

    public EquipmentManager(EquipmentManager equipmentManager) {
        initialize();
        Cloner clone = new Cloner();

        this.equipmentMap = clone.deepClone(equipmentManager.equipmentMap);
    }

    /* Member Methods */

    public List<AbstractMap.SimpleEntry<CombatEffect, CombatEngine.CombatPhase>> getAllEffects() {
        List<AbstractMap.SimpleEntry<CombatEffect, CombatEngine.CombatPhase>> effects = new ArrayList<>();

        equipmentMap.values().stream().filter(Objects::nonNull).forEach(equipmentID -> effects.addAll(new Equipment((Equipment) Manager.itemHashMap.get(equipmentID)).getPreCombatEffects()));

        return effects;
    }

    public boolean equip(int id) {
        Item i = Manager.itemHashMap.get(id);

        // Verify if the equipment can actually be equipped.
        if (!(i instanceof Equipment)) {
            LogUtils.error("Thats not an equipment\n");
            return false;
        }

        if (!Requirement.allMet(((Equipment) i).getEquipRequirements())) {
            LogUtils.error("Requirements not met!\n");
            return false;
        }

        // Equip the equipment to the right slot
        Equipment.EquipmentType type = ((Equipment) i).getType();
        if (equipmentMap.get(type) != null) unequip(type); // Unequip the item already in that slot
        setSlot(type, id);                                 // Set the item in that slot to the new item
        Manager.player.getInventory().consumeQuantity(id, 1); // Remove the item from the player's inventory

        // Register the abilities to the player
        ((Equipment) Manager.itemHashMap.get(id)).getAbilityNames().forEach(abName -> Manager.player.abilityManager.learn(abName));

        return true;
    }

    public void unequip(Equipment.EquipmentType slot) {
        if (equipmentMap.get(slot) != null && equipmentMap.get(slot) > 0) {
            Manager.player.getInventory().addItem(equipmentMap.get(slot));
            ((Equipment) Manager.itemHashMap.get(equipmentMap.get(slot))).getAbilityNames().forEach(abName -> Manager.player.abilityManager.unlearn(abName));
        }
        setSlot(slot, -1);
    }

    // Returns the sum of all damage buffs
    public int getDamageBuff() {
        return equipmentMap.values().stream().filter(Objects::nonNull).reduce(0, (subtotal, e) -> subtotal + ((Equipment) Manager.itemHashMap.get(e)).getDamageBuff() , Integer::sum);
    }

    // Returns the sum of all damage resistance
    public int getDamageResistance() {
        return equipmentMap.values().stream().filter(Objects::nonNull).filter(integer -> integer != -1).reduce(0, (subtotal, e) -> subtotal + ((Equipment) Manager.itemHashMap.get(e)).getDamageResistance() , Integer::sum);
    }

    /* Helper Methods */

    private void initialize() {
        equipmentMap = new HashMap<>();
        Arrays.stream(Equipment.EquipmentType.values()).forEach(equipmentType -> equipmentMap.put(equipmentType, -1));
    }

    /* Accessor Methods */
    public void setSlot(Equipment.EquipmentType slot, int equipmentID) {
        if (equipmentID  != -1 && ((Equipment) Manager.itemHashMap.get(equipmentID)).getType() != slot) {
            LogUtils.error("Cannot assign a " + ((Equipment) Manager.itemHashMap.get(equipmentID)).getType().toString() + " to the " + slot.toString() + " slot!");
            return;
        }

        equipmentMap.put(slot, equipmentID);
    }

    // Returns an item instance of the equipment in the given slot
    public Equipment getSlot(Equipment.EquipmentType type) {
        if (equipmentMap.get(type) == null || equipmentMap.get(type) < 0) return null;
        return (Equipment) Manager.itemHashMap.get( equipmentMap.get(type) );
    }

    public int getSlotID(Equipment.EquipmentType type) {
        return equipmentMap.get(type);
    }

}
