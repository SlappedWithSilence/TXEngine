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
        List<Pair<String, Integer>> data = Utils.parseStringIntPairs(List.of(properties));
        for (Pair<String, Integer> p : data) {
            if (Manager.skillManager.getSkillLevel(p.getKey()) < p.getValue()) return false;
        }
        return true;
    }
}
