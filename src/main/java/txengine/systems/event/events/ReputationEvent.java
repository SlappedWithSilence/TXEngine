package txengine.systems.event.events;

import txengine.main.Manager;
import txengine.systems.event.Event;
import txengine.systems.reputation.Faction;
import txengine.ui.LogUtils;
import txengine.ui.Out;

public class ReputationEvent extends Event {

    int xpGiven = 0;
    String factionName;

    @Override
    protected void execute() {
        factionName = getProperties()[0];

        try {
            xpGiven = Integer.parseInt(getProperties()[1]);
            Manager.factionManager.changeReputation(factionName, xpGiven);
        } catch (Exception e) {
            Faction.Growth growth = null;
            Faction.GrowthMode mode = null;
            try {
                growth = Faction.Growth.valueOf(getProperties()[1]);
            } catch (IllegalArgumentException ire) {
                Out.error("Expected a Faction.Growth value, got " + getProperties()[1] + " instead!","ReputationEvent");
            }

            try {
                mode = Faction.GrowthMode.valueOf(getProperties()[2]);
            } catch (IllegalArgumentException ire) {
                Out.error("Expected a Faction.GrowthMode value, got " + getProperties()[2] + " instead!","ReputationEvent");
            }

            xpGiven = Manager.factionManager.changeReputation(factionName, growth, mode);
        }
    }

    @Override
    protected String print() {
        String mod = "up";
        if (xpGiven < 0) mod = "down";
        return "Your " + factionName + " reputation went " + mod + " by " + xpGiven;
    }
}
