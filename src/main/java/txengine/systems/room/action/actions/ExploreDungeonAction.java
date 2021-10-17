package txengine.systems.room.action.actions;

import txengine.io.load.PropertyTags;
import txengine.main.Manager;
import txengine.systems.dungeon.Dungeon;
import txengine.systems.room.action.Action;
import txengine.ui.color.Colors;
import txengine.util.Utils;

import java.util.List;
import java.util.Map;

import static txengine.io.load.PropertyTags.*;

public class ExploreDungeonAction extends Action {

    public ExploreDungeonAction() {

    }

    @Override
    public int perform() {
        Dungeon d = new Dungeon();

        // set up Dungeon generation parameters
        int length = Integer.parseInt(properties[0]);
        int randomness = Integer.parseInt(properties[1]);
        int complexity = Integer.parseInt(properties[2]);
        int doorKeyID = Integer.parseInt(properties[3]);

        Map<String, List<String>> data = PropertyTags.getMarkedProperties(properties);

        // Set up pools and seed
        if  (data.containsKey(seedMarker)) d.setSeed(Long.parseLong(data.get(seedMarker).get(0)));
        if (data.containsKey(hostileMarker)) {
            List<Integer> hostiles = data.get(hostileMarker).stream().map(Integer::parseInt).toList();
            d.setEnemyPool(hostiles.toArray(new Integer[0]));
        }
        if (data.containsKey(rewardsMarker)) {
            List<Integer> rewards = data.get(rewardsMarker).stream().map(Integer::parseInt).toList();
            d.setRewardsPool(rewards);
        }

        // Apply dungeon parameters
        d.setMaximumLength(length);
        d.setRandomness(randomness);
        d.setBranchRandomness(complexity);
        d.setGimmickKeyID(doorKeyID);

        if (d.enter()) {
            System.out.println("As you reach the end of the dungeon you notice a large chest.\nYou open it to find:");
            for (Integer loot : d.getRewards()) {
                Manager.player.getInventory().addItem(loot);
                System.out.println(Colors.GREEN_BRIGHT + Manager.itemHashMap.get(loot).getName() + Colors.RESET);
            }
        } else {
            System.out.println("You failed to conquer the dungeon and awake at its entrance.");
        }

        return unhideIndex;
    }
}
