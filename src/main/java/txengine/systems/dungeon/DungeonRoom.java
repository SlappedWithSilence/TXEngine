package txengine.systems.dungeon;

import txengine.structures.Canvas;
import txengine.structures.CanvasNode;
import txengine.structures.Coordinate;
import txengine.systems.room.action.Action;
import txengine.systems.room.action.actions.MoveAction;
import txengine.ui.LogUtils;
import txengine.ui.component.Components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class DungeonMove extends Action {
    Coordinate targetRoom;

    public DungeonMove(Coordinate targetRoom) {
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

public class DungeonRoom extends CanvasNode {
    String name;
    List<Action> roomActions;
    final Dungeon owner;

    DungeonRoom(Dungeon owner, List<Action> roomActions, Coordinate coordinate) {
        super.coordinates = coordinate;
        this.owner = owner;
        this.roomActions = roomActions;
        this.roomActions.addAll(Dungeon.getDefaultActions());
    }

    public DungeonRoom(Dungeon owner, Coordinate rootCoordinate) {
        roomActions = new ArrayList<>();
        this.owner = owner;
        super.coordinates = rootCoordinate;
    }

    public Coordinate enter() {
        while(true) {
            System.out.println("What would you like to do?");
            Components.numberedList(roomActions.stream().map(Action::getMenuName).toList());

            int choice = LogUtils.getNumber(0,roomActions.size()-1);
            System.out.println(roomActions.get(choice).getText());

            if (roomActions.get(choice) instanceof DungeonMove) {
                return ((DungeonMove) roomActions.get(choice)).targetRoom;
            }

            roomActions.get(choice).perform();
        }
    }

    @Override
    public void addDoor(Direction d) {
        if (getDoors() == null) setDoors(new HashSet<>());
        if (!super.getDoors().contains(d)) {
            roomActions.add(new DungeonMove(to(d)));
        }
    }
}
