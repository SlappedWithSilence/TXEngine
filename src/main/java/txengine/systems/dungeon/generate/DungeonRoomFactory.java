package txengine.systems.dungeon.generate;

import txengine.structures.CanvasNode;
import txengine.structures.Coordinate;
import txengine.systems.dungeon.Dungeon;
import txengine.systems.dungeon.DungeonGimmick;
import txengine.systems.dungeon.DungeonRoom;
import txengine.systems.integration.Requirement;
import txengine.systems.integration.requirements.ConsumeItemRequirement;

import java.util.List;

public class DungeonRoomFactory {

    public static DungeonRoom build(final Dungeon owner, Coordinate coordinate, DungeonGimmick gimmick, CanvasNode.Direction returnDoor, List<CanvasNode.Direction> doors) {
        DungeonRoom dr = new DungeonRoom(owner, coordinate);
        dr.addGimmick(gimmick);

        if (doors != null) {
            for (CanvasNode.Direction door : doors) dr.addDoor(door);
        }

        if (returnDoor != null) dr.addUnlockedDoor(returnDoor);

        return dr;
    }


    public static Requirement buildLock(final Dungeon owner) {
        ConsumeItemRequirement r = new ConsumeItemRequirement();
        r.setProperties(new String[] {"true",""+owner.getGimmickKeyID()});
        return r;
    }
}
