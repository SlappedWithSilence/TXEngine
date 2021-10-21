package txengine.systems.item.effect.effects;

import txengine.systems.combat.CombatEntity;
import txengine.ui.LogUtils;
import txengine.systems.item.effect.Effect;
import txengine.ui.Out;

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
            Out.error("Malformed properties for a TeachAbilityEffect!");
            return;
        }

        user.getAbilityManager().learn(properties[0]);
    }
}
