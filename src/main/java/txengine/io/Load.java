package txengine.io;

import txengine.integration.Requirement;
import txengine.systems.item.effect.EffectFactory;
import txengine.systems.room.Room;
import txengine.systems.room.action.Action;
import txengine.systems.room.action.ActionFactory;
import txengine.systems.conversation.Conversation;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.lang.Long;
import java.util.List;

import txengine.systems.item.effect.Effect;
import txengine.main.Manager;
import txengine.systems.combat.Player;
import txengine.systems.item.*;

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
