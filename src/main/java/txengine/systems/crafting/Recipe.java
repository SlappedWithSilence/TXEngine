package txengine.systems.crafting;

import txengine.integration.Requirement;
import txengine.systems.inventory.Inventory;

import java.util.*;

public class Recipe {

    /* Member Variables */

    private List<AbstractMap.SimpleEntry<Integer, Integer>> ingredients;
    private List<AbstractMap.SimpleEntry<Integer, Integer>> products;
    private List<Requirement> requirements;

    public Recipe() {
        ingredients = new ArrayList<>();
        products = new ArrayList<>();
        requirements = new ArrayList<>();
    }

    public Recipe(List<AbstractMap.SimpleEntry<Integer, Integer>> ingredients, List<AbstractMap.SimpleEntry<Integer, Integer>> products) {
        this.ingredients = ingredients;
        this.products = products;
        requirements = new ArrayList<>();
    }

    public Recipe(List<AbstractMap.SimpleEntry<Integer, Integer>> ingredients, List<AbstractMap.SimpleEntry<Integer, Integer>> products, List<Requirement> requirements) {
        this.ingredients = ingredients;
        this.products = products;
        this.requirements = requirements;
    }

    public Recipe(Recipe recipe) {
        ingredients = recipe.ingredients;
        products = recipe.products;
        requirements = recipe.requirements; // It's ok that this is a reference
    }


    public boolean hasIngredients(Collection<AbstractMap.SimpleEntry<Integer, Integer>> itemPairs) {
        return itemPairs.containsAll(ingredients);
    }

    public boolean hasIngredients(Inventory inventory) {
        if (!Requirement.allMet(requirements)) return false;

        for (AbstractMap.SimpleEntry<Integer, Integer> pair : ingredients) {
            if (inventory.getItemQuantity(pair.getKey()) < pair.getValue()) return false;
        }

        return true;
    }

    public List<AbstractMap.SimpleEntry<Integer, Integer>> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<AbstractMap.SimpleEntry<Integer, Integer>> ingredients) {
        this.ingredients = ingredients;
    }

    public List<AbstractMap.SimpleEntry<Integer, Integer>> getProducts() {
        return products;
    }

    public void setProducts(List<AbstractMap.SimpleEntry<Integer, Integer>> products) {
        this.products = products;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
    }
}
