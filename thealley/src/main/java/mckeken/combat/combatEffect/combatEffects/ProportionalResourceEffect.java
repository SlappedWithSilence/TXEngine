package mckeken.combat.combatEffect.combatEffects;

import mckeken.combat.CombatEntity;
import mckeken.combat.combatEffect.CombatEffect;
import mckeken.item.effect.Effect;
import mckeken.main.Manager;

// Modifies a resource by a percent of the resource's max quantity.
// Ex: Reduce Health by 25%
public class ProportionalResourceEffect extends CombatEffect {

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
        // Resource[resourceName] = Resources[resourceName][quantity] + Resources[resourceName][maxQuantity]
        Manager.player.getResourceManager().setResource(super.properties[0], Manager.player.getResourceManager().getResourceQuantity(super.properties[0]) +
                                                                                           (int) (Manager.player.getResourceManager().getResources().get(super.properties[0])[0] * Double.parseDouble(super.properties[1])));
    }


    @Override
    public void perform(CombatEntity entity) {
        entity.getResourceManager().setResource(super.properties[0], entity.getResourceManager().getResourceQuantity(super.properties[0]) +
                (int) (entity.getResourceManager().getResources().get(super.properties[0])[0] * Double.parseDouble(super.properties[1])));
    }
}
