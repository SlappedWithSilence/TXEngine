package txengine.systems.dungeon.gimmicks;

import txengine.main.Manager;
import txengine.systems.combat.CombatEntity;
import txengine.systems.dungeon.Dungeon;
import txengine.systems.dungeon.DungeonGimmick;
import txengine.systems.room.action.Action;
import txengine.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class CombatGimmick extends DungeonGimmick {


    public CombatGimmick(Dungeon owner) {
        super(owner);
    }

    @Override
    public List<Action> get() {
        List<Action> actions = new ArrayList<>();

        int randomEntityID = Utils.selectRandom(owner.getEnemyPool(), owner.getRand());
        CombatEntity ce = new CombatEntity();

        return actions;
    }
}
