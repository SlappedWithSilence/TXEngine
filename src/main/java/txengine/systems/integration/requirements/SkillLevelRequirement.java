package txengine.systems.integration.requirements;

import txengine.main.Manager;
import txengine.structures.Pair;
import txengine.systems.integration.Requirement;
import txengine.util.Utils;

import java.util.List;

public class SkillLevelRequirement extends Requirement {
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
        return null;
    }

    @Override
    public boolean met() {
        List<Pair<String, Integer>> data = Utils.parseStringIntPairs(List.of(properties)); // Convert properties into workable Pairs
        for (Pair<String, Integer> p : data) { // For each Pair
            if (Manager.skillManager.getSkillLevel(p.getKey()) < p.getValue()) return false; // If any level of any skill is too low, return false
        }
        return true;
    }
}
