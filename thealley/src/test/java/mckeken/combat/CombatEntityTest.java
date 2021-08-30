package mckeken.combat;

import org.junit.Test;

import static org.junit.Assert.*;

public class CombatEntityTest {

    @Test
    public void getName() {
        assert(new CombatEntity().getName() != null);
    }

    @Test
    public void setName() {

    }

    @Test
    public void getOpeningDialog() {
        assert(new CombatEntity().getOpeningDialog() != null);

    }

    @Test
    public void setOpeningDialog() {

    }

    @Test
    public void getClosingDialog() {
        assert(new CombatEntity().getClosingDialog() != null);
    }

    @Test
    public void setClosingDialog() {

    }

    @Test
    public void getInventory() {
        assert(new CombatEntity().getInventory() != null);
    }

    @Test
    public void setInventory() {

    }

    @Test
    public void getResourceManager() {
        assert(new CombatEntity().getResourceManager() != null);
    }

    @Test
    public void setResourceManager() {
    }

    @Test
    public void getSpeed() {
        assert(new CombatEntity().getSpeed() == 0);
    }

    @Test
    public void setSpeed() {
    }
}