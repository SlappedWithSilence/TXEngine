package txengine.systems.integration.requirements;

import txengine.systems.integration.Requirement;
import txengine.main.Manager;
import txengine.util.Utils;

import java.util.List;

// An ItemRequirement is met when the player's inventory contains all the items with the ids
// contained in the properties array.
public class ItemRequirement extends Requirement {
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

        sb.append("You must have ");

        for (int id : Utils.toInts(List.of(properties))) sb.append(Manager.itemHashMap.get(id)).append(",");
        sb.replace(sb.length()-1, sb.length()-1, " ");
        sb.append("in your inventory.");

        return sb.toString();
    }

    @Override
    public boolean met() {
        return Manager.player.getInventory().getItemIDs().containsAll(Utils.toInts(List.of(properties)));
    }
}
