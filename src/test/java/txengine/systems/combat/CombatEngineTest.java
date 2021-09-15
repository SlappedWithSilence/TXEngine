package txengine.systems.combat;

import txengine.systems.ability.AbilityManager;
import txengine.systems.inventory.Inventory;
import txengine.main.Manager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


public class CombatEngineTest {

    @BeforeAll
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

        friendlies.add(new CombatEntity("F1", "", "", 1, new Inventory(), new AbilityManager(), new CombatResourceManager(), new EquipmentManager(), 1, 5, 5));
        friendlies.add(new CombatEntity("F2", "", "", 1, new Inventory(), new AbilityManager(), new CombatResourceManager(),  new EquipmentManager(),2, 5,5));

        enemies.add(new CombatEntity("E1", "", "", 1, new Inventory(), new AbilityManager(), new CombatResourceManager(), new EquipmentManager(), 3, 5,5));
        enemies.add(new CombatEntity("E2", "", "", 1, new Inventory(), new AbilityManager(), new CombatResourceManager(),  new EquipmentManager(),4, 50,5));

        CombatEngine engine = new CombatEngine(friendlies, enemies);

        assert (engine.getTurnOrder().get(0).getKey() == CombatEngine.EntityType.HOSTILE && engine.getTurnOrder().get(0).getValue() == 2);
    }

    @Test
    public void getValidTargets() {


    }
}