package txengine.systems.dungeon.generate;

import txengine.structures.Canvas;
import txengine.structures.CanvasNode;
import txengine.systems.dungeon.Dungeon;
import txengine.systems.dungeon.DungeonRoom;
import txengine.systems.dungeon.gimmicks.GimmickFactory;
import txengine.util.Utils;

import java.util.*;

public class ProgressiveBranchPasser implements BranchPasser {
    Map<String, Integer> args;
    @Override
    public List<CanvasNode> pass(List<CanvasNode> lastPass, Canvas canvas, Random rand, Dungeon owner) {
        if (lastPass == null) return new ArrayList<>();
        if (args == null) configure(new HashMap<>());

        List<CanvasNode> newNodes = new ArrayList<>();
        if (lastPass.size() == 0) return lastPass;
        for (CanvasNode n : lastPass) { // For each node generated in the last pass
            if (n.self().x == owner.getExitCoordinates().x && n.self().y == owner.getExitCoordinates().y) continue;

            int chance = args.get("base_chance") + (args.get("chance_per_pass") * args.get("passes"));
            if (Utils.randomInt(0,100) <= chance) { // If we succeed our check

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
        args.put("passes", args.get("passes") + 1);
        return newNodes;
    }

    @Override
    public void configure(Map<String, Integer> args) {
        this.args = args;
        if (!this.args.containsKey("base_chance")) this.args.put("base_chance", 25); // 25% base chance
        if (!this.args.containsKey("chance_per_pass")) this.args.put("chance_per_pass", 9); // +10% chance per pass
        if (!this.args.containsKey(("passes"))) this.args.put("passes", 1);
    }
}
