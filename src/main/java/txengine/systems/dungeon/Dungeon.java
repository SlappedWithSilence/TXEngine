package txengine.systems.dungeon;

import jdk.jshell.execution.Util;
import txengine.io.CrashReporter;
import txengine.structures.Canvas;
import txengine.structures.CanvasNode;
import txengine.structures.Coordinate;
import txengine.structures.Graph;
import txengine.systems.dungeon.gimmicks.GimmickFactory;
import txengine.systems.room.Room;
import txengine.systems.room.action.Action;
import txengine.systems.room.action.actions.*;
import txengine.ui.LogUtils;
import txengine.ui.color.Colors;
import txengine.ui.component.Components;
import txengine.util.Utils;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Dungeon {
    Canvas roomCanvas;

    Integer[] enemyPool;
    List<AbstractMap.SimpleEntry<Integer, Integer>> clearRewards;

    int maximumLength;
    Long seed;
    Random rand;
    int directionalSpread = 50; // How likely it is for the core route to change directions during generation (out of 100)

    Coordinate playerLocation = null;
    Coordinate exitCoordinates = null;

    public Dungeon() {
        maximumLength = 10;
        roomCanvas = new Canvas(maximumLength+ Utils.randomInt(-1,maximumLength/2), maximumLength + Utils.randomInt(-1, maximumLength/2));

        rand = new Random();
        seed = rand.nextLong();
        rand = new Random(seed);

        clearRewards = new ArrayList<>();
        enemyPool = new Integer[0];
    }

    public Dungeon(Long seed) {
        maximumLength = 10;
        roomCanvas = new Canvas(maximumLength+ Utils.randomInt(-1,maximumLength/2), maximumLength + Utils.randomInt(-1, maximumLength/2));
        rand = new Random(seed);
        this.seed = seed;
        clearRewards = new ArrayList<>();
        enemyPool = new Integer[0];

    }

    public boolean enter() {
        while (!generate());
        while (!playerLocation.equals(exitCoordinates)) {
            LogUtils.info("Player location:" + playerLocation.x + ", " + playerLocation.y, "Dungeon::enter");
            playerLocation = ((DungeonRoom) roomCanvas.getNode(playerLocation)).enter();
        }
        return true;
    }

    private DungeonRoom getRoot() {
        // Generate a coordinate pair at (n,0) where n is between 0 and the width of the canvas
        Coordinate rootCoordinate = new Coordinate(Utils.randomInt(0,roomCanvas.getWidth()-1),0);
        DungeonRoom dr = new DungeonRoom(this, rootCoordinate);
        playerLocation = rootCoordinate;
        LogUtils.info("Root: " + rootCoordinate.x + ", " + rootCoordinate.y, "Dungeon::getRoot");
        roomCanvas.put(rootCoordinate, dr);
        return dr;
    }

    private boolean generateCoreRoute(DungeonRoom from, int length, CanvasNode.Direction fromDirection) {
        // Check if the route has reached its maximum length (ie is done generating)
        if (length > maximumLength) {
            exitCoordinates = from.getCoordinates();
            LogUtils.info("Exit: " + exitCoordinates, "Dungeon::generateCoreRoute");
            return true;
        }

        // Check if the route trapped itself before reaching its maximum length
        if (roomCanvas.openDirections(from).size() == 0) {
            LogUtils.error("Failed to generate dungeon! Writing configuration files to crash-details.txt","Dungeon::GenerateCoreRoute");
            handleCrash();
            return false;
        }


        CanvasNode.Direction nextDirection = null;

        // Detect if we are extending from the root node, or if we cannot continue in the same direction
        if (fromDirection == null || !roomCanvas.openDirections(from).contains(fromDirection)) nextDirection = Utils.selectRandom(roomCanvas.openDirections(from).toArray(new CanvasNode.Direction[0]), rand); // Choose a random direction

        // directionalSpread% chance to go in a direction other than the same direction
        else if (Utils.randomInt(0,100, rand) <= directionalSpread) {
            Set<CanvasNode.Direction> directions = roomCanvas.openDirections(from);
            directions.remove(fromDirection);
            nextDirection = Utils.selectRandom(directions.toArray(new CanvasNode.Direction[0]), rand);
        }

        // Generate the next node in the same direction
        else nextDirection = fromDirection;

        // Create a door to the next node in the previous node
        from.addDoor(nextDirection);

        // Generate the new node
        DungeonRoom to = new DungeonRoom(this, new ArrayList<>(), from.to(nextDirection));
        to.addDoor(Canvas.inverseDirection(nextDirection));
        to.roomActions.addAll(GimmickFactory.build(this));

        // Add the new node to the canvas
        roomCanvas.put(from.to(nextDirection), to);
        return generateCoreRoute(to, length + 1, nextDirection);
    }

    private boolean generate() {
        DungeonRoom root = getRoot();
        return generateCoreRoute(root, 1, null);
    }

    public static List<Action> getDefaultActions() {
        List<Action> actions = new ArrayList<>();
        actions.add(new SummaryAction());
        actions.add(new InventoryAction());
        actions.add(new AbilitySummaryAction());
        actions.add(new SkillSummaryAction());
        actions.add(new EquipmentAction());

        return actions;
    }

    private void handleCrash() {
        CrashReporter.getInstance().append("Failed to generate dungeon!\n");
        StringBuilder sb = new StringBuilder();
        sb.append("Seed: ").append(seed).append("\n");
        sb.append("Dimensions (LxW): ").append(roomCanvas.getLength()).append(",").append(roomCanvas.getWidth()).append("\n");
        sb.append("Maximum Length: ").append(maximumLength).append("\n");
        sb.append("Enemy pool: ");
        for (int i : enemyPool) sb.append(i).append(" ");
        sb.append("\n");
        CrashReporter.getInstance().append(sb);
        CrashReporter.getInstance().write();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < roomCanvas.getLength(); i++) sb.append("---");
        sb.append("\n");
        for (int y = 0; y < getRoomCanvas().getLength(); y++) {
            StringBuilder nodeBuilder0 = new StringBuilder();
            StringBuilder nodeBuilder1 = new StringBuilder();
            StringBuilder nodeBuilder2 = new StringBuilder();
            nodeBuilder0.append("|");
            nodeBuilder1.append("|");
            nodeBuilder2.append("|");
            for (int x = 0; x < getRoomCanvas().getWidth(); x++) {
                if (roomCanvas.getNode(x, y) == null) {
                    nodeBuilder0.append("   ");
                    nodeBuilder1.append("   ");
                    nodeBuilder2.append("   ");
                } else {
                    Set<CanvasNode.Direction> doors = roomCanvas.getNode(x, y).getDoors();
                    // North
                    nodeBuilder0.append(" ");
                    if (doors.contains(CanvasNode.Direction.NORTH)) nodeBuilder0.append("|");
                    else nodeBuilder0.append(" ");
                    nodeBuilder0.append(" ");

                    // West
                    if (doors.contains(CanvasNode.Direction.WEST)) nodeBuilder1.append("-");
                    else nodeBuilder1.append(" ");

                    // Node
                    nodeBuilder1.append("*");

                    // East
                    if (doors.contains(CanvasNode.Direction.EAST)) nodeBuilder1.append("-");
                    else nodeBuilder1.append(" ");

                    // South
                    nodeBuilder2.append(" ");
                    if (doors.contains(CanvasNode.Direction.SOUTH)) nodeBuilder2.append("|");
                    else nodeBuilder2.append(" ");
                    nodeBuilder2.append(" ");
                }
            }

            sb.append(nodeBuilder0).append("|\n");
            sb.append(nodeBuilder1).append("|\n");
            sb.append(nodeBuilder2).append("|\n");

        }
        for (int i = 0; i < roomCanvas.getLength(); i++) sb.append("---");
        sb.append("\n");
        return sb.toString();
    }

    /*** Accessors ***/
    public Canvas getRoomCanvas() {
        return roomCanvas;
    }

    public void setRoomCanvas(Canvas roomCanvas) {
        this.roomCanvas = roomCanvas;
    }

    public Long getSeed() {
        return seed;
    }

    public void setSeed(Long seed) {
        this.seed = seed;
    }

    public Integer[] getEnemyPool() {
        return enemyPool;
    }

    public void setEnemyPool(Integer[] enemyPool) {
        this.enemyPool = enemyPool;
    }

    public List<AbstractMap.SimpleEntry<Integer, Integer>> getClearRewards() {
        return clearRewards;
    }

    public void setClearRewards(List<AbstractMap.SimpleEntry<Integer, Integer>> clearRewards) {
        this.clearRewards = clearRewards;
    }

    public int getMaximumLength() {
        return maximumLength;
    }

    public void setMaximumLength(int maximumLength) {
        this.maximumLength = maximumLength;
    }

    public Random getRand() {
        return rand;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }
}
