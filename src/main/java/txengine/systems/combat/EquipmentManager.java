package txengine.systems.combat;

import com.rits.cloning.Cloner;
import txengine.integration.Requirement;
import txengine.io.LogUtils;
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

        if (!(i instanceof Equipment)) {
            LogUtils.error("Thats not an equipment\n");
            return false;
        }

        if (!Requirement.allMet(((Equipment) i).getEquipRequirements())) {
            LogUtils.error("Requirements not met!\n");
            return false;
        }

        Equipment.EquipmentType type = ((Equipment) i).getType();

        if (equipmentMap.get(type) != null) unequip(type);

        setSlot(type, id);
        Manager.player.getInventory().consumeQuantity(id, 1);

        return true;
    }

    public void unequip(Equipment.EquipmentType slot) {
        Manager.player.getInventory().addItem(equipmentMap.get(slot));
        setSlot(slot, -1);
    }

    // Returns the sum of all damage buffs
    public int getDamageBuff() {
        return equipmentMap.values().stream().reduce(0, (subtotal, e) -> subtotal + ((Equipment) Manager.itemHashMap.get(e)).getDamageBuff() , Integer::sum);
    }

    // Returns the sum of all damage resistance
    public int getDamageResistance() {
        return equipmentMap.values().stream().reduce(0, (subtotal, e) -> subtotal + ((Equipment) Manager.itemHashMap.get(e)).getDamageResistance() , Integer::sum);
    }

    /* Helper Methods */

    private void initialize() {
        equipmentMap = new HashMap<>();
        Arrays.stream(Equipment.EquipmentType.values()).forEach(equipmentType -> equipmentMap.put(equipmentType, null));
    }

    /* Accessor Methods */
    public void setSlot(Equipment.EquipmentType slot, int equipmentID) {
        if (((Equipment) Manager.itemHashMap.get(equipmentID)).getType() != slot) {
            LogUtils.error("Cannot assign a " + ((Equipment) Manager.itemHashMap.get(equipmentID)).getType().toString() + " to the " + slot.toString() + " slot!");
            return;
        }

        equipmentMap.put(slot, equipmentID);
    }

    // Returns an item instance of the equipment in the given slot
    public Equipment getSlot(Equipment.EquipmentType type) {
        return (Equipment) Manager.itemHashMap.get( equipmentMap.get(type) );
    }

    public int getSlotID(Equipment.EquipmentType type) {
        return equipmentMap.get(type);
    }

}
