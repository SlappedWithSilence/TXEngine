package mckeken.room.action;

import mckeken.room.Action;
import mckeken.color.*;
import mckeken.io.*;
import mckeken.item.Item;
import mckeken.main.Manager;
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
	public void perform() {
		Manager.player.getInventory().display();
		
		System.out.println("What slot would you like to interact with? (enter -1 to exit)");
		int input = LogUtils.getNumber(-1, Manager.player.getInventory().getUsage());

		System.out.println("What would you like to do with " + Manager.player.getInventory().getItemInstance(input));
		LogUtils.numberedList(itemOptions);

	}

}