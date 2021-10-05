package txengine.systems.room;

import com.rits.cloning.Cloner;
import txengine.main.Manager;
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
	ArrayList<Action> onFirstEnterActions; // A list of actions to be performed series the first time a user enters a room
	ArrayList<Action> roomActions; // A list of things the user can do in the room
	String introPrompt; // The string that is printed when the user first enters the room
	boolean ignoreDefaultActions;

	private static List<Action> defaultActions; // Whenever adding default actions to roomActions list, must add them *last* to avoid breaking unhide indexes

	// Constructors

	public Room() {
		id = -1;
		name = "Generic Room";
		text = "Generic opener text";
		onFirstEnterActions = new ArrayList<>();
		roomActions = new ArrayList<>();
		ignoreDefaultActions = false;
	}

	public Room(int id, String name, String text) {
		this.id = id;
		this.name = name;
		this.text = text;
		onFirstEnterActions = new ArrayList<>();
		roomActions = new ArrayList<>();
		ignoreDefaultActions = false;
	}

	// Public Methods

	public void printActions() {

		Components.numberedList(roomActions.stream().filter(action -> !action.isHidden()).map(Action::toString).toList());

	}

	public void enter() {

		System.out.println(text);

		if (!Manager.roomManager.getVisitedRooms().contains(id)) { // If this room hasn't been entered before
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

	// Helper Methods

	private void addDefaultActions() {
		if (ignoreDefaultActions) {
			LogUtils.info("Ignoring default actions...",name);
			return;
		}

		if (defaultActions == null || defaultActions.size() == 0) {
			LogUtils.warn("ignoreDefaultActions set to false, but no Default Actions found!", "Room: " + name);
			return;
		}

		for (Action a : defaultActions) {
			Cloner cloner = new Cloner();
			if (a != null) roomActions.add(cloner.deepClone(a));
		}
	}

	// Accessor Methods

	public static List<Action> getDefaultActions() {
		return defaultActions;
	}

	public static void setDefaultActions(List<Action> actions) {
		defaultActions = actions;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isIgnoreDefaultActions() {
		return ignoreDefaultActions;
	}

	public void setIgnoreDefaultActions(boolean ignoreDefaultActions) {
		this.ignoreDefaultActions = ignoreDefaultActions;
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
		if (!ignoreDefaultActions) addDefaultActions(); // Must add default actions *last* to avoid breaking unhide indexes
	}

	public String getIntroPrompt() {
		return introPrompt;
	}

	public void setIntroPrompt(String introPrompt) {
		this.introPrompt = introPrompt;
	}

	public List<Integer> getHiddenActions() {
		int offset = 0;
		if (!ignoreDefaultActions) offset = Room.defaultActions.size();

		List<Integer> hiddenIndexes = new ArrayList<>();

		for (int i = 0; i < roomActions.size() - offset; i++) {
			if (roomActions.get(i).isHidden()) hiddenIndexes.add(i);
		}

		return hiddenIndexes;
	}
}