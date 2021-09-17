package txengine.systems.inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import txengine.main.Manager;
import txengine.ui.color.*;
import txengine.ui.component.Components;
import txengine.ui.LogUtils;
import txengine.systems.item.Item;
import txengine.util.Utils;

public class Inventory {

	// Constants
	private static final int MAX_CAPACITY = 50;

	// Variables
	private int capacity = 16;
	private int usage    = 0;
	private ArrayList<String>     itemNames;
	private ArrayList<Integer>    itemIDs;
	private ArrayList<Integer>    itemQuantities;


	// Default constructor. Initializes in the inventory without any customization
	public Inventory() {
		itemNames     = new ArrayList<>();
		itemIDs       = new ArrayList<>();
		itemQuantities = new ArrayList<>();
	}

	// Capacity constructor. Initializes the inventory with capacity passed in param 0;
	public Inventory(int capacity) {
		this.capacity = capacity;
		itemNames     = new ArrayList<>();
		itemIDs       = new ArrayList<>();
		itemQuantities = new ArrayList<>();
	}

	// List constructor. Builds an inventory from a pair of lists
	public Inventory(List<Integer> itemIds, List<Integer> itemQuantities) {
		this.itemNames     = new ArrayList<>();
		this.itemIDs       = new ArrayList<>();
		this.itemQuantities = new ArrayList<>();

		if (itemIds.size() != itemQuantities.size()) {
			LogUtils.error("Something went wrong building an Inventory. itemIDs and itemQuantities are not equal in length!\n");
			return;
		}

		for (int i = 0; i < itemIds.size(); i++) {
			addItem(itemIds.get(i), itemQuantities.get(i));
		}

	}

	public Inventory(Inventory inventory) {
		capacity = inventory.capacity;
		usage = inventory.usage;
		itemNames = new ArrayList<>(inventory.itemNames);
		itemIDs = new ArrayList<>(inventory.itemIDs);
		itemQuantities = new ArrayList<>(inventory.itemQuantities);
	}

	public ArrayList<Integer> getItemIDs() {
		return itemIDs;
	}

	// Add an item to the inventory. This function should only ever be called after ensuring there is space in the inventory.
	public void addItem(Item i) {
		if (i == null) LogUtils.error("tried to add a null item");

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
				for (int index: indexes) { // For each stack
					if (itemQuantities.get(index) < i.getMaxStacks()) { // Check if it is full
						incrementItem(index); // Increment the stack count of that item
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
		addItem(Manager.itemHashMap.get(id));
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
		usage--;
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

	// There are three cases:
	// - Case 0: Inventory contains no stacks of the item
	// - Case 1: Inventory contains one stack of the item
	// - Case 2: Inventory contains multiple stacks of the item
	public int getItemQuantity(int itemID) {

		// Case 0:
		if (!itemIDs.contains(itemID)) return 0;

		// Case 1 & 2
		ArrayList<Integer> instanceIndexes = Utils.getAllInstances(itemIDs, itemID);
		int quantitySum = 0;
		for (int i : instanceIndexes) quantitySum+=itemQuantities.get(i);

		return quantitySum;
	}

	// Removes 'quantity' of the item with id='itemID' from any stack in the inventory
	// Returns true if successful, false, otherwise
	public boolean consumeQuantity(int itemID, int quantity) {
		if (getItemQuantity(itemID) < quantity) return false;

		int remaining = quantity; // Amount of the item that needs to be removed

		while (remaining > 0) {
			ArrayList<Integer> stackIndexes = Utils.getAllInstances(itemIDs, itemID);

			int stackSize = itemQuantities.get( stackIndexes.get(0));

			// If the stack at the index has more than the remaining quantity of items, decrement the stack by 'remaining'.
			if (stackSize > remaining) {
				itemQuantities.set(stackIndexes.get(0), stackSize - remaining); // Update stack
				remaining = 0; // Update remaining
			} else if (stackSize == remaining) {
				remaining = 0; // Update remaining
				removeItem(stackIndexes.get(0)); // Remove stack
			} else {
				remaining = remaining - stackSize; // Update remaining
				removeItem(stackIndexes.get(0)); // Remove stack
			}
		}

		return true;
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
		Components.header("Inventory");
		printItems();
	}

	public void printItems() {
		for (int i = 0; i < getUsage(); i++ ) {
			System.out.println("[" + i + "] " + itemNames.get(i) + "\t" + Colors.CYAN_BOLD + itemQuantities.get(i) + Colors.RESET);
		}

		if(getUsage() == 0) System.out.println("Your inventory is empty");

		Components.bar();
	}

	// Returns an instance of the item class associated with the ID located at 'index' in the inventory
	public Item getItemInstance(Integer index) {
		return Manager.itemHashMap.get( itemIDs.get(index) );
	}

	public List<Item> getItemInstances() {
		return itemIDs.stream().map(integer -> Manager.itemHashMap.get(integer)).toList();
	}
}