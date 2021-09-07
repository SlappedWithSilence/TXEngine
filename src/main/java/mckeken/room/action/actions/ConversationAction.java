package mckeken.room.action.actions;

import mckeken.main.Manager;
import mckeken.room.action.Action;

public class ConversationAction extends Action {

    protected int conversationID;

    public ConversationAction() {

    }

    public ConversationAction(String menuName, String text, String[] properties, boolean enabled, int unlockIndex) {
        super(menuName, text, properties, enabled, unlockIndex);
    }

    @Override
    public int perform() {
        conversationID = Integer.parseInt(super.properties[0]);

        Manager.conversationList.get(conversationID).converse();

        return super.unlockIndex;
    }
}
