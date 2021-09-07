package mckeken.inventory;

import mckeken.main.Manager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InventoryTest {

    @Before
    public void setup() {
        Manager.main(new String[] {"-D"});



    }

    @Test
    public void getItemIDs() {
        Manager.player.setInventory(new Inventory(10));

        Manager.player.getInventory().addItem(1);
        assert Manager.player.getInventory().getItemIDs().size() == 1;
        assert Manager.player.getInventory().getItemIDs().get(0) == 1;
    }

    @Test
    public void addItem() {

        Manager.player.setInventory(new Inventory(10));

        Manager.player.getInventory().addItem(1);
        assert Manager.player.getInventory().getItemIDs().get(0) == 1;

        Manager.player.getInventory().addItem(1);
        assert  Manager.player.getInventory().getItemIDs().size() == 1;
        assert  Manager.player.getInventory().getUsage() == 1;

        Manager.player.getInventory().addItem(2);
        assert Manager.player.getInventory().getUsage() == 2;
        assert  Manager.player.getInventory().getItemIDs().size() == 2;
    }

    @Test
    public void addItemWithQuantity() {
        Manager.player.setInventory(new Inventory(10));

        Manager.player.getInventory().addItem(1, Manager.itemList.get(1).getMaxStacks());

        assert Manager.player.getInventory().getUsage() == 1;
        assert Manager.player.getInventory().getItemIDs().size() == 1;

    }

    @Test
    public void addItemOverflow() {
        Manager.player.setInventory(new Inventory(10));
        Manager.player.getInventory().addItem(1, Manager.itemList.get(1).getMaxStacks());
        assert Manager.player.getInventory().getUsage() == 1;
        assert Manager.player.getInventory().getItemIDs().size() == 1;

        Manager.player.getInventory().addItem(1);
        assert Manager.player.getInventory().getUsage() == 2;
    }

    @Test
    public void testAddItem2() {
    }

    @Test
    public void removeItem() {
    }

    @Test
    public void decrementItem() {
    }

    @Test
    public void incrementItem() {
    }

    @Test
    public void setCapacity() {
    }

    @Test
    public void getCapacity() {
    }

    @Test
    public void setUsage() {
    }

    @Test
    public void getUsage() {
    }

    @Test
    public void display() {
    }

    @Test
    public void getItemInstance() {
    }
}