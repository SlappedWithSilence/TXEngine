package mckeken.room.action.actions.conversation;

import mckeken.io.LogUtils;
import mckeken.room.action.Action;

public class EventFactory {

    final private static String EVENTS_PACKAGE = "mckeken.room.action.actions.conversation.events."; // The fully-qualified package name where Effects are found

    public EventFactory() {

    }

    // Returns an instance of an Effect that has the properties passed in the parameter
    public static Event build(String className, String[] properties) {
        try {
            Class clasz = Class.forName(EVENTS_PACKAGE + className); // Looks up the class name passed in
            Event e = (Event) clasz.newInstance(); // Generates an instance of it cast to an Effect
            e.setProperties(properties); // Sets the Effect's internal properties to the values passed in the parameters
            return e;	// Returns the effect

        } catch (InstantiationException e) {
            LogUtils.error("Failed to build class.");
            e.printStackTrace();
            return null;

        } catch (ClassNotFoundException e) {
            LogUtils.error("Can't locate class: " + className);
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            LogUtils.error("Something went wrong while building an Action!");
            e.printStackTrace();
            return null;
        }

    }

}
