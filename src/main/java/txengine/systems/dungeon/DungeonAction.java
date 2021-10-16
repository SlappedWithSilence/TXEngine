package txengine.systems.dungeon;

import txengine.systems.room.action.Action;
import txengine.systems.room.action.actions.AbilitySummaryAction;

public abstract class DungeonAction extends Action {
    final Dungeon owner;

    public DungeonAction(Dungeon owner) {
        this.owner = owner;
    }

    public Dungeon getOwner() {
        return owner;
    }
}
