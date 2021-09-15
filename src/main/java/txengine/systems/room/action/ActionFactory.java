package txengine.systems.room.action;

import txengine.systems.integration.Requirement;
import txengine.io.LogUtils;

import java.util.List;

public class ActionFactory {

        final private static String ACTIONS_PACKAGE = "txengine.systems.room.action.actions."; // The fully-qualified package name where Effects are found

        public ActionFactory() {

        }

        // Returns an instance of an Effect that has the properties passed in the paramter
        public static Action build(String className, String menuName, String text, boolean enabled, int unlockedIndex, String[] properties, List<Requirement> requirements) {
            try {
                Class clasz = Class.forName(ACTIONS_PACKAGE + className); // Looks up the class name passed in
                Action a = (Action)clasz.newInstance(); // Generates an instance of it cast to an Effect
                a.setProperties(properties); // Sets the Effect's internal properties to the values passed in the parameters
                a.setMenuName(menuName);
                a.setText(text);
                a.setHidden(enabled);
                a.setUnhideIndex(unlockedIndex);
                a.setRequirements(requirements);
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
