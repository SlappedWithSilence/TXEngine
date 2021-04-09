package mckeken.room.action.actions;

import mckeken.color.*;
import mckeken.io.LogUtils;
import mckeken.item.Item;
import mckeken.main.Manager;
import mckeken.room.action.Action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Iterator;

// This action emulates the player entering a store
public class StoreAction extends Action {

	ArrayList<Integer> inventoryIDs;
	ArrayList<Integer> costs;

	public StoreAction() {

	}

	public StoreAction(String menuName, String text, String[] properties, boolean enabled, int unlockIndex) {
		super(menuName, text, properties, enabled, unlockIndex);
	}

	@Override
	// Performs the action
	public int perform() {

		Iterator<String> propIterator = Arrays.stream(super.properties).iterator();

		// Iterate through the shop's properties to build a side-by-side pair of arrays that contain the IDs of the items offered and their respective costs
		inventoryIDs = new ArrayList<Integer>();
		costs = new ArrayList<Integer>();

		while(propIterator.hasNext()) {
			Integer id = Integer.parseInt(propIterator.next());
			Integer cost = Integer.parseInt(propIterator.next());

			inventoryIDs.add(id);
			costs.add(cost);
		}

		if (!checkConfig()) {
			return -1;
		}

		printStorePrompt();
		displayInventory();
		return enableOnComplete();
	}

	private void printStorePrompt() {
		System.out.println(super.text);
	}

	private void displayInventory() {
		System.out.println("-------------------------------------------------------");
		System.out.println("------------        Store Inventory      --------------");
		System.out.println("-------------------------------------------------------");
		for (int i = 0; i < inventoryIDs.size(); i++ ) {
			System.out.println(
					Manager.itemList.get(inventoryIDs.get(i)).getName()
							+ "\t" + Colors.CYAN_BOLD + costs.get(i) + Colors.RESET);
		}
		System.out.println("-------------------------------------------------------");
	}

	private boolean checkConfig() {
		if (super.properties.length % 2 != 0 ) {
			LogUtils.error("A StoreAction had a mismatching inventory and cost array! Rooms.json may be corrupted!\n");
			throw new InputMismatchException();
		}

		return true;
	}



}
