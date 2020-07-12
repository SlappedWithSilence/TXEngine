package mckeken.inventory;

import java.util.ArrayList;
import mckeken.color.ColorConsole;
import mckeken.io.LogUtils;
import mckeken.item.Item;

public class Inventory {

	// Constants
	private static final int MAX_CAPACITY = 50;

	// Variables
	private static int capacity = 16;
	private static int usage    = 0;
	private ArrayList<String> itemNames;
	private ArrayList<Integer>    itemIDs;
	private ArrayList<Integer>    itemQuantities;



	public Inventory() {
		itemNames     = new ArrayList<String>(capacity);
		itemIDs       = new ArrayList<Integer>(capacity);
		itemQuantities = new ArrayList<Integer>(capacity);
	}

	public Inventory(int capacacity) {
		itemNames     = new ArrayList<String>(capacity);
		itemIDs       = new ArrayList<Integer>(capacity);
		itemQuantities = new ArrayList<Integer>(capacity);
	}

	public void addItem(Item i) {
		if (usage >= capacity) {
			LogUtils.error("Tried to add an item when the inventory was full! This is an error, please report it on https://github.com/TopperMcKek/TheAlley");
		}
	}

	public void removeItem(int index) {
		itemNames.set(index, null);
		itemIDs.set(index, null); 
		itemQuantities.set(index, null); 
	}

	public void decrementItem(int index) {
		if (itemQuantities.get(index) != null) {
			itemQuantities.set(index, itemQuantities.get(index) - 1);

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