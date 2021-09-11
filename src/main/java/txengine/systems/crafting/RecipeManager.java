package txengine.systems.crafting;

import java.util.*;

public class RecipeManager {

    HashMap<Integer,Recipe> recipeList;

    public RecipeManager() {
        recipeList = new HashMap<>();
    }

    public RecipeManager(Map<Integer,Recipe> recipes) {
        recipeList = new HashMap<>(recipes);
    }

}
