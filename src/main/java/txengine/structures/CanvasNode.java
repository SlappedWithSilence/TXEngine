package txengine.structures;

import java.util.List;
import java.util.Set;


public class CanvasNode {

    public enum Direction {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    protected Coordinate coordinates;
    Set<Direction> doors;

    public CanvasNode() {
        coordinates = new Coordinate(0,0);
    }

    public CanvasNode(Coordinate coordinates) {
        this.coordinates = coordinates;
    }

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
}
