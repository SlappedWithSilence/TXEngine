package mckeken.item.effect.effects;

import mckeken.item.effect.Effect;
import mckeken.main.Manager;

public class LevelResourceEffect extends Effect {

    public LevelResourceEffect() {
        super.properties = new String[]{"NoName", "NoQuantity"};
    }

    public LevelResourceEffect(String resourceName, int modifier) {
        super(new String[] {resourceName, ""+modifier});
    }

    public LevelResourceEffect(String[] properties) {
        super(properties);
    }

    @Override
    public void perform() {
        int newValue = Manager.player.getResourceManager().getResourceQuantity(super.properties[0]);
        newValue += Manager.player.getLevel() * Integer.parseInt(super.properties[1]);
        Manager.player.getResourceManager().setResource(super.properties[0], newValue);
    }
}
