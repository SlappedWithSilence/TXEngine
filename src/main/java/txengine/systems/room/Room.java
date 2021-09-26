package txengine.systems.room;

import txengine.systems.integration.Requirement;
import txengine.ui.component.Components;
import txengine.ui.LogUtils;
import txengine.systems.room.action.Action;
import txengine.systems.room.action.actions.MoveAction;

import java.util.ArrayList;
import java.util.List;

public class Room {

	// *** Class Variables ***//
	int id;      // The room's id. This is used to track which room the user is in as well as to map the user into new rooms. All IDs must be unique.
	String name; // The room's name. Used for visual prompting.
	String text; // The text that is printed when you enter the room
	ArrayList<Action> onFirstEnterActions = new ArrayList<>(); // A list of actions to be performed series the first time a user enters a room
	ArrayList<Action> roomActions = new ArrayList<>(); // A list of things the user can do in the room

	String introPrompt; // The string that is printed when the user first enters the room

	public Room() {

	}

	public Room(int id, String name, String text) {
		this.id = id;
		this.name = name;
		this.text = text;
	}

	public void printActions() {

		Components.numberedList(roomActions.stream().filter(action -> !action.isHidden()).map(Action::toString).toList());

	}

	public void enter() {

		System.out.println(text);

		if (!RoomManager.getVisitedRooms().contains(id)) { // If this room hasn't been entered before
			for(Action a : onFirstEnterActions) a.perform(); // Perform all the first-time-only actions
		}

		// Loop until the user performs a MoveAction
		while (true) {
			printActions();
			int userSelection = LogUtils.getNumber(0, getVisibleActions().size()-1);

			// Check if the user can actually perform the action, then if they can, execute it.
			if (Requirement.allMet(getVisibleActions().get(userSelection).getRequirements())) {
				System.out.println(getVisibleActions().get(userSelection).getText());
				int unhide = getVisibleActions().get(userSelection).perform(); // Calculate the index of the action to unhide (if any)
				if (unhide >= 0) roomActions.get(unhide).setHidden(false); // Enable the Action that was passed through the last performed Action.

				if (getVisibleActions().get(userSelection) instanceof MoveAction) break; // If the user needs to move, do so

				if (getVisibleActions().get(userSelection).isHideAfterUse()) { // Hide the action after use if necessary
					getVisibleActions().get(userSelection).setHidden(true);
				}
			} else {
				System.out.println("You can't do that right now!");
				getVisibleActions().get(userSelection).getRequirements().forEach(r -> System.out.println(r.toString()));
			}
		}
	}

	public List<Action> getVisibleActions() {
		return roomActions.stream().filter(action -> !action.isHidden()).toList();
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