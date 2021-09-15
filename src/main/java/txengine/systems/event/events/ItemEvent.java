package txengine.systems.event.events;

import txengine.main.Manager;
import txengine.systems.event.Event;

public class ItemEvent extends Event {

    int itemID;
    int itemQuantity;

    public ItemEvent () {

    }

    @Override
    public void perform() {
        itemID = Integer.parseInt(super.getProperties()[0]);
        itemQuantity = Integer.parseInt(super.getProperties()[1]);
        Manager.player.getInventory().addItem(itemID, itemQuantity);
    }


}
