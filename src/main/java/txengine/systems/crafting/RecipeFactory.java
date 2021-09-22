package txengine.systems.crafting;

import txengine.systems.event.Event;
import txengine.systems.integration.Requirement;

import java.util.AbstractMap;
import java.util.List;

public class RecipeFactory {

    public static Recipe build(List<AbstractMap.SimpleEntry<Integer, Integer>> ingredients, List<AbstractMap.SimpleEntry<Integer, Integer>> products, List<Requirement> requirements, List<Event> events) {
        return new Recipe(ingredients, products, requirements, events);
    }

}
