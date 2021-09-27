package txengine.systems.conversation;

public class ConversationLayer {

    /* Member variables */
    ConversationModule[] modules;


    /* Constructors */
    public ConversationLayer() {

    }

    public ConversationLayer(ConversationModule[] modules) {
        this.modules = modules;
    }

    /* Member methods */
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

