package mckeken.combat.combatEffect.combatEffects;

import mckeken.combat.CombatEngine;
import mckeken.combat.CombatEntity;
import mckeken.combat.combatEffect.CombatEffect;


// This is a special combat effect. RemovePhaseEffect don't actually do anything on their own, instead relying entirely
// on the CombatEngine for their functionality. The CombatEngine recognizes RemovePhaseEffect and then removes the desired phase from the current turn.
public class RemovePhaseEffect extends CombatEffect {

    CombatEngine.CombatPhase phase; // The phase to skip

    public RemovePhaseEffect() {
        setDuration(1);
    }


    @Override
    public void perform(CombatEntity entity) {

    }

    public CombatEngine.CombatPhase getPhase() {
        phase = CombatEngine.CombatPhase.valueOf(properties[0]);
        return phase;
    }

    public void setPhase(CombatEngine.CombatPhase phase) {
        this.phase = phase;
    }


}
