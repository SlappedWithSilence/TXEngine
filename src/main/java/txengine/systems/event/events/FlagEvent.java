package txengine.systems.event.events;

import txengine.ui.LogUtils;
import txengine.systems.event.Event;

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
        LogUtils.error("Setting flag " + flagName + " to " + flagValue + "\n" ); // TODO: Remove debug
    }

    @Override
    public String print() {
        return null;
    }

}
