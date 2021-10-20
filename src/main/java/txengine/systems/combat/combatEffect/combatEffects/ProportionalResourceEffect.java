package txengine.systems.combat.combatEffect.combatEffects;

import txengine.systems.combat.CombatEntity;
import txengine.systems.combat.combatEffect.CombatEffect;

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
    public void perform(CombatEntity entity) {
        float resistBy = 1;
        if (getTags() != null && getTags().length > 0) {
            resistBy = resistBy - entity.getEquipmentManager().totalResistance(getTags());
        }

        int quantity = entity.getResourceManager().getResourceQuantity(super.properties[0]) +
                (int) (entity.getResourceManager().getResources().get(super.properties[0])[0] * Double.parseDouble(super.properties[1]));

        entity.getResourceManager().setResource(super.properties[0], (int)(quantity * resistBy));
    }
}
