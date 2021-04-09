package mckeken.room;

import mckeken.room.action.Action;

import java.util.ArrayList;

public class Room {

	// *** Class Varaibles ***//
	int id;      // The room's id. This is used to track which room the user is in as well as to map the user into new rooms. All IDs must be unique.
	String name; // The room's name. Used for visual prompting.

	ArrayList<Action> roomActions = new ArrayList<Action>(); // A list of things the user can do in the room

	String introPrompt; // The string that is printed when the user first enters the room

	public void printActions() {
		for (int i = 0; i < roomActions.size(); i++) {
			
			System.out.print("[" + i + "] "); 
			roomActions.get(i).print();

		}
	}
}