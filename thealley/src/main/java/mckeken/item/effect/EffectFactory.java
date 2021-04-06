package mckeken.item.effect;

import mckeken.item.effect.Effect;
import java.lang.InstantiationException;
import java.lang.ClassNotFoundException;
import java.lang.IllegalAccessException;
import mckeken.io.LogUtils;

public class EffectFactory {

	final private static String EFFECTS_PACKAGE = "mckeken.item.effect.effects."; // The fully-qualified package name where Effects are found

	public EffectFactory() {

	}

	// Returns an instance of an Effect that has the properties passed in the paramter
	public static Effect build(String className, Integer[] properties) {
		try {
			Class clasz = Class.forName(EFFECTS_PACKAGE + className); // Looks up the class name passed in
			Effect e = (Effect)clasz.newInstance(); // Generates an instance of it cast to an Effect
			e.setProperties(properties); // Sets the Effect's internal properties to the values passed in the paramters
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
			LogUtils.error("Something went wrong while building an Effect!");
			e.printStackTrace();
			return null;
		}
		
	}

}