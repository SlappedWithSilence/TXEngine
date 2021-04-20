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

