package txengine.item.effect.effects;

import txengine.systems.combat.CombatEntity;
import txengine.item.effect.Effect;

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
    public void perform(CombatEntity combatEntity) {
        // Resource[resourceName] = Resources[resourceName][quantity] + Resources[resourceName][maxQuantity]
        combatEntity.getResourceManager().setResource(super.properties[0], combatEntity.getResourceManager().getResourceQuantity(super.properties[0]) +
                                                                                           (int) (combatEntity.getResourceManager().getResources().get(super.properties[0])[0] * Double.parseDouble(super.properties[1])));
    }


}
