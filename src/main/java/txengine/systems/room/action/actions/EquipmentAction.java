package txengine.systems.room.action.actions;

import txengine.main.Manager;
import txengine.systems.item.Equipment;
import txengine.systems.room.action.Action;
import txengine.ui.LogUtils;
import txengine.ui.component.Components;

import java.util.ArrayList;
import java.util.List;

public class EquipmentAction extends Action {

    @Override
    public int perform() {
        int totalDef = Manager.player.getEquipmentManager().getDamageResistance();
        int totalAtk = Manager.player.getEquipmentManager().getDamageBuff();

        Components.header("Equipment");
        Components.subHeader(List.of("Attack: " + totalAtk, "Defense: " + totalDef));

       List<Components.Tabable> data = new ArrayList<>();

       for (Equipment.EquipmentType type : Equipment.EquipmentType.values()) {
           Components.Tabable tabable = Manager.player.getEquipmentManager().getSlot(type);
           if (tabable == null) data.add(() -> {
               List<String> ls = new ArrayList<>();

               ls.add("Type: " + type.toString());
               ls.add(" [Empty]");

               return  ls;
           });
           else data.add(tabable);
       }
        int size = data.size();
        List<Components.Tabable> first = new ArrayList<>(data.subList(0, (size) / 2));
        List<Components.Tabable> second = new ArrayList<>(data.subList((size) / 2, size));

       //Components.verticalTabList(data);
        Components.parallelVerticalTabList(first, second, null, null, 0);

        System.out.println("Which slot do you want to interact with?");
        Equipment.EquipmentType type = Equipment.EquipmentType.valueOf(LogUtils.getEnumValue(Equipment.EquipmentType.class));
        if (Manager.player.getEquipmentManager().getSlot(type) == null) System.out.println("There is nothing in that slot.");
        else {
            Components.header("Equipment");
            String[] options = new String[] {"Inspect", "Unequip"};
            System.out.println("What do you want to do?");
            Components.numberedList(options);
            int choice = LogUtils.getNumber(-1, options.length-1);
            switch(choice){
                case -1 -> { return unhideIndex; }
                case 0  -> {
                    System.out.println(Manager.player.getEquipmentManager().getSlot(type).inspect());
                }
                case 1 -> {
                    Manager.player.getEquipmentManager().unequip(type);
                }
            }
        }

        return unhideIndex;
    }
}
