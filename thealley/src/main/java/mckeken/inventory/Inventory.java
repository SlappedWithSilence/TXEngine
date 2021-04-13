package mckeken.inventory;

import java.util.ArrayList;
import java.util.Collections;

import mckeken.main.Manager;
import mckeken.color.*;
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
				ArrayList<Integer> indexes = Utils.getAllInstances(itemIDs, i.getId()); // Seek the index of each stack
				boolean stackFound = false;
				for (int indx: indexes) { // For each stack
					if (itemQuantities.get(indx) < i.getMaxStacks()) { // Check if it is full
						incrementItem(indx); // Increment the stack count of that item
						stackFound = true;   // Flag that a non-full stack was located
						break;
					}
				}

				if (!stackFound) { // If no non-full stack was found
					usage++; // Create a new stack
					itemNames.add(i.getName()); 
					itemIDs.add(i.getId());
					itemQuantities.add(1);
				}
			break;
		}

	}

	// An overload for addItem() that allows the programmer to specify a quantity to add to the inventory
	public void addItem(Item item, int quantity) {
		for (int i = 0; i < quantity; i++) {
			addItem(item);
		}
	}

	// An overload for addItem() that allows the programmer to specify the item ID instead of a literal object
	public void addItem(int id) {
		addItem(Manager.itemList.get(id));
	}

	// An overload for addItem() that allows the programmer to specify the item ID instead of a literal object as well as quantity
	public void addItem(int id, int quantity) {
		for (int i = 0; i < quantity; i++) {
			addItem(id);
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

	// Increments the quantity of the item located at 'index'
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

	// Prints the inventory in a vertical numbered list
	public void display() {
		System.out.println("-------------------------------------------------------");
		System.out.println("------------          Inventory          --------------");
		System.out.println("-------------------------------------------------------");
		for (int i = 0; i < getUsage(); i++ ) {
			System.out.println("[" + i + "]" + itemNames.get(i) + "/t" + Colors.CYAN_BOLD + itemQuantities.get(i) + Colors.RESET);
		}
		System.out.println("-------------------------------------------------------");
	}

	// Returns an instance of the item class associated with the ID located at 'index' in the inventory
	public Item getItemInstance(int index) {
		return Manager.itemList.get( itemIDs.get(index) );
	}

}