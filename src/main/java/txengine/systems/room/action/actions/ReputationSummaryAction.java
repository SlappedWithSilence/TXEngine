package txengine.systems.room.action.actions;

import txengine.main.Manager;
import txengine.systems.room.action.Action;
import txengine.ui.component.Components;

public class ReputationSummaryAction extends Action {

    @Override
    public int perform() {

        Components.verticalTabList(Manager.factionManager.getFactions().stream().map(f -> (Components.Tabable) f).toList());

        return unhideIndex;
    }
}
