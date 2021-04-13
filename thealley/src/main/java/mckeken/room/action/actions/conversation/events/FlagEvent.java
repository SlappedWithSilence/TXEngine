package mckeken.room.action.actions.conversation.events;

import mckeken.io.LogUtils;
import mckeken.room.action.actions.conversation.Event;

public class FlagEvent extends Event {

    String flagName;
    Boolean flagValue;

    public FlagEvent() {

    }

    public FlagEvent(String[] properties) {
        flagName = properties[0];
        flagValue = Boolean.parseBoolean(properties[1]);
    }


    public FlagEvent(String flagName, Boolean flagValue) {
        this.flagName = flagName;
        this.flagValue = flagValue;
    }

    @Override
    public void perform() {
        LogUtils.error("Setting flag " + flagName + " to " + flagValue.toString() + "\n" );
    }

}
