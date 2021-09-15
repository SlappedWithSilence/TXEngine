package txengine.systems.crafting;

import txengine.color.Colors;
import txengine.systems.integration.Requirement;
import txengine.main.Manager;
import txengine.systems.inventory.Inventory;

import java.util.*;
import java.util.List;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (AbstractMap.SimpleEntry<Integer, Integer> pair : ingredients) sb.append("[" + Manager.itemHashMap.get(pair.getKey()).getName() + ": " + pair.getValue() + "]");
        sb.append(" -> ");
        for (AbstractMap.SimpleEntry<Integer, Integer> pair : products) sb.append("[" + Manager.itemHashMap.get(pair.getKey()).getName() + ": " + pair.getValue() + "]");

        return sb.toString();
    }

    public String toFormattedString() {
        StringBuilder sb = new StringBuilder();

        for (AbstractMap.SimpleEntry<Integer, Integer> pair : ingredients){
            String color;

            if (Manager.player.getInventory().getItemQuantity(pair.getKey()) >= pair.getValue()) color = Colors.GREEN;
            else color = Colors.RED;

            sb.append("[").append(Manager.itemHashMap.get(pair.getKey()).getName()).append(": ").append(color).append(pair.getValue()).append(Colors.RESET).append("]");
        }
        sb.append(" -> ");
        for (AbstractMap.SimpleEntry<Integer, Integer> pair : products) sb.append("[").append(Manager.itemHashMap.get(pair.getKey()).getName()).append(": ").append(pair.getValue()).append("]");

        return sb.toString();
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
