package mckeken.combat.combatEffect.combatEffects;

import mckeken.combat.CombatEngine;
import mckeken.combat.combatEffect.CombatEffect;


// This is a special combat effect. RemovePhaseEffect don't actually do anything on their own, instead relying entirely
// on the CombatEngine for their functionality. The CombatEngine recognizes RemovePhaseEffect and then removes the desired phase from the current turn.
public class RemovePhaseEffect extends CombatEffect {

    CombatEngine.CombatPhase phase; // The phase to skip

    @Override
    public void perform() {
        phase = CombatEngine.CombatPhase.valueOf(super.properties[0]);
        setDuration(1);
    }

    public CombatEngine.CombatPhase getPhase() {
        return phase;
    }

    public void setPhase(CombatEngine.CombatPhase phase) {
        this.phase = phase;
    }
}
