package txengine.systems.integration.requirements;

import txengine.systems.integration.Requirement;
import txengine.main.Manager;

// A LevelRequirement is met when the player's level is greater-than or equal-to the integer is properties[0]
public class LevelRequirement extends Requirement {

    @Override
    public String[] getProperties() {
        return properties;
    }

    @Override
    public void setProperties(String[] properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "You must be at least level " + properties[0] +".";
    }

    @Override
    public boolean met() {
        return Manager.player.getLevel() >= Integer.parseInt(properties[0]);
    }
}
