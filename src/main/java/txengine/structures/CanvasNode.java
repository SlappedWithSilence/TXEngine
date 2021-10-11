package txengine.structures;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CanvasNode {

    // Inner Classes
    public enum Direction {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    // Member Variables
    protected Coordinate coordinates;
    Set<Direction> doors;

    // Constructors
    public CanvasNode() {
        coordinates = new Coordinate(0,0);
        doors = new HashSet<>();
    }

    public CanvasNode(Coordinate coordinates) {

        this.coordinates = coordinates;
    }

    // Member Methods
    public Coordinate north() {
        Coordinate c = new Coordinate(coordinates.x,coordinates.y-1);
        if (c.x < 0 || c.y < 0) return null;
        return c;
    }
    public Coordinate south() {
        Coordinate c = new Coordinate(coordinates.x,coordinates.y+1);
        if (c.x < 0 || c.y < 0) return null;
        return c;
    }
    public Coordinate east() {
        Coordinate c = new Coordinate(coordinates.x+1,coordinates.y);
        if (c.x < 0 || c.y < 0) return null;
        return c;

    }
    public Coordinate west() {
        Coordinate c = new Coordinate(coordinates.x-1,coordinates.y);
        if (c.x < 0 || c.y < 0) return null;
        return c;

    }
    public Coordinate self() {
        return coordinates;
    }

    public void addDoor(Direction d) {
        doors.add(d);
    }

    public Coordinate to(Direction d) {
        switch (d) {
            case EAST -> {
                return east();
            }
            case WEST -> {
                return west();
            }
            case NORTH -> {
                return north();
            }
            case SOUTH -> {
                return south();
            }
            default -> {
                return null;
            }
        }
    }

    // Accessors

    public Coordinate getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinate coordinates) {
        this.coordinates = coordinates;
    }

    public Set<Direction> getDoors() {
        return doors;
    }

    public void setDoors(Set<Direction> doors) {
        this.doors = doors;
    }
}
