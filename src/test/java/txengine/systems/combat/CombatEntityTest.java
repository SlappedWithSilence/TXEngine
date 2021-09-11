package txengine.systems.combat;

import org.junit.jupiter.api.Test;

public class CombatEntityTest {

    @Test
    public void getName() {
        assert(new CombatEntity().getName() != null);
        assert(new CombatEntity("Name", "", "", 1).getName().equals("Name"));
    }

    @Test
    public void setName() {
        CombatEntity ce = new CombatEntity();
        ce.setName("Who");
        assert ce.getName().equals("Who");
    }

    @Test
    public void getOpeningDialog() {
        assert(new CombatEntity().getOpeningDialog() != null);
        assert(new CombatEntity("Name", "This", "", 1).getOpeningDialog().equals("This"));

    }

    @Test
    public void setAndGetOpeningDialog() {
        String d = "Some Dialog";
        CombatEntity ce = new CombatEntity();
        ce.setOpeningDialog(d);
        assert ce.getOpeningDialog().equals(d);
    }

    @Test
    public void setAndGetClosingDialog() {
        String d = "Some other dialog";
        CombatEntity ce = new CombatEntity();

        assert ce.getClosingDialog() != null;

        ce.setClosingDialog(d);

        assert ce.getClosingDialog().equals(d);
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