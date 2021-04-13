package mckeken.room.action.actions.conversation;

public class ConversationLayer {
    ConversationModule[] modules;

    public ConversationLayer() {

    }

    public ConversationLayer(ConversationModule[] modules) {
        this.modules = modules;
    }

    public ConversationModule[] getModules() {
        return modules;
    }

    public void setModules(ConversationModule[] modules) {
        this.modules = modules;
    }
}

class ConversationModule {
    String NPCText;
    String[] options;
    Event[] events;
    Integer[] targets;

    public ConversationModule() {

    }

    public ConversationModule(String NPCText, String[] options, Event[] events, Integer[] targets) {
        this.NPCText = NPCText;
        this.options = options;
        this.events = events;
        this.targets = targets;

    }

    public String getNPCText() {
        return NPCText;
    }

    public void setNPCText(String NPCText) {
        this.NPCText = NPCText;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }

    public Integer[] getTargets() {
        return targets;
    }

    public void setTargets(Integer[] targets) {
        this.targets = targets;
    }
}
