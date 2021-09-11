package txengine.systems.combat.combatEffect.combatEffects;

import txengine.systems.combat.CombatEntity;
import txengine.systems.combat.combatEffect.CombatEffect;

public class LevelResourceEffect extends CombatEffect {

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
    public void perform(CombatEntity entity) {
        int newValue = entity.getResourceManager().getResourceQuantity(super.properties[0]);
        newValue += entity.getLevel() * Integer.parseInt(super.properties[1]);
        entity.getResourceManager().setResource(super.properties[0], newValue);
    }
}
