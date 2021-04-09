package mckeken.room.action;

import mckeken.io.LogUtils;
import mckeken.item.effect.Effect;

public class ActionFactory {

        final private static String ACTIONS_PACKAGE = "mckeken.room.action.actions."; // The fully-qualified package name where Effects are found

        public ActionFactory() {

        }

        // Returns an instance of an Effect that has the properties passed in the paramter
        public static Action build(String className, String[] properties) {
            try {
                Class clasz = Class.forName(ACTIONS_PACKAGE + className); // Looks up the class name passed in
                Action a = (Action)clasz.newInstance(); // Generates an instance of it cast to an Effect
                a.setProperties(properties); // Sets the Effect's internal properties to the values passed in the paramters
                return a;	// Returns the effect

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
