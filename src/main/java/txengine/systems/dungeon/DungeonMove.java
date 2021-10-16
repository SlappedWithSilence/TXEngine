package txengine.systems.dungeon;

import txengine.structures.Coordinate;

public class DungeonMove extends DungeonAction{
    Coordinate targetRoom;

    public DungeonMove(Dungeon owner, Coordinate targetRoom) {
        super(owner);
        this.targetRoom = targetRoom;
        menuName = "Move to " + targetRoom;
    }

    public Coordinate getTargetRoom() {
        return targetRoom;
    }

    public void setTargetRoom(Coordinate targetRoom) {
        this.targetRoom = targetRoom;
    }

    @Override
    public int perform() {
        return 0;
    }
}
