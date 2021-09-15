package txengine.systems.room.action.actions;

import txengine.systems.item.Consumable;
import txengine.systems.item.Item;
import txengine.systems.item.Usable;
import txengine.main.Manager;
import txengine.systems.room.action.Action;
import txengine.ui.component.Components;
import txengine.ui.LogUtils;

import java.util.ArrayList;

// This action emulates the player entering a store
public class InventoryAction extends Action {

	private ArrayList<String> itemOptions = new ArrayList<>(3);

	public InventoryAction() {
		itemOptions.add("Inspect");
		itemOptions.add("Use");
		itemOptions.add("Equip");
		itemOptions.add("Drop");
	}

	@Override
	// Performs the action
	// TODO: Improve option-scanning (use only shows up for usable, equip only shows up for Equipment, etc)
	public int perform() {

		while (true) {
			Manager.player.getInventory().display();

			System.out.println("What slot would you like to interact with? (enter -1 to exit)");
			int input = LogUtils.getNumber(-1, Manager.player.getInventory().getUsage()-1);

			if (input == -1) return enableOnComplete();

			System.out.println("What would you like to do with " + Manager.player.getInventory().getItemInstance(input).getName() + "?");
			Components.numberedList(itemOptions);

			int itemOptionChoice = LogUtils.getNumber(0, itemOptions.size()-1);

			Item i = Manager.player.getInventory().getItemInstance(input);
			switch (itemOptionChoice) {
				case 0: // The case for inspection.
					System.out.println(i.inspect());
					break;
				case 1: // The case for Use
					if (i instanceof Usable) {
						((Usable) i).use(Manager.player);
						if (i instanceof Consumable) Manager.player.getInventory().decrementItem(input);
					} else {
						System.out.println("It appears that " + i.getName() + " can't be used.");
					}
					break;
				case 2:
					if (Manager.player.getEquipmentManager().equip(i.getId())) {
						System.out.println("You equip yourself with " + i.getName());
					} else {
						System.out.println("You weren't able to equip " + i.getName());
					}

					break;
				case 3: // The case for drop
					Manager.player.getInventory().decrementItem(input);
					break;
				default:

			}
		}
	}

}