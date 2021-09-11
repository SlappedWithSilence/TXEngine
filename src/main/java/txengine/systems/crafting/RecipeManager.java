package txengine.systems.crafting;

import txengine.color.Colors;
import txengine.io.LogUtils;
import txengine.main.Manager;
import txengine.systems.combat.CombatEntity;

import java.util.*;

// Records and manages all the recipies that the player has learned.
public class RecipeManager {

    List<Recipe> recipeList;

    public RecipeManager() {
        recipeList = new ArrayList<>();
    }

    public RecipeManager(List<Recipe> recipes) {
        recipeList = new ArrayList<>(recipes);
    }

    /* Member Functions */
    public void addRecipe(Recipe recipe) {
        if (recipeList.contains(recipe)) {
            return;
        }
        recipeList.add(recipe);
    }

    // Returns a list of items that the combatEntity has the ingredients to craft
    public List<Recipe> getCraftableRecipes(CombatEntity combatEntity) {
        return recipeList.stream().filter(recipe -> recipe.hasIngredients(Manager.player.getInventory())).toList();
    }

    public List<String> ingredientsAsStrings(Recipe recipe) {
        List<String> formattedIngredients = new ArrayList<>();

        // Pair key = item id, pair value = item quantity
        for (AbstractMap.SimpleEntry<Integer, Integer> pair : recipe.getIngredients()) {
            StringBuilder sb = new StringBuilder();

            sb.append(Manager.itemList.get(pair.getKey())).append(": ");

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


    /* Accessor Functions */

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }


}
