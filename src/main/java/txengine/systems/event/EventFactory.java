package txengine.systems.event;

import txengine.ui.LogUtils;
import txengine.ui.Out;

public class EventFactory {

    final private static String EVENTS_PACKAGE = "txengine.systems.event.events."; // The fully-qualified package name where Effects are found

    public EventFactory() {

    }

    // Returns an instance of an Event that has the properties passed in the parameter
    public static Event build(String className, String[] properties) {
        try {
            Class clasz = Class.forName(EVENTS_PACKAGE + className); // Looks up the class name passed in
            Event e = (Event) clasz.newInstance(); // Generates an instance of it cast to an Event
            e.setProperties(properties); // Sets the Events's internal properties to the values passed in the parameters
            return e;	// Returns the event

        } catch (InstantiationException e) {
            Out.error("Failed to build class.");
            e.printStackTrace();
            return null;

        } catch (ClassNotFoundException e) {
            Out.error("Can't locate class: " + className);
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            Out.error("Something went wrong while building an Event!");
            e.printStackTrace();
            return null;
        }

    }

}
