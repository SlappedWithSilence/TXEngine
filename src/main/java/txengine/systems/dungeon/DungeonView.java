package txengine.systems.dungeon;

import txengine.structures.CanvasNode;
import txengine.systems.room.action.Action;
import txengine.ui.color.Colors;
import txengine.ui.component.Components;

import java.util.Set;

public class DungeonView extends DungeonAction{

    public DungeonView(Dungeon owner) {
        super(owner);
    }

    @Override
    public int perform() {
        Components.header("Dungeon Map");
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int y = 0; y < owner.getVisitedNodes().getLength(); y++) {
            StringBuilder nodeBuilder0 = new StringBuilder();
            StringBuilder nodeBuilder1 = new StringBuilder();
            StringBuilder nodeBuilder2 = new StringBuilder();
            nodeBuilder0.append("|");
            nodeBuilder1.append("|");
            nodeBuilder2.append("|");
            for (int x = 0; x < owner.getVisitedNodes().getWidth(); x++) {
                if (owner.getVisitedNodes().getNode(x, y) == null) {
                    nodeBuilder0.append("   ");
                    nodeBuilder1.append("   ");
                    nodeBuilder2.append("   ");
                } else {
                    Set<CanvasNode.Direction> doors = owner.getVisitedNodes().getNode(x, y).getDoors();
                    // North
                    nodeBuilder0.append(" ");
                    if (doors.contains(CanvasNode.Direction.NORTH)) nodeBuilder0.append("|");
                    else nodeBuilder0.append(" ");
                    nodeBuilder0.append(" ");

                    // West
                    if (doors.contains(CanvasNode.Direction.WEST)) nodeBuilder1.append("-");
                    else nodeBuilder1.append(" ");

                    // Node
                    if (owner.playerLocation.x == x && owner.playerLocation.y == y) nodeBuilder1.append(Colors.GREEN_BOLD).append("*").append(Colors.RESET);
                    else nodeBuilder1.append('*');

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
        sb.append("\n");

        System.out.println(sb);

        return 0;
    }

}
