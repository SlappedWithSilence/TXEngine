package txengine.systems.crafting;

import txengine.structures.Pair;
import txengine.systems.event.Event;
import txengine.ui.color.Colors;
import txengine.systems.integration.Requirement;
import txengine.main.Manager;
import txengine.systems.combat.CombatEntity;
import txengine.systems.inventory.Inventory;

import java.util.*;

// Records and manages all the recipies that the player has learned.
public class RecipeManager {

    HashMap<Integer, Recipe> recipeHashMap;
    Set<Recipe> learnedRecipeList;

    public RecipeManager(HashMap<Integer, Recipe> recipeHashMap) {
        this.recipeHashMap = recipeHashMap;
        learnedRecipeList = new HashSet<>();
    }

    public RecipeManager(List<Recipe> recipes) {
        learnedRecipeList = new HashSet<>(recipes);
    }

    /* Member Functions */
    public void addRecipe(Recipe recipe) {
        if (learnedRecipeList.contains(recipe)) {
            return;
        }
        learnedRecipeList.add(recipe);
    }

    // Returns a list of items that the combatEntity has the ingredients to craft
    public List<Recipe> getCraftableRecipes(CombatEntity combatEntity) {
        return learnedRecipeList.stream().filter(recipe -> recipe.hasIngredients(Manager.player.getInventory())).toList();
    }

    public List<String> ingredientsAsStrings(Recipe recipe) {
        List<String> formattedIngredients = new ArrayList<>();

        // Pair key = item id, pair value = item quantity
        for (Pair<Integer, Integer> pair : recipe.getIngredients()) {
            StringBuilder sb = new StringBuilder();

            sb.append(Manager.itemManager.get_instance(pair.getKey())).append(": ");

            // Make the item quantity green if it is satisfied, red otherwise
            if (Manager.player.getInventory().getItemQuantity(pair.getKey()) < pair.getValue()) {
                sb.append(Colors.RED);
                sb.append("(").append(Manager.player.getInventory().getItemQuantity(pair.getKey())).append(")"); // Append the current quantity
            }
            else sb.append(Colors.GREEN);
            sb.append(pair.getValue()).append("x").append(Colors.RESET);

            formattedIngredients.add(sb.toString());
        }

        return formattedIngredients;
    }

    // Attempt to craft. Return false and print a warning to the user if it can't be done.
    public boolean craft(Recipe recipe, Inventory inventory) {

        if (!Requirement.allMet(recipe.getRequirements())) {
            System.out.println("You haven't met all the requirements to craft!");
            Requirement.asStrings(recipe.getRequirements()).forEach(System.out::println);
            return false;
        }

        if (!recipe.hasIngredients(inventory)) {
            System.out.println("You don't have the right ingredients to craft!");
            ingredientsAsStrings(recipe).forEach(System.out::println);
            return false;
        }

        if (inventory.getUsage() - recipe.getIngredients().size() > inventory.getCapacity() - recipe.getProducts().size()) {
            System.out.println("You don't have enough space to craft! You need " + (inventory.getUsage() - recipe.getIngredients().size() - inventory.getCapacity() - recipe.getProducts().size()) + " spaces.");
            return false;
        }

        for (Pair<Integer, Integer> pair : recipe.getIngredients()) {
            inventory.consumeQuantity(pair.getKey(), pair.getValue());
        }

        for (Pair<Integer, Integer> pair : recipe.getProducts()) {
            inventory.addItem(pair.getKey(), pair.getValue());
        }

        for (Event e : recipe.getEvents()) e.perform();

        return true;
    }

    public void learn(Recipe r) {
        learnedRecipeList.add(r);
    }

    public void learn(int id) { learnedRecipeList.add(recipeHashMap.get(id));}

    /* Accessor Functions */

    public Set<Recipe> getLearnedRecipeList() {
        return learnedRecipeList;
    }
    public Collection<Recipe> getRecipes() {return recipeHashMap.values();}

    public void setLearnedRecipeList(Set<Recipe> learnedRecipeList) {
        this.learnedRecipeList = learnedRecipeList;
    }


}
