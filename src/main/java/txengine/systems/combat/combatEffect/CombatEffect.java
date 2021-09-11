package txengine.systems.combat.combatEffect;

import txengine.systems.combat.CombatEngine;
import txengine.systems.combat.CombatEntity;
import txengine.item.effect.Effect;

public abstract class CombatEffect extends Effect {

    // Member variables
    CombatEngine.EntityType sourceEntityType; // Whether the entity that spawned the effect was hostile or friendly to the player
    int duration; // Duration (in turns) of the effect. A value of -1 means that the effect lasts indefinitely.
    String triggerMessage; // A message that gets printed when the ability triggers
    String cleanupMessage; // A message that gets printed when the ability is cleaned up

    // Constructors
    public CombatEffect() {
        super();
        sourceEntityType = null;
        duration = 1;
    }

    public CombatEffect(String[] properties) {
        super(properties);
    }

    public CombatEffect(String[] properties, int duration) {
        super(properties);
        this.duration = duration;
    }


    // Abstract Functions
    public abstract void perform(CombatEntity entity);

    // Concrete Member Functions
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

    public String getTriggerMessage() {
        return triggerMessage;
    }

    public void setTriggerMessage(String triggerMessage) {
        this.triggerMessage = triggerMessage;
    }

    public String getCleanupMessage() {
        return cleanupMessage;
    }

    public void setCleanupMessage(String cleanupMessage) {
        this.cleanupMessage = cleanupMessage;
    }
}
