package txengine.systems.room.action.actions;

import txengine.io.LoadUtils;
import txengine.structures.Graph;
import txengine.systems.combat.CombatEntity;
import txengine.systems.room.Room;
import txengine.systems.room.action.Action;
import txengine.util.Utils;

import java.util.*;

public class DungeonAction extends Action {
    Graph<Room> dungeon;
    String seed;
    int[] enemyPool;
    List<AbstractMap.SimpleEntry<Integer, Integer>> clearRewards;
    int maximumLength;
    Random rand;

    public DungeonAction() {

    }

    @Override
    public int perform() {
        rand = new Random(seed.hashCode());
        enemyPool = Utils.parseInts(properties[0], ",");
        clearRewards = Utils.parseIntPairs(properties[1]);
        maximumLength = Integer.parseInt(properties[2]);
        dungeon = new Graph<>();


        return unhideIndex;
    }

    private void generate() {

    }

    private void generateRoot() {
        Room root = new Room();

    }
}
