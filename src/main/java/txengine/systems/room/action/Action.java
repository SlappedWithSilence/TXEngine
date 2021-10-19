package txengine.systems.room.action;

import txengine.ui.color.*;
import txengine.systems.integration.Requirement;

import java.util.ArrayList;
import java.util.List;

public abstract class Action {
	protected String[] properties; // A raw list of config values. This is the array that stores values read from the JSON config files. These strings may need to be cast to other types in order to be used.
	protected String menuName; // The name of the Action. This is displayed in the main prompt of the room that holds the action
	protected String text; // The text that is printed when action is selected
	protected List<Requirement> requirements;
	protected boolean hidden;
	protected int unhideIndex = -1;	 // The other Action in the room this Action is in that should be un-hidden if this Action succeeds
	protected boolean hideAfterUse;

	/* Constructors */
	public Action() {
		properties = new String[0];
		menuName = "Default Menu Name";
		text = "";
		requirements = new ArrayList<>();
		hidden = false;
		hideAfterUse = false;
	}

	public Action(String menuName, String text, String[] properties, boolean hidden, int unhideIndex, boolean hideAfterUse, List<Requirement> requirements) {
		this.menuName = menuName;
		this.hidden = hidden;
		this.properties = properties;
		this.text = text;
		this.unhideIndex = unhideIndex;
		this.hideAfterUse = hideAfterUse;
		this.requirements = requirements;
	}

	public Action(Action copyFrom) {
		menuName = copyFrom.menuName;
		hidden = copyFrom.hidden;
		properties = copyFrom.properties;
		text = copyFrom.text;
		unhideIndex = copyFrom.unhideIndex;
		hideAfterUse = copyFrom.hideAfterUse;
		this.requirements = new ArrayList<>(copyFrom.getRequirements());
	}

	/* Abstract Member Functions */
	// Performs the action. In MOST CASES, properties should only be accessed in this function. Manipulating properties in the constructors wastes memory.
	public abstract int perform();


	/* Concrete Member Functions */

	// Returns the index of the Action that should be enabled if this action succeeds.
	public int enableOnComplete() {
		return unhideIndex;
	}

	// Determines whether the Action can be executed. Each action should override this function as needed.
	public boolean isHidden() {
		return hidden;
	}

	public boolean print() {
		if (!isHidden()) ColorConsole.d(menuName + "\n", false);
		return isHidden();
	}

	public void addRequirement(Requirement r) {
		if (r != null) requirements.add(r);
	}


	@Override
	public String toString() {
		return menuName;
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

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public int getUnhideIndex() {
		return unhideIndex;
	}

	public void setUnhideIndex(int unhideIndex) {
		this.unhideIndex = unhideIndex;
	}

	public List<Requirement> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<Requirement> requirements) {
		this.requirements = requirements;
	}

	public boolean isHideAfterUse() {
		return hideAfterUse;
	}

	public void setHideAfterUse(boolean hideAfterUse) {
		this.hideAfterUse = hideAfterUse;
	}
}
