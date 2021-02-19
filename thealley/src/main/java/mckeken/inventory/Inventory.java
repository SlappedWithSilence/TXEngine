package mckeken.inventory;

import java.util.ArrayList;
import java.util.Collections;

import mckeken.color.ColorConsole;
import mckeken.io.LogUtils;
import mckeken.item.Item;
import mckeken.util.Utils;

public class Inventory {

	// Constants
	private static final int MAX_CAPACITY = 50;

	// Variables
	private static int capacity = 16;
	private static int usage    = 0;
	private ArrayList<String>     itemNames;
	private ArrayList<Integer>    itemIDs;
	private ArrayList<Integer>    itemQuantities;


	// Default constructor. Initializes in the invetroy without any customization
	public Inventory() {
		itemNames     = new ArrayList<String>();
		itemIDs       = new ArrayList<Integer>();
		itemQuantities = new ArrayList<Integer>();
	}

	// Capacity constructor. Initializes the inventory with capacity passed in param 0;
	public Inventory(int capacacity) {
		this.capacity = capacity;
		itemNames     = new ArrayList<String>();
		itemIDs       = new ArrayList<Integer>();
		itemQuantities = new ArrayList<Integer>();
	}

	// Add an item to the inventory. This function should only ever be called after ensuring there is space in the inventory.
	public void addItem(Item i) {
		if (usage >= capacity) {
			LogUtils.error("Tried to add an item when the inventory was full! This is an error, please report it on https://github.com/TopperMcKek/TheAlley");
		}

		// Check if the inventory contains that item already
		switch (Collections.frequency(itemIDs, i.getId() ) ) {
			case 0: // There is no pre-existing stack of this item
				usage++;
				itemNames.add(i.getName());
				itemIDs.add(i.getId());
				itemQuantities.add(1);
			break;

			case 1: // There is one stack of the item that already exists

				// Check if there's a non-full stack of the item
				if (itemQuantities.get(itemIDs.indexOf(i.getId())) < i.getMaxStacks() ) { // if a non-full stack exists
					incrementItem(itemIDs.indexOf(i.getId())); // Increment the stack count of that item
				} else { // if only a full stack exists
					usage++; // Add a new stack to the inventory
					itemNames.add(i.getName()); // Push the item's name to the inventory
					itemIDs.add(i.getId());     // Push the item's ID to the inventory
					itemQuantities.add(1);      // Push a stack size of one to the inventory
				}
			break;

			default: // There are multiple existing stacks of this item
				ArrayList<Integer> indexes = Utils.getAllInstances(itemIDs, i.getId());
				boolean stackFound = false;
				for (int indx: indexes) {
					if (itemQuantities.get(indx) < i.getMaxStacks()) {
						incrementItem(indx); // Increment the stack count of that item
						stackFound = true;
						break;
					}
				}

				if (!stackFound) {
					usage++;
					itemNames.add(i.getName());
					itemIDs.add(i.getId());
					itemQuantities.add(1);
				}
			break;
		}

	}

	public void removeItem(int index) {
		itemNames.remove(index);
		itemIDs.remove(index); 
		itemQuantities.remove(index); 
	}

	public void decrementItem(int index) {
		if (itemQuantities.get(index) != null) {
			itemQuantities.set(index, itemQuantities.get(index) - 1);

			if (itemQuantities.get(index) <= 0) {
				removeItem(index);
			}
		}
	}

	public void incrementItem(int index) {
		if (itemQuantities.get(index) != null) {
			itemQuantities.set(index, itemQuantities.get(index) + 1);

			if (itemQuantities.get(index) <= 0) {
				removeItem(index);
			}
		}
	}

	public void setCapacity(int i) {
		capacity = i;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setUsage(int i) {
		usage = i;
	}

	public int getUsage() {
		return usage;
	}

}