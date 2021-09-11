package txengine.systems.combat.combatEffect.combatEffects;

import txengine.systems.combat.CombatEntity;
import txengine.systems.combat.combatEffect.CombatEffect;

public class FlatResourceEffect extends CombatEffect {

    public FlatResourceEffect() {
        super.properties = new String[]{"NoName", "NoQuantity"};
        setDuration(1);
    }

    public FlatResourceEffect(String resourceName, int modifier) {
        super(new String[] {resourceName, ""+modifier});
        setDuration(1);
    }

    public FlatResourceEffect(String[] properties) {
        super(properties);
        setDuration(1);
    }

    public FlatResourceEffect(String[] properties, int duration) {
        super(properties, duration);
    }

    @Override
    public void perform(CombatEntity entity) {
        entity.getResourceManager().incrementResource(super.properties[0], Integer.parseInt(super.properties[1]));
    }
}
