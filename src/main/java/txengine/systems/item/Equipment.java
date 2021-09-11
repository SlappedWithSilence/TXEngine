package txengine.systems.item;

import txengine.systems.combat.combatEffect.CombatEffect;

import java.util.ArrayList;
import java.util.List;

public class Equipment extends Item {

    /* Member Variables */
    private List<CombatEffect> preCombatEffects;

    /* Constructors */
    public Equipment() {
        super();
        preCombatEffects = new ArrayList<>();
    }

    // Value constructor, no effects
    public Equipment(String name, String description, int id, int value, int maxStacks) {
        super(name, description, id, value, maxStacks);
        preCombatEffects = new ArrayList<>();
    }

    // Value constructor, effects
    public Equipment(String name, String description, int id, int value, int maxStacks, List<CombatEffect> preCombatEffects) {
        super(name, description, id, value, maxStacks);
        setPreCombatEffects(preCombatEffects);
    }

    public Equipment(Equipment equipment) {
        super(equipment);
        setPreCombatEffects(equipment.preCombatEffects);
    }
    /* Member Functions*/


    /* Accessor Methods */

    public final List<CombatEffect> getPreCombatEffects() {
        return preCombatEffects;
    }

    public void setPreCombatEffects(List<CombatEffect> preCombatEffects) {
        this.preCombatEffects = new ArrayList<>(preCombatEffects);
    }
}
