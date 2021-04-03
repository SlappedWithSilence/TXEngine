package mckeken.item.effect;

import mckeken.item.effect.Effect;
import java.lang.InstantiationException;
import java.lang.ClassNotFoundException;
import java.lang.IllegalAccessException;
import mckeken.io.LogUtils;

public class EffectFactory {

	final private static String EFFECTS_PACKAGE = "mckeken.item.effect.effects.";

	public EffectFactory() {

	}

	public static Effect build(String className, int[] properties) {
		try {
			Class clasz = Class.forName(EFFECTS_PACKAGE + className);
			Effect e = (Effect)clasz.newInstance();
			e.setProperties(properties);
			return e;	
		} catch (InstantiationException e) {
			LogUtils.error("Failed to build class.");
			e.printStackTrace();
			return null;
		
		} catch (ClassNotFoundException e) {
			LogUtils.error("Can't locate class.");
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			LogUtils.error("Something went wrong while building an Effect!");
			e.printStackTrace();
			return null;
		}
		
	}

}