package txengine.structures;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CanvasTest {
    @Test
    void basicConstructor() {
        Canvas c = new Canvas(5, 1);
        assert c.length == 5;
        assert c.width  == 1;
    }

    @Test
    void put() {
        Canvas c = new Canvas(3,3);

        assert c.put(0,0, new CanvasNode()) == true;
        assert c.getNodes()[0][0] != null;
        assert c.put(0,0,new CanvasNode()) == false;


        assert c.put(0,1, new CanvasNode()) == true;
        assert c.put(0,1, new CanvasNode()) == false;
        assert c.getNodes()[1][0] != null;
    }

    @Test
    void openDirectionsCorner() {
        Canvas c = new Canvas(3,3);

        c.put(0,0, new CanvasNode());
        assert c.openDirections(c.getNode(0,0)).contains(CanvasNode.Direction.EAST);
        assert c.openDirections(c.getNode(0,0)).contains(CanvasNode.Direction.SOUTH);
        assertFalse(c.openDirections(c.getNode(0,0)).contains(CanvasNode.Direction.WEST));
        assertFalse(c.openDirections(c.getNode(0,0)).contains(CanvasNode.Direction.NORTH));
    }

    @Test
    void openDirectionsMiddle() {
        Canvas c = new Canvas(3, 3);

        c.put(1, 1, new CanvasNode());

        assert c.openDirections(c.getNode(1,1)).containsAll(Arrays.stream(CanvasNode.Direction.values()).toList());
    }

    @Test
    void openDirectionsWall() {
        Canvas c = new Canvas(3, 3);

        c.put(0, 1, new CanvasNode());

        assert c.openDirections(c.getNode(0,1)).contains(CanvasNode.Direction.EAST);
        assert c.openDirections(c.getNode(0,1)).contains(CanvasNode.Direction.SOUTH);
        assert (c.openDirections(c.getNode(0,1)).contains(CanvasNode.Direction.NORTH));
        assertFalse(c.openDirections(c.getNode(0,1)).contains(CanvasNode.Direction.WEST));
    }

    @Test
    void openDirectionsCollision() {
        Canvas c = new Canvas(3, 3);

        c.put(1, 1, new CanvasNode()); // Center
        c.put(0, 1, new CanvasNode()); // West of center

        assert c.openDirections(c.getNode(1,1)).contains(CanvasNode.Direction.EAST);
        assert c.openDirections(c.getNode(1,1)).contains(CanvasNode.Direction.SOUTH);
        assert (c.openDirections(c.getNode(1,1)).contains(CanvasNode.Direction.NORTH));
        assertFalse(c.openDirections(c.getNode(1,1)).contains(CanvasNode.Direction.WEST));

        c = new Canvas(3, 3);

        c.put(1, 1, new CanvasNode()); // Center
        c.put(2, 1, new CanvasNode()); // East of center

        assert c.openDirections(c.getNode(1,1)).contains(CanvasNode.Direction.WEST);
        assert c.openDirections(c.getNode(1,1)).contains(CanvasNode.Direction.SOUTH);
        assert (c.openDirections(c.getNode(1,1)).contains(CanvasNode.Direction.NORTH));
        assertFalse(c.openDirections(c.getNode(1,1)).contains(CanvasNode.Direction.EAST));

        c = new Canvas(3, 3);

        c.put(1, 1, new CanvasNode()); // Center
        c.put(1, 0, new CanvasNode()); // North of center

        assert c.openDirections(c.getNode(1,1)).contains(CanvasNode.Direction.WEST);
        assert c.openDirections(c.getNode(1,1)).contains(CanvasNode.Direction.SOUTH);
        assert (c.openDirections(c.getNode(1,1)).contains(CanvasNode.Direction.EAST));
        assertFalse(c.openDirections(c.getNode(1,1)).contains(CanvasNode.Direction.NORTH));

        c = new Canvas(3, 3);

        c.put(1, 1, new CanvasNode()); // Center
        c.put(1, 2, new CanvasNode()); // South of center

        assert c.openDirections(c.getNode(1,1)).contains(CanvasNode.Direction.WEST);
        assert c.openDirections(c.getNode(1,1)).contains(CanvasNode.Direction.NORTH);
        assert (c.openDirections(c.getNode(1,1)).contains(CanvasNode.Direction.EAST));
        assertFalse(c.openDirections(c.getNode(1,1)).contains(CanvasNode.Direction.SOUTH));
    }
}