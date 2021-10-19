package txengine.systems.dungeon.generate;

import txengine.structures.Canvas;
import txengine.structures.CanvasNode;
import txengine.systems.dungeon.Dungeon;

import java.util.List;
import java.util.Map;
import java.util.Random;

public interface BranchPasser {
    List<CanvasNode> pass(final List<CanvasNode> lastPass, final Canvas canvas, final Random rand, final Dungeon owner);
    void configure(Map<String, Integer> args);

    default void generate(int passes, final Canvas canvas, final Random rand, final Dungeon owner) {
        List<CanvasNode> nodes = canvas.allNodes();
        for (int i  = 0; i < passes; i++) {
            nodes = pass(nodes, canvas, rand, owner);
        }
    }
}
