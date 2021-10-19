package txengine.systems.dungeon.gimmicks;

import txengine.systems.dungeon.Dungeon;
import txengine.systems.dungeon.DungeonGimmick;
import txengine.systems.room.action.Action;

import java.util.ArrayList;
import java.util.List;

public class EmptyGimmick extends DungeonGimmick {
    public EmptyGimmick(Dungeon owner) {
        super(owner);
        type = Type.UNLOCKED;
    }

    @Override
    public List<Action> get() {
        return new ArrayList<>();
    }
}
