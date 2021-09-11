package txengine.item.effect.effects;

import txengine.systems.combat.CombatEntity;
import txengine.item.effect.Effect;

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
    public void perform(CombatEntity combatEntity) {
        int newValue = combatEntity.getResourceManager().getResourceQuantity(super.properties[0]);
        newValue += combatEntity.getLevel() * Integer.parseInt(super.properties[1]);
        combatEntity.getResourceManager().setResource(super.properties[0], newValue);
    }
}
