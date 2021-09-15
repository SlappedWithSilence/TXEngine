package txengine.systems.event.events;

import txengine.ui.color.Colors;
import txengine.main.Manager;
import txengine.systems.event.Event;

public class ItemEvent extends Event {

    int itemID;
    int itemQuantity;

    public ItemEvent () {

    }

    @Override
    public void execute() {
        itemID = Integer.parseInt(super.getProperties()[0]);
        itemQuantity = Integer.parseInt(super.getProperties()[1]);
        Manager.player.getInventory().addItem(itemID, itemQuantity);
    }

    @Override
    public String print() {
        return Colors.GREEN_BOLD +  "You received " + itemQuantity + " " + Manager.itemHashMap.get(itemID).getName() + "(s)!" + Colors.RESET;
    }


}
