package mckeken.item.effect.effects;

import mckeken.item.effect.Effect;
import mckeken.main.Manager;

// Modifies a resource by a percent of the resource's max quantity.
// Ex: Reduce Health by 25%
public class ProportionalResourceEffect extends Effect {

    public ProportionalResourceEffect() {
        super.properties = new String[]{"NoName", "NoQuantity"};
    }

    public ProportionalResourceEffect(String resourceName, int modifier) {
        super(new String[] {resourceName, ""+modifier});
    }

    public ProportionalResourceEffect(String[] properties) {
        super(properties);
    }

    @Override
    public void perform() {
        Manager.player.getResourceManager().setResource(super.properties[0], Manager.player.getResourceManager().getResourceQuantity(super.properties[0]) +
                                                                                    Manager.player.getResourceManager().getResourceQuantity(super.properties[0]) * Integer.parseInt(super.properties[1]));
    }


}
