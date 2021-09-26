package txengine.systems.event.events;

import txengine.main.Manager;
import txengine.systems.event.Event;
import txengine.ui.color.Colors;

public class AbilityEvent extends Event {

    public AbilityEvent() {
        super();
    }

    @Override
    protected void execute() {
        Manager.player.getAbilityManager().learn(getProperties()[0]);
    }

    @Override
    protected String print() {
        return (Colors.GREEN_BOLD + "You learned a new ability! " + Colors.CYAN_BRIGHT + "[{AB_NAME}]" + Colors.RESET).replace("{AB_NAME}",getProperties()[0]);
    }
}
