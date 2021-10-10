package txengine.systems.room.action.actions;

import txengine.main.Manager;
import txengine.systems.reputation.Faction;
import txengine.systems.room.action.Action;
import txengine.ui.component.Components;

public class ReputationSummaryAction extends Action {

    @Override
    public int perform() {

        for (Faction f : Manager.factionManager.getFactions()) System.out.println(f.toString());

        return unhideIndex;
    }
}
