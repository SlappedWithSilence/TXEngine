package txengine.systems.reputation;

import txengine.ui.LogUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

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
    public void changeReputation(String factionName, int reputationChange) {
        if (!factionMap.containsKey(factionName)) {
            LogUtils.error("No Faction" + factionName + " exists!","FactionManager");
            throw new NoSuchElementException();
        }
        factionMap.get(factionName).changeXP(reputationChange);
    }

}
