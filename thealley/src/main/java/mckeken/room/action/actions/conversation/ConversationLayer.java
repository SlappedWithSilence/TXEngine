package mckeken.room.action.actions.conversation;

import mckeken.io.LogUtils;

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
    Event[][] events;
    Integer[] targets;

    public ConversationModule() {

    }

    public ConversationModule(String NPCText, String[] options, Event[][] events, Integer[] targets) {
        this.NPCText = NPCText;
        this.options = options;
        this.events = events;
        this.targets = targets;

    }

    public int perform() {
        System.out.println(getNPCText()); // Print the npc's text
        LogUtils.numberedList(options);   // Print the user's repsonses
        int userChoice = LogUtils.getNumber(0, options.length); // Get the user's choice
        for (Event e : events[userChoice]) e.perform();                   // Perform any game events related to the choice
        return targets[userChoice];                                       // Return the index of the next conversation module in the next layer
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

    public Event[][] getEvents() {
        return events;
    }

    public void setEvents(Event[][] events) {
        this.events = events;
    }

    public Integer[] getTargets() {
        return targets;
    }

    public void setTargets(Integer[] targets) {
        this.targets = targets;
    }
}
