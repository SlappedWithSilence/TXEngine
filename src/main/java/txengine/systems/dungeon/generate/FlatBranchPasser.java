package txengine.systems.dungeon.generate;

import txengine.structures.Canvas;
import txengine.structures.CanvasNode;
import txengine.systems.dungeon.Dungeon;
import txengine.systems.dungeon.DungeonRoom;
import txengine.systems.dungeon.gimmicks.GimmickFactory;
import txengine.util.Utils;

import java.util.*;

public class FlatBranchPasser implements BranchPasser{
    Map<String, Integer> args;
    @Override
    public List<CanvasNode> pass(final List<CanvasNode> lastPass, final Canvas canvas, final Random rand, final Dungeon owner) {
        if (lastPass == null) return new ArrayList<>();
        if (args == null) configure(new HashMap<>());

        List<CanvasNode> newNodes = new ArrayList<>();
        if (lastPass.size() == 0) return lastPass;
        for (CanvasNode n : lastPass) { // For each node generated in the last pass
            if (n.self().x == owner.getExitCoordinates().x && n.self().y == owner.getExitCoordinates().y) continue;

            if (Utils.randomInt(0,100) <= args.get("spread")) { // If we succeed our check

                Set<CanvasNode.Direction> possibleNodeDirections = canvas.openDirections(n); // Determine possible directions

                if (!possibleNodeDirections.isEmpty()) { // If there is at least one possible direction

                    CanvasNode.Direction newNodeDirection =
                            Utils.selectRandom(possibleNodeDirections.toArray(new CanvasNode.Direction[0]), rand); // Randomly select a direction

                    n.addDoor(newNodeDirection); // Add the direction to the current node's door list
                    DungeonRoom dr = DungeonRoomFactory.build(owner, n.to(newNodeDirection), GimmickFactory.randomGimmick(owner),
                            Canvas.inverseDirection(newNodeDirection), null);

                    canvas.put(dr.self(), dr);
                    newNodes.add(dr);
                }
            }
        }
        return newNodes;
    }

    @Override
    public void configure(Map<String, Integer> args) {
        this.args = args;
        if (!this.args.containsKey("spread")) this.args.put("spread", 33); // 33% chance to branch
    }
}
