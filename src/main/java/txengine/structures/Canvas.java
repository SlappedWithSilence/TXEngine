package txengine.structures;

import txengine.ui.LogUtils;

import java.io.DataInput;
import java.util.*;

public class Canvas {
    int length;
    int width;

    CanvasNode[][] nodes;


    public Canvas(int length, int width) {
        this.length = length;
        this.width = width;
        nodes = new CanvasNode[length][width];
    }

    public boolean put(int x, int y, CanvasNode node) {
        if (nodes[y][x] != null) return false;

        nodes[y][x] = node;
        nodes[y][x].setCoordinates(new Coordinate(x, y));
        return true;
    }

    public boolean put (Coordinate coordinate, CanvasNode node) {
        return put (coordinate.x, coordinate.y, node);
    }

    public static CanvasNode.Direction inverseDirection(CanvasNode.Direction d) {
        switch (d) {
            case NORTH -> { return CanvasNode.Direction.SOUTH;}
            case SOUTH -> { return CanvasNode.Direction.NORTH;}
            case EAST -> { return CanvasNode.Direction.WEST;}
            case WEST -> { return CanvasNode.Direction.EAST;}
            default -> { return null;}
        }
    }

    public List<CanvasNode> allNodes() {
        List<CanvasNode> all = new ArrayList<>();
        for (CanvasNode[] n : nodes) all.addAll(Arrays.stream(n).filter(Objects::nonNull).toList());
        return all;
    }

    public boolean isEmpty() {
        for (CanvasNode[] carr : nodes) {
            if (Arrays.stream(carr).anyMatch(Objects::nonNull)) return false;
        }

        return true;
    }

    public CanvasNode getNode(int x, int y) {
        return nodes[y][x];
    }

    public CanvasNode getNode(Coordinate coordinate) {
        return nodes[coordinate.y][coordinate.x];
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public final CanvasNode[][] getNodes() {
        return nodes;
    }

    public void setNodes(CanvasNode[][] nodes) {
        this.nodes = nodes;
    }

    public Set<CanvasNode.Direction> openDirections(CanvasNode canvasNode) {
        Set<CanvasNode.Direction> dirs = new HashSet<>();

        //North
        if (canvasNode.north() != null && canvasNode.north().y >= 0 && nodes[canvasNode.north().y][canvasNode.north().x] == null) dirs.add(CanvasNode.Direction.NORTH);
        // South
        if (canvasNode.south().y < length && canvasNode.south().x < width && nodes[canvasNode.south().y][canvasNode.south().x] == null) dirs.add(CanvasNode.Direction.SOUTH);
        // East
        if (canvasNode.east().x < width && nodes[canvasNode.east().y][canvasNode.east().x] == null) dirs.add(CanvasNode.Direction.EAST);
        // West
        if (canvasNode.west() != null && canvasNode.west().x >= 0 && nodes[canvasNode.west().y][canvasNode.west().x] == null) dirs.add(CanvasNode.Direction.WEST);

        return dirs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) sb.append("-");
        sb.append("\n");
        for (int y = 0; y < length; y++) {
            sb.append("|");
            for (int x = 0; x < width; x++) {
                if (nodes[y][x] != null) sb.append("*");
                else sb.append(" ");
            }
            sb.append("|");
            sb.append("\n");
        }
        for (int i = 0; i < length; i++) sb.append("-");
        sb.append("\n");
        return sb.toString();
    }
}
