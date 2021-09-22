package txengine.systems.event.events;

import txengine.main.Manager;
import txengine.systems.event.Event;

public class RecipeEvent extends Event {

    public RecipeEvent() {

    }

    @Override
    public void execute() {
        Manager.player.getRecipeManager().learn(Integer.parseInt(getProperties()[0]));
    }

    @Override
    public String print() {
        return "You've learned a new recipe!";
    }


}
