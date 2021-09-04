package mckeken.combat;

import mckeken.combat.ability.AbilityManager;
import mckeken.inventory.Inventory;
import mckeken.main.Manager;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CombatEngineTest {

    @Before
    public void setup() {
        Manager.main(new String[] {"-D"});
    }

    @Test
    public void startCombat() {
    }

    @Test
    public void addEndCondition() {
    }

    @Test
    public void getTurnOrder() {
        ArrayList<CombatEntity> friendlies = new ArrayList<>();
        ArrayList<CombatEntity> enemies = new ArrayList<>();

        friendlies.add(new CombatEntity("F1", "", "", 1, new Inventory(), new AbilityManager(), new CombatResourceManager(), 1));
        friendlies.add(new CombatEntity("F2", "", "", 1, new Inventory(), new AbilityManager(), new CombatResourceManager(), 2));

        enemies.add(new CombatEntity("E1", "", "", 1, new Inventory(), new AbilityManager(), new CombatResourceManager(), 3));
        enemies.add(new CombatEntity("E2", "", "", 1, new Inventory(), new AbilityManager(), new CombatResourceManager(), 4));

        CombatEngine engine = new CombatEngine(friendlies, enemies);

        assert (engine.getTurnOrder().get(0).getKey() == CombatEngine.EntityType.HOSTILE && engine.getTurnOrder().get(0).getValue() == 2);
    }

    @Test
    public void getValidTargets() {


    }
}