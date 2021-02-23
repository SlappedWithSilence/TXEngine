package mckeken.player;

import mckeken.inventory.*;

public class Player {

	public Enum gender {
		MALE,
		FEMALE;
	}

	private int DEFAULT_MAX_HEALTH = 100;
	private int DEFAULT_HEALTH     = 100;

	private int DEFAULT_MAX_STAMINA = 50;
	private int DEFAULT_STAMINA     = 50;

	private String DEFAULT_PLAYER_NAME = "Player";

	private static String name;

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

		health = DEFAULT_HEALTH;
		maxHealth = DEFAULT_MAX_HEALTH;

		stamina = DEFAULT_STAMINA;
		maxStamina = DEFAULT_MAX_STAMINA;

		inventory = new Inventory();

		location = 0;
	}

	// Specific constructor
	public Player(String name, int health, int maxHealth, int stamina, int maxStamina, Inventory inventory, int location) {
		this.name = name;

		this.health = health;
		this.maxHealth = maxHealth;

		this.stamina = stamina;
		this.maxStamina = maxStamina;

		this.inventory = inventory;

		this.location = location;
	}

	public void modifyHealth(int amount) {
		health += amount;

		if (health > maxHealth) health = maxHealth;
	}	

}