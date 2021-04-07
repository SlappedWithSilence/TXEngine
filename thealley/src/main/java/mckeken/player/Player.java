package mckeken.player;

import mckeken.inventory.*;

public class Player {

	enum Gender {
		MALE,
		FEMALE
	}

	final private int DEFAULT_MAX_HEALTH = 100;
	final private int DEFAULT_HEALTH     = 50;

	private int DEFAULT_MAX_STAMINA = 50;
	private int DEFAULT_STAMINA     = 50;

	private String DEFAULT_PLAYER_NAME = "Player";

	private static String name;

	private static int level;

	// HP
	private static int health;
	private static int maxHealth;

	// Stamina
	private static int stamina;
	private static int maxStamina;

	private static Inventory inventory;

	// The room that the player is currently in. This value must always be a valid room id.
	private static int location;

	//*** Constructors ***//

	// Default constructor
	public Player() {
		name = DEFAULT_PLAYER_NAME;

		level = 1;

		health = DEFAULT_HEALTH;
		maxHealth = DEFAULT_MAX_HEALTH;

		stamina = DEFAULT_STAMINA;
		maxStamina = DEFAULT_MAX_STAMINA;

		inventory = new Inventory();

		location = 0;
	}

	// Specific constructor
	public Player(String name, int level, int health, int maxHealth, int stamina, int maxStamina, Inventory inventory, int location) {
		Player.name = name;

		Player.level = level;

		Player.health = health;
		Player.maxHealth = maxHealth;

		Player.stamina = stamina;
		Player.maxStamina = maxStamina;

		Player.inventory = inventory;

		Player.location = location;
	}

	public void modifyHealth(int amount) {
		health += amount;

		if (health > maxHealth) health = maxHealth;
	}

	public void modifyStamina(int amount) {
		stamina += amount;

		if (health > maxStamina) stamina = maxStamina;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		Player.level = level;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int i) {
		health = i;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public  String getName() {
		return name;
	}

	public  int getMaxHealth() {
		return maxHealth;
	}

	public  int getStamina() {
		return stamina;
	}

	public  int getMaxStamina() {
		return maxStamina;
	}

	public  int getLocation() {
		return location;
	}
}