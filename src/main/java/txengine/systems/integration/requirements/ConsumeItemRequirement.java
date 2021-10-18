package txengine.systems.integration.requirements;

import txengine.main.Manager;
import txengine.systems.integration.Requirement;
import txengine.ui.LogUtils;
import txengine.util.Utils;

import java.util.ArrayList;
import java.util.List;

// Is only satisfied when the user
// - Has the listed items
// - Agrees to "consume" them
public class ConsumeItemRequirement extends Requirement {

    Boolean persist = false;
    boolean met = false;

    public ConsumeItemRequirement() {
        try {
            persist = Boolean.parseBoolean(properties[0]);
            LogUtils.info("Successfully set persistence to " + persist, "ConsumeItemRequirement::()");
        } catch (Exception ignored) {
            LogUtils.info("Failed to set persistence", "ConsumeItemRequirement::()");
        }
    }

    public ConsumeItemRequirement(String[] properties) {
        super(properties);
    }

    public ConsumeItemRequirement(Requirement requirement) {
        super(requirement);
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
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("You must have ");

        for (int id : Utils.toInts(List.of(properties))) sb.append(Manager.itemHashMap.get(id).getName()).append(",");
        sb.replace(sb.length()-1, sb.length(), " ");
        sb.append("in your inventory.");

        return sb.toString();
    }

    @Override
    public boolean met() {
        if (persist && met) {
            return true;
        } else {
            LogUtils.info("Persist: " + persist, "ConsumeItemRequirement::met");
            LogUtils.info("Met: " + met, "ConsumeItemRequirement::met");
        }
        List<Integer> ids = Utils.toInts(List.of(properties)); //  Track the parsed IDs
        if (Manager.player.getInventory().getItemIDs().containsAll(ids)) {
            StringBuilder names = new StringBuilder();
            for (int id : ids) {
                names.append(", ").append(Manager.itemHashMap.get(id).getName());
            }
            System.out.println("Are you sure you want to use " + names.toString() + "?");
            if (LogUtils.getAffirmative()) {
                for (int id : ids) Manager.player.getInventory().consumeQuantity(id, 1);
                met = true;
                return true;
            }
        }
        return false;
    }
}
