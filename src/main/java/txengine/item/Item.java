package txengine.item;

public class Item {

	private String name;
	private String description;
	private int id;
	private int value = 0;
	private int maxStacks = 16;

	// Generic constructor
	public Item() {
		name = "Generic Item";
		id = -1;
	}

	// Value constructor. 
	public Item(String name, String description, int id, int value, int maxStacks) {
		this.name = name;
		this.description = description;
		this.id = id;
		this.value = value;
		this.maxStacks = maxStacks;
	}

	// DEBUGGING FUNCTION
	public void print() {
		System.out.println(name + " | " + id + " | " + value + " | " + maxStacks);
	}

	public String summary() {
		return ("[" + getName() + "]\tValue: " + getValue() + "\t");
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}