package txengine.systems.dungeon;

import txengine.systems.integration.Requirement;

import java.util.List;

public class DungeonForfeit extends DungeonAction {

    public DungeonForfeit(Dungeon owner) {
        super(owner);
        menuName = "Surrender to the dungeon";
    }

    public DungeonForfeit(Dungeon owner, List<Requirement> requirements) {
        super(owner, requirements);
        menuName = "Surrender to the dungeon";
    }

    @Override
    public int perform() {
        owner.setForceExit(true);
        return unhideIndex;
    }
}
