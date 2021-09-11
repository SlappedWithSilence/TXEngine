package txengine.integration.requirements;

import txengine.integration.Requirement;
import txengine.main.Manager;

import java.util.Arrays;
import java.util.Collection;

// A FlagRequirement is met when all the flags inside its properties array
// - exist
// - are set to true
public class FlagRequirement extends Requirement {

    public FlagRequirement() {
        super();

    }

    public FlagRequirement(String[] properties) {
        super(properties);

    }

    public FlagRequirement(Collection<String> flagNames) {
        this.properties = new String[flagNames.size()];
        this.properties = flagNames.toArray(properties);
    }

    public FlagRequirement(FlagRequirement flagRequirement) {
        super(flagRequirement);
    }

    @Override
    public String[] getProperties() {
        return properties;
    }

    @Override
    public void setProperties(String[] properties) {
        this.properties = properties;
    }

    @Override
    public boolean met() {
        return Arrays.stream(properties).allMatch(flagName -> Manager.flagManager.hasFlag(flagName) && Manager.flagManager.getFlag(flagName));
    }
}
