package txengine.systems.event.events;

import txengine.color.Colors;
import txengine.main.Manager;
import txengine.systems.event.Event;

public class MoneyEvent extends Event {

    int quantity;

    @Override
    public void execute() {
        quantity = Integer.parseInt(super.getProperties()[0]);
        Manager.player.setMoney( Manager.player.getMoney() + quantity);
        if ( Manager.player.getMoney() < 0)  Manager.player.setMoney(0);
    }

    @Override
    public String print() {
        if (quantity > 0) return Colors.RED_BOLD + "You lost " + quantity + " " + Manager.primaryCurrency + Colors.RESET;
        else return Colors.GREEN_BOLD + "You gained " + quantity + " " + Manager.primaryCurrency + Colors.RESET;
    }
}
