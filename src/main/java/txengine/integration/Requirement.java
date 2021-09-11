package txengine.integration;

// A Requirement is a configurable class that returns a true or false value
// through an arbitrary calculation using one or more configuration values
// stored in the properties array. Requirements are used in a variety of ways by the
// various systems in mckeken.systems
public abstract class Requirement implements Configurable {

    /* Constants */

    /* Member Variables */
    protected String[] properties;

    /* Constructors */
    public Requirement() {
        properties = new String[1];
    }

    public Requirement(String[] properties) {
        this.properties = properties;
    }

    public Requirement(Requirement requirement) {
        properties = requirement.getProperties();
    }

    /* Member Methods */

    public abstract boolean met();

    /* Getters and Setters */
}
