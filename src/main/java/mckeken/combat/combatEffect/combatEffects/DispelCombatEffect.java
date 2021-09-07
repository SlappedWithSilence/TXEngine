package mckeken.combat.combatEffect.combatEffects;

import mckeken.combat.CombatEngine;
import mckeken.combat.CombatEntity;
import mckeken.combat.combatEffect.CombatEffect;

// This is a special combat effect. DispelCombatEffects don't actually do anything on their own, instead relying entirely
// on the CombatEngine for their functionality. The CombatEngine recognizes DispelCombatEffects and then sets the remaining duration
// of all effects of the chosen class to zero (effectively removing them during the next cleanup scan).
public class DispelCombatEffect extends CombatEffect {

    private String dispelInstanceOf; // Dispels/removes any instances of effects of the class stored here
    private CombatEngine.EntityType entityType; // Which type of entity should have their effects removed (friendly or hostile, relative to the player).

    public DispelCombatEffect() {
        dispelInstanceOf = properties[0];
        setDuration(1);
    }

    @Override
    public void perform(CombatEntity entity) {

    }

    public String getDispelInstanceOf() {
        return dispelInstanceOf;
    }

    public void setDispelInstanceOf(String dispelInstanceOf) {
        this.dispelInstanceOf = dispelInstanceOf;
    }

    public CombatEngine.EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(CombatEngine.EntityType entityType) {
        this.entityType = entityType;
    }


}
