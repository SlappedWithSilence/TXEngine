package mckeken.item.effect;

import mckeken.color.ColorConsole;

public abstract class Effect {

	final static int DEFAULT_NUM_PROPERTY = 3;

	protected int numProperties;
	protected Integer[] properties;

	//*** Constructors ***//

	// Default constructor
	public Effect() {
		numProperties = DEFAULT_NUM_PROPERTY;
		properties = new Integer[numProperties];
	}

	// Empty properties constructor. Useful when you don't already know the values of a property, but you do know how many there are.
	public Effect(int numProperties) {
		this.numProperties = numProperties;
		properties = new Integer[numProperties];
	}

	// Pre-existing properties constructor. Useful when loading from disk, creating an anonymous Effect
	public Effect(Integer[] properties) {
		this.numProperties = properties.length;
		this.properties = properties;
	}

	// *** Concrete Member Functions ***//

	// Allows for external effect configuration. Useful for loading an effect from disk.
	public void setProperty(int index, int value) {
		if (index >= numProperties) { // Checks for a valid index
			ColorConsole.e("Invalid property index for effect configuration! This is an error, please report it on https://github.com/TopperMcKek/TheAlley");
		} else {
			properties[index] = value;
		}
	}

	public void setProperties(Integer[] props) {
		properties = props;
		numProperties = props.length;
	}

	//*** Abstract Member Functions ***//

	// Performs a function on the player or world
	public abstract void perform();

}
