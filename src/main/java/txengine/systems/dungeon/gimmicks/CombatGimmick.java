package txengine.systems.dungeon.gimmicks;

import txengine.main.Manager;
import txengine.systems.combat.CombatEntity;
import txengine.systems.dungeon.Dungeon;
import txengine.systems.dungeon.DungeonGimmick;
import txengine.systems.room.action.Action;
import txengine.systems.room.action.actions.CombatAction;
import txengine.util.Utils;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class CombatGimmick extends DungeonGimmick {


    public CombatGimmick(Dungeon owner) {
        super(owner);
    }

    @Override
    public List<Action> get() {
        List<Action> actions = new ArrayList<>();

        // Get random enemy
        Integer randomEntityID = Utils.selectRandom(owner.getEnemyPool(), owner.getRand());
        ArrayList<Integer> hostiles = new ArrayList<>();
        hostiles.add(randomEntityID);

        // Get random loot
        // TODO: Fetch loot from Dungeon loot pool
        ArrayList<AbstractMap.SimpleEntry<Integer, Integer>> loot = new ArrayList<>();
        CombatAction a = new CombatAction(new ArrayList<>(), hostiles, loot);

        actions.add(a);
        return actions;
    }
}
