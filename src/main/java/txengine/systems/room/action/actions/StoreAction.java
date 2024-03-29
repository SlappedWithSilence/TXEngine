package txengine.systems.room.action.actions;

import txengine.ui.color.*;
import txengine.systems.integration.Requirement;
import txengine.ui.component.Components;
import txengine.ui.LogUtils;
import txengine.main.Manager;
import txengine.systems.room.action.Action;
import txengine.util.Utils;

import java.util.ArrayList;
import java.util.List;

// This action emulates the player entering a store
public class StoreAction extends Action {

	// Stores can have multiple modes that govern how the store reacts to the user making a purchase
	// Unlimited : The user may make as many purchases as they want. Price never changes.
	// Demand	 : The user may make as many purchases as they want. Price goes up with every purchase.
	// Single	 : The user may purchase a single instance of each item. Price never changes.
	enum StoreMode {
		UNLIMITED , DEMAND, SINGLE
	}

	StoreMode storeMode;

	final String CHOICE_PROMPT = "What would you like to purchase? (-1 to exit)";

	ArrayList<Integer> inventoryIDs;
	ArrayList<Integer> costs;

	public StoreAction() {
		storeMode = StoreMode.DEMAND;
	}

	public StoreAction(String menuName, String text, String[] properties, boolean enabled, int unlockIndex, boolean hideOnUse, List<Requirement> requirements) {
		super(menuName, text, properties, enabled, unlockIndex, hideOnUse, requirements);
	}

	private int purchase(int index) {
		Manager.player.setMoney(Manager.player.getMoney() - costs.get(index)); // Subtract the amount of money that the item costs
		Manager.player.getInventory().addItem(inventoryIDs.get(index), 1);
		switch (storeMode) {
			default:
			case UNLIMITED:

				return costs.get(index);

			case DEMAND:
				return (int) (costs.get(index) * 1.85);

			case SINGLE:
				// TODO: Implement
				return costs.get(index);
		}
	}

	@Override
	// Performs the action
	public int perform() {

		// Iterate through the shop's properties to build a side-by-side pair of arrays that contain the IDs of the items offered and their respective costs
		inventoryIDs = new ArrayList<>();
		costs = new ArrayList<>();

		// Load the id-cost value pairs and insert them into the arraylists
		for (String s : properties) {
			int[] vals = Utils.parseInts(s, ",");
			inventoryIDs.add(vals[0]);
			costs.add(vals[1]);
		}

		printStorePrompt();


		while(true) {
			System.out.println();

			displayInventory();
			int choice = LogUtils.getNumber(-1, inventoryIDs.size() - 1);

			if (choice == -1) {
				break;
			} else {
				int itemChoiceID = inventoryIDs.get(choice);
				Components.header("Purchase Item");
				Components.subHeader(List.of("Money: " + Manager.player.getMoney()));

				if (Manager.player.getMoney() >= costs.get(choice)) {
					ColorConsole.d("Are you sure you want to purchase " + Manager.itemManager.get_instance(itemChoiceID).getName() + "? ", false);

					if (LogUtils.getAffirmative()) {
						costs.set(choice, purchase(choice)); // Purchase the item, and update the cost of the item based on the store's purchase mode.
					}
				} else {
					ColorConsole.d("You can't afford that item, sorry!\n", false);
				}

			}

		}

		return enableOnComplete();
	}

	private void printStorePrompt() {
		System.out.println(super.text);
	}

	private void displayInventory() {
		Components.header("Shop Inventory");
		Components.subHeader(List.of("Money: " + Manager.player.getMoney()));
		ColorConsole.d(CHOICE_PROMPT+"\n", false);
		for (int i = 0; i < inventoryIDs.size(); i++ ) {
			System.out.println("[" + i + "] " +
					Manager.itemManager.get_instance(inventoryIDs.get(i)).getName()
							+ "\t" + Colors.CYAN_BOLD + costs.get(i) + Colors.RESET);
		}
		Components.bar();
	}

}
