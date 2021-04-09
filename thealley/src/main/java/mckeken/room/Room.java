package mckeken.room;

import mckeken.io.LogUtils;
import mckeken.room.action.Action;
import mckeken.room.action.actions.MoveAction;

import java.util.ArrayList;

public class Room {

	// *** Class Varaibles ***//
	int id;      // The room's id. This is used to track which room the user is in as well as to map the user into new rooms. All IDs must be unique.
	String name; // The room's name. Used for visual prompting.
	ArrayList<Action> onFirstEnterActions = new ArrayList<Action>(); // A list of actions to be performed series the first time a user enters a room
	ArrayList<Action> roomActions = new ArrayList<Action>(); // A list of things the user can do in the room

	String introPrompt; // The string that is printed when the user first enters the room

	public void printActions() {
		for (int i = 0; i < roomActions.size(); i++) {
			
			System.out.print("[" + i + "] "); 
			roomActions.get(i).print();

		}
	}

	public void enter() {
		boolean stay = true;

		while (stay) {
			printActions();
			int userSelection = LogUtils.getNumber(0, roomActions.size());

			roomActions.get(userSelection).perform();

			if (roomActions.get(userSelection) instanceof MoveAction) break;
		}
	}
}