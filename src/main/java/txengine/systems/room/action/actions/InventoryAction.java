package txengine.systems.room.action.actions;

import txengine.io.*;
import txengine.item.Consumable;
import txengine.item.Item;
import txengine.item.Usable;
import txengine.main.Manager;
import txengine.systems.room.action.Action;

import java.util.ArrayList;

// This action emulates the player entering a store
public class InventoryAction extends Action {

	private ArrayList<String> itemOptions = new ArrayList<String>(3);

	public InventoryAction() {
		itemOptions.add("Inspect");
		itemOptions.add("Use");
		itemOptions.add("Drop");
	}

	@Override
	// Performs the action
	public int perform() {

		while (true) {
			Manager.player.getInventory().display();

			System.out.println("What slot would you like to interact with? (enter -1 to exit)");
			int input = LogUtils.getNumber(-1, Manager.player.getInventory().getUsage()-1);

			if (input == -1) return enableOnComplete();

			System.out.println("What would you like to do with " + Manager.player.getInventory().getItemInstance(input).getName() + "?");
			LogUtils.numberedList(itemOptions);

			int itemOptionChoice = LogUtils.getNumber(0, itemOptions.size()-1);

			Item i = Manager.player.getInventory().getItemInstance(input);
			switch (itemOptionChoice) {
				case 0: // The case for inspection.
					System.out.println(i.getDescription());
					break;
				case 1: // The case for Use
					if (i instanceof Usable) {
						((Usable) i).use(Manager.player);
						if (i instanceof Consumable) Manager.player.getInventory().decrementItem(input);
					} else {
						System.out.println("It appears that " + i.getName() + " can't be used.");
					}
					break;
				case 2: // The case for drop
					Manager.player.getInventory().decrementItem(input);
					break;
				default:

			}
		}
	}

}