package txengine.systems.item;

import txengine.systems.combat.CombatEntity;
import txengine.ui.LogUtils;
import txengine.systems.item.effect.*;
import txengine.ui.Out;

import java.util.ArrayList;

public class Usable extends Item {

	ArrayList<Effect> effects;

	public Usable() {
		effects = new ArrayList<>();
	}

	public Usable(String name, String description, int id, int value, int maxStacks, ArrayList<Effect> effects) {
		super(name, description, id, value, maxStacks);
		this.effects = effects;
	}


	public void use(CombatEntity combatEntity) {
		if (this.effects.size() < 1) {
			Out.error("Error! Cannot use an item with no effects! Please report this issue on Github at https://github.com/TopperMcKek/TheAlley/issues");
		} else {
			for (Effect e : this.effects) e.perform(combatEntity);
		}
	}

	public void clearEffects() {
		effects.clear();
	}

	public void addEffect(Effect e) {
		effects.add(e);
	}

	public void setEffects(ArrayList<Effect> ef) {
		effects = ef;
	}

	public ArrayList<Effect> getEffects() {
		return effects;
	}
}