package txengine.systems.combat.combatEffect.combatEffects;

import txengine.main.Manager;
import txengine.systems.combat.CombatEntity;
import txengine.systems.combat.combatEffect.CombatEffect;
import txengine.systems.item.Equipment;
import txengine.systems.item.Item;

public class EquipEffect extends CombatEffect {

    public EquipEffect() {
        super.properties = new String[]{"1"};
        setDuration(1);
    }

    public EquipEffect(int id) {
        super(new String[]{""+id});
        setDuration(1);
    }

    public EquipEffect(String[] properties) {
        super(properties);
        setDuration(1);
    }

    public EquipEffect(String[] properties, int duration) {
        super(properties, duration);
    }

    @Override
    public void perform(CombatEntity entity) {
        if(super.getDuration() == 1){
            String slotName = super.properties[1];
            
            Equipment.EquipmentType slot = Equipment.EquipmentType.valueOf(slotName);
                                               
            entity.getEquipmentManager.unequip(slot);
            entity.getEquipmentManager().equip(Integer.parseInt(super.properties[0]));
            /*
            int id = Integer.parseInt(super.properties[0]);
            if( !entity.getEquipmentManager().equip(id) ){ //Equip item at id
                Item i = Manager.itemHashMap.get(id);
                if( i instanceof Equipment ){ //If equip fails, unequip item in slot
                    Equipment.EquipmentType type = ((Equipment) i).getType();
                    entity.getEquipmentManager().unequip(type);
                }
            }
            */
        }
    }
}
