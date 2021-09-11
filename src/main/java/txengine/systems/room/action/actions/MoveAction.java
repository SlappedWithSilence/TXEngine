package txengine.systems.room.action.actions;

import txengine.main.Manager;
import txengine.systems.room.action.Action;

public class MoveAction extends Action {

    @Override
    public int perform() {

        Manager.player.setLocation(Integer.parseInt(properties[0]));

        return unhideIndex;
    }
}
