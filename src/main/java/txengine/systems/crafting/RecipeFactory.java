package txengine.systems.crafting;

import txengine.integration.Requirement;

import java.util.AbstractMap;
import java.util.List;

public class RecipeFactory {

    public static Recipe build(List<AbstractMap.SimpleEntry<Integer, Integer>> ingredients, List<AbstractMap.SimpleEntry<Integer, Integer>> products, List<Requirement> requirements) {
        return new Recipe(ingredients, products, requirements);
    }

}
