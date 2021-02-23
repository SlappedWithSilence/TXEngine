package mckeken.room.action;

import mckeken.room.Action;
import mckeken.color.*;
import mckeken.io.*;
import mckeken.item.Item;
import java.util.ArrayList;

// This action emulates the player entering a store
public class InventoryAction extends Action {
	@Override
	// Performs the action
	public void perform() {
		Manager.player.getInventory().display();
		System.out.println("What slot would you like to interact with? (enter -1 to exit)");
		input = LogUtils.getNumber(-1, Manager.player.getInventory().getUsage() - 1);


	}

}