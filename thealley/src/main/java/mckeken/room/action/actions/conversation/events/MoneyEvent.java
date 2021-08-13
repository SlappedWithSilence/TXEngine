package mckeken.room.action.actions.conversation.events;

import mckeken.player.Player;
import mckeken.room.action.actions.conversation.Event;

public class MoneyEvent extends Event {

    int quantity;

    @Override
    public void perform() {
        quantity = Integer.parseInt(super.getProperties()[0]);

        Player.setMoney(Player.getMoney() + quantity);

        if (Player.getMoney() < 0) Player.setMoney(0);
    }
}
