package txengine.systems.integration;

import txengine.ui.LogUtils;
import txengine.ui.Out;

public class RequirementFactory {

    final private static String REQUIREMENTS_PACKAGE = "txengine.systems.integration.requirements."; // The fully-qualified package name where Effects are found

    public RequirementFactory() {

    }

    // Returns an instance of an Effect that has the properties passed in the parameter
    public static Requirement build(String className, String[] properties) {
        try {
            Class clasz = Class.forName(REQUIREMENTS_PACKAGE + className); // Looks up the class name passed in
            Requirement r = (Requirement) clasz.newInstance(); // Generates an instance of it cast to an Effect
            r.setProperties(properties); // Sets the Effect's internal properties to the values passed in the parameters
            return r;	// Returns the effect

        } catch (InstantiationException e) {
            Out.error("Failed to build class.");
            e.printStackTrace();
            return null;

        } catch (ClassNotFoundException e) {
            Out.error("Can't locate class: " + className);
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            Out.error("Something went wrong while building a Requirement!");
            e.printStackTrace();
            return null;
        }

    }

}
