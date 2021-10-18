package txengine.systems.dungeon.gimmicks;

import txengine.main.Manager;
import txengine.systems.combat.CombatEntity;
import txengine.systems.dungeon.Dungeon;
import txengine.systems.dungeon.DungeonGimmick;
import txengine.systems.room.action.Action;
import txengine.systems.room.action.actions.CombatAction;
import txengine.ui.LogUtils;
import txengine.util.Utils;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class CombatGimmick extends DungeonGimmick {
    private static final int DEFAULT_LOOT_QUANTITY = 3;
    private static final int DEFAULT_LOOT_SPREAD = 2;

    int lootQuantity;

    public CombatGimmick(Dungeon owner) {
        super(owner);
        lootQuantity = Utils.randomInt(Math.max(DEFAULT_LOOT_QUANTITY - DEFAULT_LOOT_SPREAD,1), DEFAULT_LOOT_QUANTITY+DEFAULT_LOOT_SPREAD);
        type = Type.LOCKED;
    }

    @Override
    public List<Action> get() {
        List<Action> actions = new ArrayList<>();

        // Get random enemy
        Integer randomEntityID = Utils.selectRandom(owner.getEnemyPool(), owner.getRand());
        List<Integer> hostiles = new ArrayList<>();
        hostiles.add(randomEntityID);

        // Get random loot
        ArrayList<AbstractMap.SimpleEntry<Integer, Integer>> loot = new ArrayList<>();
        CombatAction a = new CombatAction(new ArrayList<>(), hostiles, loot);
        a.setMenuName("Fight mysterious entity");
        a.setHideOnWin(true);
        a.addLoot(owner.getGimmickKeyID());

        for (int i = 0; i < lootQuantity; i++) { // TODO: Allow for individual loot pools for specific combat gimmicks
            if (owner.getRewardsPool() == null || owner.getRewardsPool().size() == 0) {
                break;
            }
            if (owner.getRand() == null) {
                LogUtils.error("Dungeon random generator is null!", "CombatGimmick::get");
            }
            int randID = Utils.selectRandom(owner.getRewardsPool(), owner.getRand());
            a.addLoot(randID);
        }

        actions.add(a);
        return actions;
    }
}
