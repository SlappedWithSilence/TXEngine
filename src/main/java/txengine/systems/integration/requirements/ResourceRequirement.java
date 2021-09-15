package txengine.systems.integration.requirements;

import txengine.systems.integration.Requirement;
import txengine.main.Manager;
import txengine.util.Utils;

import java.util.AbstractMap;
import java.util.List;

public class ResourceRequirement extends Requirement {
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
        StringBuilder sb = new StringBuilder();

        sb.append("You must have at least ");

        for (AbstractMap.SimpleEntry<String, Integer> pair : Utils.parseStringIntPairs(List.of(properties))) {
            sb.append(pair.getValue()).append(" ").append(pair.getKey());
            sb.append(", ");
        }

        sb.replace(sb.length() -2, sb.length() -1, ".");

        return sb.toString();
    }

    @Override
    public boolean met() {
        return Manager.player.getResourceManager().testResource(Utils.parseStringIntPairs(List.of(properties)));
    }
}
