package txengine.systems.conversation;

import txengine.ui.component.Components;
import txengine.ui.LogUtils;
import txengine.systems.event.Event;

public class ConversationModule {
    int id;
    String NPCText;
    String[] options;
    Event[][] events;
    Integer[] targets;

    public ConversationModule() {
        id = 0;
        NPCText = "";
        options = new String[0];
        events = new Event[0][0];
        targets = new Integer[0];
    }

    public ConversationModule(int id,String NPCText, String[] options, Event[][] events, Integer[] targets) {
        this.id = id;
        this.NPCText = NPCText;
        this.options = options;
        this.events = events;
        this.targets = targets;

    }

    public int perform() {
        System.out.println(getNPCText()); // Print the npc's text
        Components.numberedList(options);   // Print the user's repsonses
        int userChoice = LogUtils.getNumber(0, options.length); // Get the user's choice
        for (Event e : events[userChoice])
            e.perform();                   // Perform any game events related to the choice
        return targets[userChoice];                                       // Return the index of the next conversation module in the next layer
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
