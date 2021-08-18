package mckeken.combat.combatEffect;

import mckeken.combat.CombatEngine;
import mckeken.item.effect.Effect;

public abstract class CombatEffect extends Effect {

    CombatEngine.EntityType sourceEntityType; // Whether the entity that spawned the effect was hostile or friendly to the player
    int duration; // Duration (in turns) of the effect. A value of -1 means that the effect lasts indefinitely.



    public CombatEngine.EntityType getSourceEntityType() {
        return sourceEntityType;
    }

    public void setSourceEntityType(CombatEngine.EntityType sourceEntityType) {
        this.sourceEntityType = sourceEntityType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}