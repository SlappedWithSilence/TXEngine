package txengine.systems.combat;

import com.rits.cloning.Cloner;
import txengine.structures.Pair;
import txengine.systems.integration.Requirement;
import txengine.ui.LogUtils;
import txengine.main.Manager;
import txengine.systems.combat.combatEffect.CombatEffect;
import txengine.systems.item.Equipment;
import txengine.systems.item.Item;
import txengine.ui.Out;

import java.util.*;

public class EquipmentManager {

    /* Member Variables */

    private HashMap<Equipment.EquipmentType, Integer> equipmentMap;
    private HashMap<String, Float> tagResistanceMap;

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

    public List<Pair<CombatEffect, CombatEngine.CombatPhase>> getAllEffects() {
        List<Pair<CombatEffect, CombatEngine.CombatPhase>> effects = new ArrayList<>();

        equipmentMap.values().stream().filter(Objects::nonNull).filter(i -> i > 0).forEach(equipmentID -> effects.addAll(new Equipment((Equipment) Manager.itemManager.get_instance(equipmentID)).getPreCombatEffects()));

        return effects;
    }

    public boolean equip(int id) {
        Item i = Manager.itemManager.get_instance(id);

        if (i == null) {
            Out.error(id + " is not a valid Item ID!","EquipmentManager");
            return false;
        }
        // Verify if the equipment can actually be equipped.
        if (!(i instanceof Equipment)) {
            Out.error("Thats not an equipment\n");
            return false;
        }

        if (!Requirement.allMet(((Equipment) i).getEquipRequirements())) {
            Out.error("Requirements not met!\n");
            return false;
        }

        // Equip the equipment to the right slot
        Equipment.EquipmentType type = ((Equipment) i).getType();
        if (equipmentMap.get(type) != null) unequip(type); // Unequip the item already in that slot
        setSlot(type, id);                                 // Set the item in that slot to the new item
        Manager.player.getInventory().consumeQuantity(id, 1); // Remove the item from the player's inventory

        // Register the abilities to the player
        ((Equipment) Manager.itemManager.get_instance(id)).getAbilityNames().forEach(abName -> Manager.player.abilityManager.learn(abName));

        return true;
    }

    public void unequip(Equipment.EquipmentType slot) {
        if (equipmentMap.get(slot) != null && equipmentMap.get(slot) > 0) {
            Manager.player.getInventory().addItem(equipmentMap.get(slot));
            ((Equipment) Manager.itemManager.get_instance(equipmentMap.get(slot))).getAbilityNames().forEach(abName -> Manager.player.abilityManager.unlearn(abName));
        }
        setSlot(slot, -1);
    }

    // Returns the sum of all damage buffs
    public int getDamageBuff() {
        return equipmentMap.values().stream().filter(i -> (i != null && i != -1)).reduce(0, (subtotal, e) -> subtotal + ((Equipment) Manager.itemManager.get_instance(e)).getDamageBuff() , Integer::sum);
    }

    // Returns the sum of all damage resistance
    public int getDamageResistance() {
        return equipmentMap.values().stream().filter( i -> (i != null && i != -1)).reduce(0, (subtotal, e) -> subtotal + ((Equipment) Manager.itemManager.get_instance(e)).getDamageResistance() , Integer::sum);
    }

    /* Helper Methods */

    private void initialize() {
        equipmentMap = new HashMap<>();
        Arrays.stream(Equipment.EquipmentType.values()).forEach(equipmentType -> equipmentMap.put(equipmentType, -1));
    }

    // Calculate and store the tag resistances for each equipment
    private void calculateTagResistances() {
        tagResistanceMap.clear(); // Clear existing values
        for (int equipmentID : equipmentMap.values()) { // For each equipment
            Equipment eq = (Equipment) Manager.itemManager.get_instance(equipmentID); // Get item instance
            List<Pair<String, Float>> tagRes = eq.getTagResistances(); // Get resistances from that item
            if (tagRes == null || tagRes.size() == 0) continue; // Skip if there are no resistances

            for (Pair<String, Float> data : tagRes) { // For each tag resistance
                // If the tag exists, add it to the current sum of resistances
                if (tagResistanceMap.containsKey(data.getKey())) tagResistanceMap.put(data.getKey(), tagResistanceMap.get(data.getKey()) + data.getValue());
                // If it doesn't exist already, set the resistance of that tag to the current resistance
                else tagResistanceMap.put(data.getKey(), data.getValue());
            }
        }
    }

    /* Accessor Methods */
    public Float resistsBy(String tag) {
        return tagResistanceMap.getOrDefault(tag, 0f);
    }

    public Float totalResistance(String[] tags) {
        if (tags == null || tags.length == 0) return 0f;

        Float totalResistance = 0f;

        for (String tag : tags) {
            if (totalResistance == 0f) totalResistance = resistsBy(tag);
            else totalResistance = totalResistance * (1 + resistsBy(tag));
        }

        return totalResistance;
    }

    public void setSlot(Equipment.EquipmentType slot, int equipmentID) {
        if (equipmentID  != -1 && ((Equipment) Manager.itemManager.get_instance(equipmentID)).getType() != slot) {
            Out.error("Cannot assign a " + ((Equipment) Manager.itemManager.get_instance(equipmentID)).getType().toString() + " to the " + slot.toString() + " slot!");
            return;
        }

        equipmentMap.put(slot, equipmentID);
    }

    // Returns an item instance of the equipment in the given slot
    public Equipment getSlot(Equipment.EquipmentType type) {
        if (equipmentMap.get(type) == null || equipmentMap.get(type) < 0) return null;
        return (Equipment) Manager.itemManager.get_instance( equipmentMap.get(type) );
    }

    public int getSlotID(Equipment.EquipmentType type) {
        return equipmentMap.get(type);
    }

    // Return a list of item IDs associated with equipment in each slot.
    public List<Integer> getIDs() {
        return equipmentMap.values().stream().filter(id -> id != null && id != -1).toList();
    }

    public HashMap<String, Float> getTagResistanceMap() {
        return tagResistanceMap;
    }

    public void setTagResistanceMap(HashMap<String, Float> tagResistanceMap) {
        this.tagResistanceMap = tagResistanceMap;
    }
}
