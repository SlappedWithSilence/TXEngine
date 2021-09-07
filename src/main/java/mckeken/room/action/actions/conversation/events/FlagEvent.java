package mckeken.room.action.actions.conversation.events;

import mckeken.io.LogUtils;
import mckeken.room.action.actions.conversation.Event;

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
    public void perform() {
        flagName = super.getProperties()[0];
        flagValue = Boolean.parseBoolean(super.getProperties()[1]);
        LogUtils.error("Setting flag " + flagName + " to " + flagValue.toString() + "\n" );
    }

}
