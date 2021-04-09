package mckeken.room.action.actions;

import mckeken.room.action.Action;

public class DialogueAction extends Action {

    public DialogueAction() {
        super();
    }

    public DialogueAction(String menuName, String text, String[] properties, boolean enabled, int unlockIndex) {
        super(menuName, text, properties, enabled, unlockIndex);
    }

    @Override
    public int perform() {
        System.out.println(properties[0]);
        return super.enableOnComplete();
    }
}
