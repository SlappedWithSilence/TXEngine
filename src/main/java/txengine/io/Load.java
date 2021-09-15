package txengine.io;

import txengine.main.Manager;
import txengine.systems.combat.Player;

public class Load {

	// Checks for a saved game
	// TODO: Implement
	public static boolean hasSave() {
		return false;
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

}
