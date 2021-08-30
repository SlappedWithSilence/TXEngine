package mckeken.combat;

import mckeken.combat.ability.Ability;
import mckeken.item.Item;
import mckeken.room.action.actions.conversation.events.ItemEvent;

import java.util.AbstractMap;
import java.util.Optional;

// In combat, a CombatEntity will have to make choices about what it is going to do. This may involve choosing an ability to use or
// an item to use. Either way, some logic is needed to make that choice. Note that the ability returned in this method MUST contain a valid target. If it doesn't, a crash will occur.
interface CombatAgency {
    AbstractMap.SimpleEntry<Ability, Item> makeChoice(Optional<CombatEngine> engine);
}
