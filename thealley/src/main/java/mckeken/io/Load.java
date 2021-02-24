package mckeken.io;

import mckeken.main.Manager;
import mckeken.player.Player;

public class Load {

	// Checks for a saved game
	// TODO: Implement
	public static boolean hasSave() {
		return true;
	}

	// Loads a saved game from disk
	// TODO: Implement
	public static void loadGame() {

	}

	// Sets up and configures a new game state
	// TODO: Implement
	public static void initializeNewGame() {
		Manager.player = new Player();
	}

	// Loads items from disk
	// TODO: Implement
	public static void loadItems() {

	}

	// Loads rooms from disk
	// TODOL Implement
	public static void loadRooms() {

	}
}