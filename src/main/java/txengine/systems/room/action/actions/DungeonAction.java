package txengine.systems.room.action.actions;

import txengine.io.LoadUtils;
import txengine.main.Manager;
import txengine.structures.Graph;
import txengine.structures.Pair;
import txengine.systems.combat.CombatEntity;
import txengine.systems.dungeon.Dungeon;
import txengine.systems.room.Room;
import txengine.systems.room.action.Action;
import txengine.util.Utils;

import java.util.*;

public class DungeonAction extends Action {


    public DungeonAction() {

    }

    @Override
    public int perform() {

        Dungeon d = new Dungeon();

        if (d.enter()) {
            for (Pair<Integer, Integer> loot : d.getRewards()) {
                Manager.player.getInventory().addItem(loot.getKey(), loot.getValue());
            }
        } else {

        }

        return unhideIndex;
    }
}
