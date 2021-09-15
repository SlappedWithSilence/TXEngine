package txengine.systems.event.events;

import txengine.main.Manager;
import txengine.systems.event.Event;

public class MoneyEvent extends Event {

    int quantity;

    @Override
    public void perform() {
        quantity = Integer.parseInt(super.getProperties()[0]);

        Manager.player.setMoney( Manager.player.getMoney() + quantity);

        if ( Manager.player.getMoney() < 0)  Manager.player.setMoney(0);
    }
}
