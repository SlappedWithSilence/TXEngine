package txengine.systems.dungeon;

import txengine.systems.integration.Requirement;
import txengine.systems.room.action.Action;
import txengine.systems.room.action.actions.AbilitySummaryAction;

import java.util.ArrayList;
import java.util.List;

public abstract class DungeonAction extends Action {
    final Dungeon owner;

    public DungeonAction(Dungeon owner) {
        super();
        this.owner = owner;
        this.requirements = new ArrayList<>();
    }

    public DungeonAction(Dungeon owner, List<Requirement>requirements) {
        super();
        this.owner = owner;
        for (Requirement r : requirements) addRequirement(r);
    }

    public Dungeon getOwner() {
        return owner;
    }
}
