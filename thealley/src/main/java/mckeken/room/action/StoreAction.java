package mckeken.room.action;

import mckeken.room.Action;
import mckeken.color.*;
import mckeken.io.*;

// This action emulates the player entering a store
public class StoreAction extends Action {

	String storePrompt;
	ArrayList<Item> inventory;
	ArrayList<Integer> cost;

	@Override
	// Performs the action
	public void perform() {
		if (!checkConfig()) {
			return;
		}

		printStorePrompt();
		displayInventory();

	}

	private void printStorePrompt() {
		System.out.println(storePrompt);
	}

	private void displayInventory() {
		System.out.println("-------------------------------------------------------")
		System.out.println("------------        Store Inventory      --------------")
		System.out.println("-------------------------------------------------------")
		for (int i; i < inventory.size(); i++ ) {
			System.out.println(inventory.get(i).getName() + "/t" + Colors.CYAN_BOLD + cost.get(i) + Colors.RESET);
		}
		System.out.println("-------------------------------------------------------")
	}

	private boolean checkConfig() {
		if (inventory.size() != cost.size()) return false;

		return true;
	}



}
