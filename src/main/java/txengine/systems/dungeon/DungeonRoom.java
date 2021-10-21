package txengine.systems.dungeon;

import txengine.io.CrashReporter;
import txengine.structures.CanvasNode;
import txengine.structures.Coordinate;
import txengine.systems.dungeon.generate.DungeonRoomFactory;
import txengine.systems.integration.Requirement;
import txengine.systems.integration.requirements.ConsumeItemRequirement;
import txengine.systems.room.action.Action;
import txengine.ui.LogUtils;
import txengine.ui.Out;
import txengine.ui.component.Components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DungeonRoom extends CanvasNode {
    String name;
    List<Action> roomActions;
    final Dungeon owner;
    DungeonGimmick gimmick;

    public DungeonRoom(Dungeon owner, Coordinate coordinate) {
        super.coordinates = coordinate;
        this.owner = owner;
        this.roomActions = new ArrayList<>();
        this.roomActions.addAll(owner.getDefaultActions());
    }

    public Coordinate enter() {
        while(!owner.isForceExit()) {
            System.out.println("What would you like to do?");
            Components.numberedList(roomActions.stream().map(Action::getMenuName).toList());

            int choice = LogUtils.getNumber(0,roomActions.size()-1); // Get user choice

            Out.error(roomActions.get(choice).getRequirements().size() + " total requirements");

            if (!Requirement.allMet(roomActions.get(choice).getRequirements())) { // Handle any requirements not being met
                System.out.println("You can't do that!");
                for (Requirement r : roomActions.get(choice).getRequirements()) System.out.println(r);
            } else if (roomActions.get(choice).isHidden()) { // Handle the choice being "hidden"
                System.out.println("You can't do that again.");
            } else { // Execute the user's choice
                System.out.println(roomActions.get(choice).getText());

                if (roomActions.get(choice) instanceof DungeonMove) { // Handle an attempt to change rooms
                    return ((DungeonMove) roomActions.get(choice)).targetRoom;
                }

                roomActions.get(choice).perform();
            }
        }
        return null;
    }

    @Override
    public void addDoor(Direction d) {
        if (gimmick == null || gimmick.type == DungeonGimmick.Type.UNLOCKED) {
            addUnlockedDoor(d);
        } else {
            addLockedDoor(d);
        }
    }

    // Adds a door that locks itself
    public void addLockedDoor(Direction d) {
        if (getDoors() == null) setDoors(new HashSet<>());
        if (!doors.contains(d)) {
            DungeonMove dm = new DungeonMove(owner, to(d)); // Create a new door
            dm.addRequirement(DungeonRoomFactory.buildLock(owner)); // Add a requirement to the door
            roomActions.add(dm); // Add the door to the room
            doors.add(d); // Record that a new door was added to the room
        }
    }

    public void addUnlockedDoor(Direction d) {
        if (getDoors() == null) setDoors(new HashSet<>());
        if (!doors.contains(d)) {
            roomActions.add(new DungeonMove(owner, to(d)));
            doors.add(d);
        }
    }

    // Add a new gimmick to the room
    public void addGimmick(DungeonGimmick dg) {
        gimmick = dg;
        if (gimmick == null) {
            Out.error(gimmick.getClass().getSimpleName() + " returned null instead of List<Action>!","DungeonRoom::addGimmick");
            CrashReporter.getInstance().clear();
            CrashReporter.getInstance().append("Crashed while adding a gimmick!\n");
            CrashReporter.getInstance().append("ClassName: " + dg.getClass().getSimpleName() + "\n");
            CrashReporter.getInstance().append("in Dungeon\n");
            CrashReporter.getInstance().append("seed: " + owner.getSeed() + "\n");
            CrashReporter.getInstance().append("key id: " + owner.gimmickKeyID + "\n");
            CrashReporter.getInstance().write();
            CrashReporter.getInstance().clear();
        } else {
            roomActions.addAll(gimmick.get());
        }

        if (gimmick.type == DungeonGimmick.Type.LOCKED) {
            ConsumeItemRequirement r = new ConsumeItemRequirement();
            r.setProperties(new String[] {"-3"});
            for (final Action a : roomActions)  {
                if (a instanceof DungeonMove) a.addRequirement(r);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Action> getRoomActions() {
        return roomActions;
    }

    public void setRoomActions(List<Action> roomActions) {
        this.roomActions = roomActions;
    }

    public Dungeon getOwner() {
        return owner;
    }

    public DungeonGimmick getGimmick() {
        return gimmick;
    }

    public void setGimmick(DungeonGimmick gimmick) {
        this.gimmick = gimmick;
    }
}
