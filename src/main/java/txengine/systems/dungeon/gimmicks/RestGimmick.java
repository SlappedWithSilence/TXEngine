package txengine.systems.dungeon.gimmicks;

import txengine.main.Manager;
import txengine.systems.conversation.Conversation;
import txengine.systems.conversation.ConversationModule;
import txengine.systems.dungeon.Dungeon;
import txengine.systems.dungeon.DungeonGimmick;
import txengine.systems.event.Event;
import txengine.systems.event.events.ResourceEvent;
import txengine.systems.room.action.Action;
import txengine.systems.room.action.actions.ConversationAction;
import txengine.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class RestGimmick extends DungeonGimmick {
    public RestGimmick(Dungeon owner) {
        super(owner);
        type = Type.UNLOCKED;
    }

    @Override
    public List<Action> get() {
        Conversation conversation = new Conversation();
        ConversationModule mainModule = new ConversationModule();
        conversation.setId(-3);
        // Get resource parameters
        String resourceName = Utils.selectRandom(Manager.playerResourceMap.keySet().toArray(new String[0]), null);
        String percentage = "0." + Utils.randomInt(10, 35);
        // Set module config options
        mainModule.setNPCText("You approach the fountain");
        mainModule.setOptions(new String[] {"Drink from the fountain"});
        mainModule.setEvents(new Event[1][1]);
        mainModule.getEvents()[0][0] = new ResourceEvent(new String[] {resourceName, percentage});
        mainModule.setTargets(new Integer[]{-1});
        mainModule.setId(0);
        // Add modules to conversation
        List<ConversationModule> modules = new ArrayList<>();
        modules.add(mainModule);
        conversation.setModules(modules);
        // Add conversation to map in the temp spot
        Manager.conversationHashMap.put(-3, conversation);
        // Generate a new Conversation action that reads the conversation we just created
        ConversationAction ca = new ConversationAction("Approach mysterious fountain",
                                                        "",
                                                        new String[]{"-3"},
                                                        false,
                                                        -1,
                                                        true,
                                                         new ArrayList<>());
        List<Action> actions = new ArrayList<>();
        actions.add(ca);
        return actions;
    }
}
