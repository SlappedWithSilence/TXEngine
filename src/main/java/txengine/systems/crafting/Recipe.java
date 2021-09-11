package txengine.systems.crafting;

import txengine.systems.inventory.Inventory;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Recipe {

    /* Member Variables */

    private List<AbstractMap.SimpleEntry<Integer, Integer>> ingredients;
    private List<AbstractMap.SimpleEntry<Integer, Integer>> products;

    public Recipe() {
        List<AbstractMap.SimpleEntry<Integer, Integer>> ingredients = new ArrayList<>();
        List<AbstractMap.SimpleEntry<Integer, Integer>> products = new ArrayList<>();
    }

    public Recipe(List<AbstractMap.SimpleEntry<Integer, Integer>> ingredients, List<AbstractMap.SimpleEntry<Integer, Integer>> products) {
        this.ingredients = ingredients;
        this.products = products;
    }

    public Recipe(Recipe recipe) {
        ingredients = recipe.ingredients;
        products = recipe.products;
    }


    public boolean hasIngredients(Collection<AbstractMap.SimpleEntry<Integer, Integer>> itemPairs) {
        return itemPairs.containsAll(ingredients);
    }

    public boolean hasIngredients(Inventory inventory) {
        for (AbstractMap.SimpleEntry<Integer, Integer> pair : ingredients) {
            if (inventory.getItemQuantity(pair.getKey()) < pair.getValue()) return false;
        }

        return true;
    }
}
