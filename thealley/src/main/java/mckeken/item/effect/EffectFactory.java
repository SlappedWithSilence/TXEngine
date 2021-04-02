package mckeken.item.effect;

import mckeken.item.effect.Effect;

public class EffectFactory {

	public EffectFactory() {

	}

	public Effect build(String className, int[] properties) {
		Class clasz = Class.forName(className);
		Effect e = clasz.newInstance();
		e.setProperties(properties);
		return e;
	}

}