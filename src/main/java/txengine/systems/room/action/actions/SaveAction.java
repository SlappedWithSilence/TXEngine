package txengine.systems.room.action.actions;

import txengine.io.Save;
import txengine.systems.room.action.Action;

public class SaveAction extends Action {

    public SaveAction() {
        super("Record your journey in your notebook",
                "You write down the progress you've made in your journey.",
                null, true, -1);
    }

    @Override
    public int perform() {

        Save.saveInventory();
        Save.SavePlayer();

        return super.enableOnComplete();
    }
}
