package txengine.systems.integration;

import java.util.Collection;
import java.util.List;

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
    public abstract String toString();


    /* Static Methods */

    // Returns true if all the requirements in the collection are met.
    public static boolean allMet(Collection<Requirement> requirements) {
        return requirements.stream().allMatch(Requirement::met);
    }

    // Returns a collection of requirements as a list of formatted strings (usually for printing)
    public static List<String> asStrings(Collection<Requirement> requirements) {
        return requirements.stream().map(Requirement::toString).toList();
    }

    public abstract boolean met();

    /* Getters and Setters */
}
