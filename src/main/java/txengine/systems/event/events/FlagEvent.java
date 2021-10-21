package txengine.systems.event.events;

import txengine.main.Manager;
import txengine.systems.flag.FlagManager;
import txengine.ui.LogUtils;
import txengine.systems.event.Event;
import txengine.ui.Out;

public class FlagEvent extends Event {

    String flagName;
    Boolean flagValue;

    public FlagEvent() {

    }

    public FlagEvent(String[] properties) {
        this.setProperties(properties);
    }


    public FlagEvent(String flagName, Boolean flagValue) {
        this.flagName = flagName;
        this.flagValue = flagValue;
    }

    @Override
    public void execute() {
        flagName = super.getProperties()[0];
        flagValue = Boolean.parseBoolean(super.getProperties()[1]);
        Out.error("Setting flag " + flagName + " to " + flagValue + "\n" ); // TODO: Remove debug
        FlagManager.setFlag(flagName,flagValue);
    }

    @Override
    public String print() {
        return null;
    }

}
