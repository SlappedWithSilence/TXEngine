package mckeken.room.action.actions.conversation;

import mckeken.io.LogUtils;

public class ConversationLayer {

    /* Member variables */
    ConversationModule[] modules;


    /* Constructors */
    public ConversationLayer() {

    }

    /* Member methods */
    public ConversationLayer(ConversationModule[] modules) {
        this.modules = modules;
    }

    public ConversationModule[] getModules() {
        return modules;
    }

    public ConversationModule getModule(int index) {
        return modules[index];
    }

    public void setModules(ConversationModule[] modules) {
        this.modules = modules;
    }
}

