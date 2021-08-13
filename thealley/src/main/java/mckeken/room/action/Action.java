package mckeken.room.action;

import mckeken.color.*;

public abstract class Action {
	protected String[] properties; // A raw list of config values. This is the array that stores values read from the JSON config files. These strings may need to be cast to other types in order to be used.
	protected String menuName; // The name of the Action. This is displayed in the main prompt of the room that holds the action
	protected String text; // The text that is printed when action is selected
	protected boolean enabled = true; // Whether or not the user is able to use this action.
	protected int numProperties = 3; // The expected number of properties
	protected int unlockIndex = -1;	 // The other Action in the room this Action is in that should be unlocked if this Action succeeds

	/* Constructors */
	public Action() {

	}

	public Action(String menuName, String text, String[] properties, boolean enabled, int unlockIndex) {
		this.menuName = menuName;
		this.enabled = enabled;
		this.properties = properties;
		this.text = text;
		this.unlockIndex = unlockIndex;
	}

	public Action(String menuName, String text, String[] properties, boolean enabled, int unlockIndex, int numProperties) {
		this.menuName = menuName;
		this.enabled = enabled;
		this.properties = properties;
		this.text = text;
		this.numProperties = numProperties;
		this.unlockIndex = unlockIndex;
	}


	/* Abstract Member Functions */
	// Performs the action. In MOST CASES, properties should only be accessed in this function. Manipulating properties in the constructors wastes memory.
	public abstract int perform();



	/* Concrete Member Functions */

	// Returns the index of the Action that should be enabled if this action succeeds.
	public int enableOnComplete() {
		return unlockIndex;
	}

	// Determines whether or not the Action can be executed. Each action should override this function as needed.
	public boolean isEnabled() {
		return enabled;
	}

	public boolean print() {
		if (isEnabled()) ColorConsole.d(menuName + "\n", false);
		return isEnabled();
	}

	public String[] getProperties() {
		return properties;
	}

	public void setProperties(String[] properties) {
		this.properties = properties;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getUnlockIndex() {
		return unlockIndex;
	}

	public void setUnlockIndex(int unlockIndex) {
		this.unlockIndex = unlockIndex;
	}
}
