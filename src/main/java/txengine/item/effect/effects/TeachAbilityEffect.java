package txengine.item.effect.effects;

import txengine.systems.combat.CombatEntity;
import txengine.io.LogUtils;
import txengine.item.effect.Effect;

public class TeachAbilityEffect extends Effect {

    public TeachAbilityEffect(String[] properties) {
        super(properties);
    }

    public TeachAbilityEffect() {
        properties = new String[1];
    }

    public TeachAbilityEffect(String abilityName) {
        properties = new String[]{abilityName};
    }

    @Override
    public void perform(CombatEntity user) {
        if (properties.length < 1) {
            LogUtils.error("Malformed properties for a TeachAbilityEffect!");
            return;
        }

        user.getAbilityManager().learn(properties[0]);
    }
}
