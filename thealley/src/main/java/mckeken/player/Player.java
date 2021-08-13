package mckeken.player;

import mckeken.inventory.*;
import mckeken.main.Manager;

public class Player {

	enum Gender {
		MALE,
		FEMALE
	}

	PlayerResourceManager resourceManager;

	/*final private int DEFAULT_MAX_HEALTH = 100;
	final private int DEFAULT_HEALTH     = 50;

	private int DEFAULT_MAX_STAMINA = 50;
	private int DEFAULT_STAMINA     = 50;*/

	private String DEFAULT_PLAYER_NAME = "Player";

	private static String name;

	private static int level;

	private static int money;

	/* HP
	private static int health;
	private static int maxHealth;

	// Stamina
	private static int stamina;
	private static int maxStamina;
	*/
	private static Inventory inventory;

	// The room that the player is currently in. This value must always be a valid room id.
	private static int location;

	//*** Constructors ***//

	// Default constructor
	public Player() {
		name = DEFAULT_PLAYER_NAME;
		resourceManager = new PlayerResourceManager(Manager.playerResourceList);
		level = 1;

		inventory = new Inventory();
		money = 0;
		location = 0;
	}

	// Specific constructor
	public Player(String name, int level, Inventory inventory, int location, int money) {
		Player.name = name;

		Player.level = level;

		/*Player.health = health;
		Player.maxHealth = maxHealth;

		Player.stamina = stamina;
		Player.maxStamina = maxStamina;*/

		Player.money = money;

		Player.inventory = inventory;

		Player.location = location;

		resourceManager = new PlayerResourceManager(Manager.playerResourceList);
	}

	public PlayerResourceManager getResourceManager() {
		return resourceManager;
	}

	public void setResourceManager(PlayerResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		Player.level = level;
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

	public static int getMoney() {
		return money;
	}

	public static void setMoney(int money) {
		Player.money = money;
	}
}