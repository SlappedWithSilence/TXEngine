package mckeken.item.effect.effects;

import mckeken.item.effect.Effect;
import mckeken.main.*;

/* Effect Functionality:
 * Number of properties: 3
 * Property Descriptions:
 * - 0: stamina modifier. This is the quantity by which the player's stamina will change. May be positive or negative.
 * - 1: Level multiplier. This is the quantity by which property 0 will scale with the player's level. May only be positive.
 * - 2: Flat bonus. This is a static quantity that is added to the player's stamina. May be positive or negative.
 */


public class StaminaEffect extends Effect {
    //*** Constants ***//
    final static int DEFAULT_HP_CHANGE = 10;


    // Default constructor.
    public StaminaEffect () {
        super(new String[] {""+DEFAULT_HP_CHANGE, ""+1, ""+0}); // Configure the effect to have a maximum of 3 properties

    }

    public StaminaEffect (int modifier, int multiplier, int bonus) {
        super(new String[] {""+modifier, ""+multiplier, ""+bonus});
    }

    public void perform() {
        Manager.player.getResourceManager().setResource("Stamina",
                Manager.player.getResourceManager().getResourceQuantity("Stamina") + (Integer.getInteger(super.properties[0]) * Integer.getInteger(super.properties[1]) + Integer.getInteger(super.properties[2])) );
    }

}