package txengine.systems.room.action.actions;

import txengine.systems.integration.Requirement;
import txengine.systems.room.action.Action;

import java.util.List;

public class DialogAction extends Action {

    public DialogAction() {
        super();
    }

    public DialogAction(String menuName, String text, String[] properties, boolean enabled, int unlockIndex, boolean hideOnUse, List<Requirement> requirements) {
        super(menuName, text, properties, enabled, unlockIndex, hideOnUse, requirements);
    }

    @Override
    public int perform() {
        System.out.println(properties[0]);
        return super.enableOnComplete();
    }
}
