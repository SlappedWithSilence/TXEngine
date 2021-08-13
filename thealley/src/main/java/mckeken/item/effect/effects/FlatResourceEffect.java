package mckeken.item.effect.effects;

import mckeken.item.effect.Effect;
import mckeken.main.Manager;

public class FlatResourceEffect extends Effect {

    public FlatResourceEffect() {
        super.properties = new String[]{"NoName", "NoQuantity"};
    }

    public FlatResourceEffect(String resourceName, int modifier) {
        super(new String[] {resourceName, ""+modifier});
    }

    public FlatResourceEffect(String[] properties) {
        super(properties);
    }

    @Override
    public void perform() {
        Manager.player.getResourceManager().incrementResource(super.properties[0], Integer.parseInt(super.properties[1]));
    }
}
