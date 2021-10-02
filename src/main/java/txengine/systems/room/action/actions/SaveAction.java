package txengine.systems.room.action.actions;

import txengine.io.save.SaveManager;
import txengine.systems.room.action.Action;

import java.util.ArrayList;

public class SaveAction extends Action {

    public SaveAction() {
        super("Record your journey in your notebook",
                "You write down the progress you've made in your journey.",
                null, true, -1, false, new ArrayList<>());
    }

    @Override
    public int perform() {

        SaveManager.getInstance().save();

        return super.enableOnComplete();
    }
}
