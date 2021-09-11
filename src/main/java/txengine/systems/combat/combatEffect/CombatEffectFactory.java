package txengine.systems.combat.combatEffect;

import txengine.io.LogUtils;

public class CombatEffectFactory {

    final private static String COMBAT_EFFECTS_PACKAGE = "mckeken.systems.combat.combatEffect.combatEffects."; // The fully-qualified package name where Effects are found

    public CombatEffectFactory() {

    }

    // Returns an instance of an Effect that has the properties passed in the paramter
    public static CombatEffect build(String className, int duration, String triggerMessage, String cleanupMessage, String[] properties) {
        try {
            Class clasz = Class.forName(COMBAT_EFFECTS_PACKAGE + className); // Looks up the class name passed in
            CombatEffect e = (CombatEffect) clasz.newInstance(); // Generates an instance of it cast to an Effect
            e.setProperties(properties); // Sets the Effect's internal properties to the values passed in the paramters
            e.setDuration(duration);
            e.setCleanupMessage(cleanupMessage);
            e.setTriggerMessage(triggerMessage);
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
            LogUtils.error("Something went wrong while building a CombatEffect!");
            e.printStackTrace();
            return null;
        }

    }

}
