package txengine.systems.dungeon;

import txengine.systems.room.action.Action;

import java.util.List;

public abstract class DungeonGimmick {

    protected final Dungeon owner;

    public DungeonGimmick(Dungeon owner) {
        this.owner = owner;
    }

    public abstract List<Action> get();

}
