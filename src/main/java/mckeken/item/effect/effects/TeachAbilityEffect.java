package mckeken.item.effect.effects;

import mckeken.combat.CombatEntity;
import mckeken.io.LogUtils;
import mckeken.item.effect.Effect;

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
