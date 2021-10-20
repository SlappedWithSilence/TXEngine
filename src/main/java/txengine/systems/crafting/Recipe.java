package txengine.systems.crafting;

import txengine.structures.Pair;
import txengine.systems.event.Event;
import txengine.ui.color.Colors;
import txengine.systems.integration.Requirement;
import txengine.main.Manager;
import txengine.systems.inventory.Inventory;

import java.util.*;
import java.util.List;

public class Recipe {

    /* Member Variables */

    private List<Pair<Integer, Integer>> ingredients;
    private List<Pair<Integer, Integer>> products;
    private List<Requirement> requirements;
    private List<Event> events;
    private int id;

    public Recipe() {
        ingredients = new ArrayList<>();
        products = new ArrayList<>();
        requirements = new ArrayList<>();
        events = new ArrayList<>();
    }

    public Recipe(List<Pair<Integer, Integer>> ingredients, List<Pair<Integer, Integer>> products) {
        this.ingredients = ingredients;
        this.products = products;
        requirements = new ArrayList<>();
        events = new ArrayList<>();
    }

    public Recipe(List<Pair<Integer, Integer>> ingredients, List<Pair<Integer, Integer>> products, List<Requirement> requirements, List<Event> events) {
        this.ingredients = ingredients;
        this.products = products;
        this.requirements = requirements;
        this.events = events;
    }

    public Recipe(Recipe recipe) {
        ingredients = recipe.ingredients;
        products = recipe.products;
        requirements = recipe.requirements; // It's ok that this is a reference
        events = new ArrayList<>(recipe.events);
    }


    public boolean hasIngredients(Collection<Pair<Integer, Integer>> itemPairs) {
        return itemPairs.containsAll(ingredients);
    }

    public boolean hasIngredients(Inventory inventory) {
        if (!Requirement.allMet(requirements)) return false;

        for (Pair<Integer, Integer> pair : ingredients) {
            if (inventory.getItemQuantity(pair.getKey()) < pair.getValue()) return false;
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Pair<Integer, Integer> pair : ingredients) sb.append("[").append(Manager.itemHashMap.get(pair.getKey()).getName()).append(": ").append(pair.getValue()).append("]");
        sb.append(" -> ");
        for (Pair<Integer, Integer> pair : products) sb.append("[").append(Manager.itemHashMap.get(pair.getKey()).getName()).append(": ").append(pair.getValue()).append("]");

        return sb.toString();
    }

    public String toFormattedString() {
        StringBuilder sb = new StringBuilder();

        for (Pair<Integer, Integer> pair : ingredients){
            String color;

            if (Manager.player.getInventory().getItemQuantity(pair.getKey()) >= pair.getValue()) color = Colors.GREEN;
            else color = Colors.RED;

            sb.append("[").append(Manager.itemHashMap.get(pair.getKey()).getName()).append(": ").append(color).append(pair.getValue()).append(Colors.RESET).append("]");
        }
        sb.append(" -> ");
        for (Pair<Integer, Integer> pair : products) sb.append("[").append(Manager.itemHashMap.get(pair.getKey()).getName()).append(": ").append(pair.getValue()).append("]");

        return sb.toString();
    }

    public List<Pair<Integer, Integer>> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Pair<Integer, Integer>> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Pair<Integer, Integer>> getProducts() {
        return products;
    }

    public void setProducts(List<Pair<Integer, Integer>> products) {
        this.products = products;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
