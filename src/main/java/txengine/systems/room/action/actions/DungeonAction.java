package txengine.systems.room.action.actions;

import txengine.main.Manager;
import txengine.systems.dungeon.Dungeon;
import txengine.systems.room.action.Action;

public class DungeonAction extends Action {


    public DungeonAction() {

    }

    @Override
    public int perform() {

        Dungeon d = new Dungeon();

        if (d.enter()) {
            for (Integer loot : d.getRewards()) {
                Manager.player.getInventory().addItem(loot);
            }
        } else {
            System.out.println("You failed to conquer the dungeon and awake at its entrance.");
        }

        return unhideIndex;
    }
}
