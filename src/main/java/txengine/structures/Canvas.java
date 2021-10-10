package txengine.structures;

import java.io.DataInput;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Canvas {
    int length;
    int width;

    List<List<CanvasNode>> nodes;


    public Canvas(int length, int width) {
        nodes = new ArrayList<>();

        for (List<CanvasNode> nodeList : nodes) nodeList = new ArrayList<>();
    }

    public CanvasNode getNode(int x, int y) {
        return nodes.get(y).get(x);
    }

    public CanvasNode getNode(Coordinate coordinate) {
        return nodes.get(coordinate.y).get(coordinate.x);
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

    public List<List<CanvasNode>> getNodes() {
        return nodes;
    }

    public void setNodes(List<List<CanvasNode>> nodes) {
        this.nodes = nodes;
    }

    public Set<CanvasNode.Direction> openDirections(CanvasNode canvasNode) {
        Set<CanvasNode.Direction> dirs = new HashSet<>();
        if (canvasNode.east() != null) dirs.add(CanvasNode.Direction.EAST);
        if (canvasNode.west().x < width) dirs.add(CanvasNode.Direction.WEST);
        if (canvasNode.north() != null) dirs.add(CanvasNode.Direction.NORTH);
        if (canvasNode.south().y < length) dirs.add(CanvasNode.Direction.SOUTH);
        return dirs;
    }
}
