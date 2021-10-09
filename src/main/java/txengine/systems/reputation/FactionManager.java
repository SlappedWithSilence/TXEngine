package txengine.systems.reputation;

import txengine.ui.LogUtils;

import java.util.*;

public class FactionManager {

    /*** Member Variables ***/
    private HashMap<String, Faction> factionMap;

    /*** Constructors ***/
    public FactionManager(Collection<Faction> factions) {
        factions.forEach(faction -> factionMap.put(faction.name,faction));
    }

    public FactionManager(Map<String, Faction> factionMap) {
        this.factionMap = new HashMap<>(factionMap);
    }

    /*** Public Methods ***/
    public int changeReputation(String factionName, int reputationChange) {
        if (!factionMap.containsKey(factionName)) {
            LogUtils.error("No Faction" + factionName + " exists!","FactionManager");
            throw new NoSuchElementException();
        }

        return factionMap.get(factionName).changeXP(reputationChange);
    }

    public int changeReputation(String factionName, Faction.Growth growth, Faction.GrowthMode mode) {
        if (!factionMap.containsKey(factionName)) {
            LogUtils.error("No Faction" + factionName + " exists!","FactionManager");
            throw new NoSuchElementException();
        }

        return factionMap.get(factionName).changeXP(growth, mode);
    }

    public boolean exists(String factionName) {
        return factionMap.containsKey(factionName);
    }

    public final List<Faction> getFactions() {
        return new ArrayList<>(factionMap.values());
    }

    public Faction getFaction(String factionName) {
        return factionMap.get(factionName);
    }

}
