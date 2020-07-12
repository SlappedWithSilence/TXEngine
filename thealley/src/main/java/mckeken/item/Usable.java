package mckeken.item;

import mckeken.item.effect.*;
import mckeken.color.ColorConsole;

import java.util.ArrayList;

public class Usable extends Item {

	private ArrayList<Effect> effects;

	public Usable() {
		effects = new ArrayList<Effect>();
	}


	public void use() {
		if (effects.size() < 1) ColorConsole.e("Error! Cannot use an item with no effects! Please report this issue on Github at https://github.com/TopperMcKek/TheAlley/issues");
	}

	public void clearEffects() {
		effects.clear();
	}

}