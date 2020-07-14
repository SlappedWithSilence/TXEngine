package mckeken.room;

import mckeken.color.*;

public class Action {
	protected String name; // The name of the Action. This is displayed in the main prompt of the room that holds the action
	protected String text; // The text that is printed when action is selected


	// Determines whether or not the Action can be executed. Each action should override this function as needed.
	public boolean isEnabled() {
		return true;
	}

	// Performs the action
	public void perform() {
		// Do something here
	}

	public boolean print() {
		if (isEnabled()) ColorConsole.d(name + "\n");
		return isEnabled();
	}
}