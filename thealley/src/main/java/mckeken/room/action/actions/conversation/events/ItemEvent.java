package mckeken.room.action.actions.conversation.events;

import mckeken.main.Manager;
import mckeken.room.action.actions.conversation.Event;

public class ItemEvent extends Event {

    int itemID;
    int itemQuantity;

    public ItemEvent () {
        itemID = 0;
        itemQuantity = 1;
    }

    @Override
    public void perform() {
        Manager.player.getInventory().addItem(itemID, itemQuantity);
    }


}
