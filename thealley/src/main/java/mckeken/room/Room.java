package mckeken.room;

import mckeken.io.LogUtils;
import mckeken.room.action.Action;
import mckeken.room.action.actions.MoveAction;

import java.util.ArrayList;

public class Room {

	// *** Class Varaibles ***//
	int id;      // The room's id. This is used to track which room the user is in as well as to map the user into new rooms. All IDs must be unique.
	String name; // The room's name. Used for visual prompting.
	String text; // The text that is printed when you enter the room
	ArrayList<Action> onFirstEnterActions = new ArrayList<Action>(); // A list of actions to be performed series the first time a user enters a room
	ArrayList<Action> roomActions = new ArrayList<Action>(); // A list of things the user can do in the room

	String introPrompt; // The string that is printed when the user first enters the room


	public Room() {

	}
	public Room(int id, String name, String text) {
		this.id = id;
		this.name = name;
		this.text = text;
	}

	public void printActions() {
		for (int i = 0; i < roomActions.size(); i++) {

			if (roomActions.get(i).isEnabled() ) System.out.print("[" + i + "] ");
			roomActions.get(i).print();

		}
	}

	public void enter() {
		boolean stay = true;

		System.out.println(text);

		if (!RoomManager.getVisitedRooms().contains(id)) { // If this room hasn't been entered before
			for(Action a : onFirstEnterActions) a.perform(); // Perform all the first-time-only actions
		}


		// Loop until the user performs a MoveAction
		while (stay) {
			printActions();
			int userSelection = LogUtils.getNumber(0, roomActions.size());

			int enableNext = roomActions.get(userSelection).perform();
			if (enableNext >= 0) roomActions.get(enableNext).setEnabled(true); // Enable the Action that was passed through the last performed Action.

			if (roomActions.get(userSelection) instanceof MoveAction) break;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Action> getOnFirstEnterActions() {
		return onFirstEnterActions;
	}

	public void setOnFirstEnterActions(ArrayList<Action> onFirstEnterActions) {
		this.onFirstEnterActions = onFirstEnterActions;
	}

	public ArrayList<Action> getRoomActions() {
		return roomActions;
	}

	public void setRoomActions(ArrayList<Action> roomActions) {
		this.roomActions = roomActions;
	}

	public String getIntroPrompt() {
		return introPrompt;
	}

	public void setIntroPrompt(String introPrompt) {
		this.introPrompt = introPrompt;
	}
}