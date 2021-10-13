package txengine.systems.dungeon;

import txengine.main.Manager;
import txengine.structures.Canvas;
import txengine.structures.CanvasNode;
import txengine.structures.Coordinate;
import txengine.structures.Pair;
import txengine.systems.dungeon.gimmicks.GimmickFactory;
import txengine.systems.room.action.Action;
import txengine.systems.room.action.actions.*;
import txengine.ui.LogUtils;
import txengine.util.Utils;

import java.util.*;
import java.util.List;

public class Dungeon {
    private final static int DEFAULT_LENGTH = 15;
    private final static int DEFAULT_SPREAD = 33; // How likely it is for the core route to change directions during generation (out of 100)
    private final static int DEFAULT_LOOT_QUANTITY = 5;
    private final static int DEFAULT_LOOT_QUANTITY_SPREAD = 3;

    // Inner structures
    Canvas roomCanvas;

    // Config pools
    Integer[] enemyPool;
    List<AbstractMap.SimpleEntry<Integer, Integer>> rewardsPool;

    // Loot Settings
    int lootQuantity;
    int lootSpread;

    // Generation settings
    int randomness;
    int maximumLength;
    Long seed;
    Random rand;

    // Core route details
    Coordinate playerLocation = null;
    Coordinate exitCoordinates = null;

    public Dungeon() {
        maximumLength = DEFAULT_LENGTH;
        randomness = DEFAULT_SPREAD;
        roomCanvas = new Canvas(maximumLength+ Utils.randomInt(-1,maximumLength/2), maximumLength + Utils.randomInt(-1, maximumLength/2));

        rand = new Random();
        seed = rand.nextLong();
        rand = new Random(seed);

        rewardsPool = new ArrayList<>();
        enemyPool = Manager.combatEntityHashMap.keySet().toArray(new Integer[0]);

        lootQuantity = DEFAULT_LOOT_QUANTITY;
        lootSpread = DEFAULT_LOOT_QUANTITY_SPREAD;
    }

    public Dungeon(Long seed) {
        maximumLength = DEFAULT_LENGTH;
        randomness = DEFAULT_SPREAD;
        roomCanvas = new Canvas(maximumLength+ Utils.randomInt(-1,maximumLength/2), maximumLength + Utils.randomInt(-1, maximumLength/2));

        rand = new Random(seed);
        this.seed = seed;

        rewardsPool = new ArrayList<>();
        enemyPool = Manager.combatEntityHashMap.keySet().toArray(new Integer[0]);
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

        if (fromDirection == null && length > 1) {
            LogUtils.error("An error occurred while generating core route! fromDirection is null without root!");
            LogUtils.error("At length: " + length + " from node: " + from.getCoordinates().toString());
            LogUtils.error("Open directions on that node: " + roomCanvas.openDirections(from).stream().map(Enum::toString).reduce("", (acc, str) -> acc + ", " + str));
        }

        CanvasNode.Direction nextDirection = null;

        // Detect if we are extending from the root node, or if we cannot continue in the same direction
        if (fromDirection == null || !roomCanvas.openDirections(from).contains(fromDirection)) {
            nextDirection = Utils.selectRandom(roomCanvas.openDirections(from).toArray(new CanvasNode.Direction[0]), rand); // Choose a random direction
            if (nextDirection == null) LogUtils.error("Obstruction case failure", "Dungeon::generateCoreRoute");
        }

        // directionalSpread% chance to go in a direction other than the same direction
        else if (Utils.randomInt(0,100, rand) <= randomness) {
            Set<CanvasNode.Direction> directions = roomCanvas.openDirections(from);
            if (directions.size() > 1) directions.remove(fromDirection); // If
            nextDirection = Utils.selectRandom(directions.toArray(new CanvasNode.Direction[0]), rand);
            if (nextDirection == null) LogUtils.error("Random-direction case failure", "Dungeon::generateCoreRoute");
        }

        // Generate the next node in the same direction
        else {
            nextDirection = fromDirection;
            if (nextDirection == null) LogUtils.error("Same-direction case failure", "Dungeon::generateCoreRoute");
        }

        // Create a door to the next node in the previous node
        from.addDoor(nextDirection);

        // Generate the new node
        DungeonRoom to = new DungeonRoom(this, GimmickFactory.randomGimmick(this), from.to(nextDirection));
        to.addDoor(Canvas.inverseDirection(nextDirection));
        to.roomActions.addAll(GimmickFactory.randomGimmick(this));

        // Add the new node to the canvas
        roomCanvas.put(from.to(nextDirection), to);
        return generateCoreRoute(to, length + 1, nextDirection);
    }

    public boolean generate() {
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

    public List<Pair<Integer, Integer>> getRewards() {
        List<Pair<Integer, Integer>> rewards = new ArrayList<>();



        return rewards;
    }

    private void handleCrash() {
        /*CrashReporter.getInstance().append("Failed to generate dungeon!\n");
        StringBuilder sb = new StringBuilder();
        sb.append("Seed: ").append(seed).append("\n");
        sb.append("Dimensions (LxW): ").append(roomCanvas.getLength()).append(",").append(roomCanvas.getWidth()).append("\n");
        sb.append("Maximum Length: ").append(maximumLength).append("\n");
        sb.append("Enemy pool: ");
        for (int i : enemyPool) sb.append(i).append(" ");
        sb.append("\n");
        CrashReporter.getInstance().append(sb);
        CrashReporter.getInstance().write();*/
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

    public List<AbstractMap.SimpleEntry<Integer, Integer>> getRewardsPool() {
        return rewardsPool;
    }

    public void setRewardsPool(List<AbstractMap.SimpleEntry<Integer, Integer>> rewardsPool) {
        this.rewardsPool = rewardsPool;
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
