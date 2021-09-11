package txengine.systems.item;

import txengine.systems.item.effect.Effect;

import java.util.ArrayList;

public class Consumable extends Usable {

	// Constructors
	public Consumable() {
		super();

	}

	public Consumable(String name, String description, int id, int value, int maxStacks, ArrayList<Effect> effects) {
		super(name, description, id, value, maxStacks, effects);
	}



}