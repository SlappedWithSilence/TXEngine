package mckeken.combat.combatEffect;

import mckeken.combat.CombatEngine;

// This is a special combat effect. DispelCombatEffects don't actually do anything on their own, instead relying entirely
// on the CombatEngine for their functionality. The CombatEngine recognizes DispelCombatEffects and then sets the remaining duration
// of all effects of the chosen class to zero (effectively removing them during the next cleanup scan).
public class DispelCombatEffect extends CombatEffect{

    private Class dispelInstanceOf; // Dispels/removes any instances of effects of the class stored here
    private CombatEngine.EntityType entityType; // Which type of entity should have their effects removed (friendly or hostile, relative to the player).

    public DispelCombatEffect() {
        super.duration = 1;
    }

    @Override
    public void perform() {
        // Don't do anything.
    }
}
