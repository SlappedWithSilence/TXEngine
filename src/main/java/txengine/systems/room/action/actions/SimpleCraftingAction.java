package txengine.systems.room.action.actions;

import txengine.ui.component.Components;
import txengine.ui.component.LogUtils;
import txengine.main.Manager;
import txengine.systems.crafting.Recipe;
import txengine.systems.room.action.Action;

public class SimpleCraftingAction extends Action {

    @Override
    public int perform() {
        Components.header("Crafting");
        String[] options = new String[] {"Craft something", "View all recipes"};
        Components.numberedList(options);
        int userChoice = LogUtils.getNumber(-1,1);

        switch (userChoice) {
            case -1 -> {}
            case 0 -> {
                Components.header("Choose a Recipe");
                System.out.println("Which recipe would you like to craft? (-1 to choose none)");
                Components.numberedList(Manager.player.getRecipeManager().getCraftableRecipes(Manager.player).stream().map(Recipe::toString).toList());
                int recipeChoice = LogUtils.getNumber(-1, Manager.player.getRecipeManager().getCraftableRecipes(Manager.player).size() - 1);

                if (recipeChoice < 0) break;

                Manager.player.getRecipeManager().craft(Manager.player.getRecipeManager().getCraftableRecipes(Manager.player).get(recipeChoice), Manager.player.getInventory());
            }
            case 1 -> {
                Components.header("All Learned Recipes");
                Components.numberedList(Manager.player.getRecipeManager().getRecipeList().stream().map(Recipe::toFormattedString).toList());
            }
        }

        return unhideIndex;
    }
}
