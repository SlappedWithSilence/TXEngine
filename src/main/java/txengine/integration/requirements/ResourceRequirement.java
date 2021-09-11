package txengine.integration.requirements;

import txengine.integration.Requirement;
import txengine.main.Manager;
import txengine.util.Utils;

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
    public boolean met() {
        return Manager.player.getResourceManager().testResource(Utils.parseStringIntPairs(List.of(properties)));
    }
}
