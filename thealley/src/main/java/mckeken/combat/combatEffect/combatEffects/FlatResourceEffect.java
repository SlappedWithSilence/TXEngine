package mckeken.combat.combatEffect.combatEffects;

import mckeken.combat.CombatEntity;
import mckeken.combat.combatEffect.CombatEffect;
import mckeken.item.effect.Effect;
import mckeken.main.Manager;

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
