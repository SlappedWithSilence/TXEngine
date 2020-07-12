package mckeken.player;

import mckeken.inventory.*;

public class Player {

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

	public Player() {
		name = DEFAULT_PLAYER_NAME;

		health = DEFAULT_HEALTH;
		maxHealth = DEFAULT_MAX_HEALTH;

		stamina = DEFAULT_STAMINA;
		maxStamina = DEFAULT_MAX_STAMINA;

		inventory = new Inventory();
	}

}