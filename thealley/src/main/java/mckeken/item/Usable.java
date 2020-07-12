package mckeken.item;

import mckeken.item.effect.*;
import java.util.ArrayList;

public class Usable extends Item {

	private ArrayList<Effect> effects;

	public Usable() {
		effects = new ArrayList<Effect>();

	}


	public void use() {

	}

	public void clearEffects() {
		effects.clear();
	}

}