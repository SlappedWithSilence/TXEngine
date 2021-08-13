package mckeken.combat.ability;

import mckeken.item.effect.Effect;

import java.util.ArrayList;

/*
 An ability is a "move" an entity can use during combat. An Ability may do the following:
 - Deal Damage
 - Remove Damage
 - Generate an Effect
 - Remove an Effect
 - Consume a resource
 - Restore a resource

 An ability has a discrete target mode. Of these modes, the following are possible:
 - Single Target
 - All entities
 - All friendly entities
 - All enemy entities

 An ability that targets multiple entities assigns its effects to all targeted entities.
 */
public class Ability {
    TargetMode targetMode;
    String name;
    ArrayList<Effect> effects;
    int damage;
}
