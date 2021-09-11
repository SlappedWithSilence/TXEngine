package txengine.systems.room.action.actions;

import txengine.integration.Requirement;
import txengine.systems.room.action.Action;

import java.util.List;

public class DialogueAction extends Action {

    public DialogueAction() {
        super();
    }

    public DialogueAction(String menuName, String text, String[] properties, boolean enabled, int unlockIndex, List<Requirement> requirements) {
        super(menuName, text, properties, enabled, unlockIndex, requirements);
    }

    @Override
    public int perform() {
        System.out.println(properties[0]);
        return super.enableOnComplete();
    }
}
