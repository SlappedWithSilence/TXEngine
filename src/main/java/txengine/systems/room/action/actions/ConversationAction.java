package txengine.systems.room.action.actions;

import txengine.systems.integration.Requirement;
import txengine.main.Manager;
import txengine.systems.room.action.Action;

import java.util.List;

public class ConversationAction extends Action {

    protected int conversationID;

    public ConversationAction() {

    }

    public ConversationAction(String menuName, String text, String[] properties, boolean hidden, int unlockIndex, boolean hideOnUse, List<Requirement> requirements) {
        super(menuName, text, properties, hidden, unlockIndex, hideOnUse, requirements);
    }


    @Override
    public int perform() {
        conversationID = Integer.parseInt(super.properties[0]);

        Manager.conversationHashMap.get(conversationID).converse();

        return super.unhideIndex;
    }
}
