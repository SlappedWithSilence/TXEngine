package txengine.systems.combat.combatEffect.combatEffects;


import txengine.systems.combat.CombatEngine;
import txengine.systems.combat.CombatEntity;
import txengine.systems.combat.combatEffect.CombatEffect;

// This is a special combat effect. AddPhaseEffect don't actually do anything on their own, instead relying entirely
// on the CombatEngine for their functionality. The CombatEngine recognizes AddPhaseEffect and then adds the desired phase to the current turn.
// Note that the new phase is added directly after the current phase, so choose carefully as to when this effect should trigger.
public class AddPhaseEffect extends CombatEffect {

    CombatEngine.CombatPhase phase; // The phase to add

    public AddPhaseEffect() {
        setDuration(1);
    }

    public CombatEngine.CombatPhase getPhase() {
        return phase;
    }

    public void setPhase(CombatEngine.CombatPhase phase) {
        this.phase = phase;
    }

    @Override
    public void perform(CombatEntity entity) {
        phase = CombatEngine.CombatPhase.valueOf(properties[0]);
    }

}
