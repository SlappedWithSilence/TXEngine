package txengine.item.effect.effects;

import txengine.systems.combat.CombatEntity;
import txengine.item.effect.Effect;

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
    public void perform(CombatEntity combatEntity) {
        combatEntity.getResourceManager().incrementResource(super.properties[0], Integer.parseInt(super.properties[1]));
    }
}
