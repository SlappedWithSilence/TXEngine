package mckeken.item;

public class Item {

	private String name;
	private int id;
	private int value = 0;
	private int maxStacks = 16;

	public Item() {
		name = "Generic Item";
		id = -1;
	}

	public Item(String name, int id, int value, int maxStacks) {
		this.name = name;
		this.id = id;
		this.value = value;
		this.maxStacks = maxStacks;
	}

	public void print() {
		System.out.println(name + " | " + id + " | " + value + " | " + maxStacks);
	}

	public String getName() {
		return name;
	}

	public void setName(String s) {
		this.name = s;
	}

	public int getId() {
		return id;
	}

	public void setId(int i) {
		this.id = i;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int i) {
		this.value = i;
	}

	public int getMaxStacks() {
		return maxStacks;
	}

	public void setMaxStacks(int i) {
		this.value = i;
	}

}