package txengine.systems.combat;

import txengine.structures.Pair;
import txengine.systems.ability.Ability;
import txengine.systems.item.Item;

import java.util.AbstractMap;

// In combat, a CombatEntity will have to make choices about what it is going to do. This may involve choosing an ability to use or
// an item to use. Either way, some logic is needed to make that choice. Note that the ability returned in this method MUST contain a valid target. If it doesn't, a crash will occur.
interface CombatAgency {
    Pair<Ability, Item> makeChoice(CombatEngine engine);
}
