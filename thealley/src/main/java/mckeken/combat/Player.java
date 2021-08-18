package mckeken.combat;

import mckeken.inventory.*;
import mckeken.main.Manager;

public class Player extends CombatEntity {

	enum Gender {
		MALE,
		FEMALE
	}

	private final String DEFAULT_PLAYER_NAME = "Player";

	private int level;

	private int money;

	// The room that the player is currently in. This value must always be a valid room id.
	private int location;

	//*** Constructors ***//

	// Default constructor
	public Player() {
		name = DEFAULT_PLAYER_NAME;
		resourceManager = new CombatResourceManager(Manager.playerResourceList);
		level = 1;

		inventory = new Inventory();
		money = 0;
		location = 0;
	}

	// Specific constructor
	public Player(String name, int level, Inventory inventory, int location, int money) {
		this.name = name;

		this.level = level;

		this.money = money;

		this.inventory = inventory;

		this.location = location;

		resourceManager = new CombatResourceManager(Manager.playerResourceList);
	}

	public CombatResourceManager getResourceManager() {
		return resourceManager;
	}

	public void setResourceManager(CombatResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public  String getName() {
		return name;
	}

	public  int getLocation() {
		return location;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
}