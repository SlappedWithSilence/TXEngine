package txengine.systems.room.action.actions;

import txengine.main.Manager;
import txengine.systems.item.Equipment;
import txengine.systems.item.Item;
import txengine.systems.room.action.Action;
import txengine.ui.LogUtils;
import txengine.ui.component.Components;

import java.util.ArrayList;
import java.util.List;

public class EquipmentAction extends Action {

    public EquipmentAction() {
        super();
        menuName = "Check your equipment";
    }

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

        Components.header("Equipment");
        String[] options = new String[] {"Switch", "Inspect", "Unequip"};
        System.out.println("What do you want to do?");
        Components.numberedList(options);
        int choice = LogUtils.getNumber(-1, options.length-1);
        switch(choice){
            case -1 -> { return unhideIndex; }
            case 0 -> { // Choice : Switch
                // Generate a list of equipment that work in the selected slot, then convert them to Tabable objects
                List<Components.Tabable> slotOptions = Manager.player.getInventory().
                                                                      getItemInstances().stream().
                                                                      filter(item -> item instanceof Equipment).
                                                                      filter(item -> ((Equipment)item).getType() == type).
                                                                      map(item -> (Components.Tabable) item).toList();

                if (slotOptions.size() == 0) return unhideIndex; // If no items matching the requirements were found, exit
                Components.verticalTabList(slotOptions);         // Print out the list of items found
                System.out.println("Which item do you want to equip? (-1 to exit)");
                int switchChoice = LogUtils.getNumber(-1,slotOptions.size()-1);
                Manager.player.getEquipmentManager().equip(((Item) slotOptions.get(switchChoice)).getId() ); // Equip the item the user chooses
            }
            case 1  -> { // Choice : Inspect
                if (Manager.player.getEquipmentManager().getSlot(type) == null) System.out.println("There is nothing in that slot.");
                else System.out.println(Manager.player.getEquipmentManager().getSlot(type).inspect());
            }
            case 2 -> { // Choice Unequip
                if (Manager.player.getEquipmentManager().getSlot(type) == null) System.out.println("There is nothing in that slot.");
                else Manager.player.getEquipmentManager().unequip(type);
            }
        }


        return unhideIndex;
    }
}
