package mckeken.combat.combatEffect.combatEffects;


import mckeken.combat.CombatEngine;
import mckeken.combat.CombatEntity;
import mckeken.combat.combatEffect.CombatEffect;

// This is a special combat effect. AddPhaseEffect don't actually do anything on their own, instead relying entirely
// on the CombatEngine for their functionality. The CombatEngine recognizes AddPhaseEffect and then adds the desired phase to the current turn.
// Note that the new phase is added directly after the current phase, so choose carefully as to when this effect should trigger.
public class AddPhaseEffect extends CombatEffect {

    CombatEngine.CombatPhase phase; // The phase to add

    public AddPhaseEffect() {
        setDuration(1);
        phase = CombatEngine.CombatPhase.valueOf(super.properties[0]);
    }





    public CombatEngine.CombatPhase getPhase() {
        return phase;
    }

    public void setPhase(CombatEngine.CombatPhase phase) {
        this.phase = phase;
    }

    @Override
    public void perform(CombatEntity entity) {

    }

}
