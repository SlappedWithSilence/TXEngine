package mckeken.room.action.actions;

import mckeken.io.*;
import mckeken.main.Manager;
import mckeken.room.action.Action;

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
		Manager.player.getInventory().display();
		
		System.out.println("What slot would you like to interact with? (enter -1 to exit)");
		int input = LogUtils.getNumber(-1, Manager.player.getInventory().getUsage());

		if (input == -1) return enableOnComplete();

		System.out.println("What would you like to do with " + Manager.player.getInventory().getItemInstance(input).getName() + "?");
		LogUtils.numberedList(itemOptions);

		return enableOnComplete();

	}

}