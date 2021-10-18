package txengine.systems.conversation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Conversation {
    List<ConversationModule> modules;
    int id;
    int nextModuleId;

    // Constructors

    public Conversation() {
        id = 0;
        nextModuleId = 0;
        modules = new ArrayList<>();
    }

    public Conversation(int id, List<ConversationModule> modules) {
        this.id = id;
        this.nextModuleId = 0;
        this.modules = modules;
    }

    // Methods

    public void converse() {
        nextModuleId = 0;
      while (nextModuleId > -1) {
          nextModuleId = modules.get(nextModuleId).perform();
      }

    }

    // Getters and setters
    public List<ConversationModule> getModules() {
        return modules;
    }

    public void setModules(List<ConversationModule> modules) {
        this.modules = modules;
    }

    public int getIndex() {
        return nextModuleId;
    }

    public void setIndex(int nextModuleId) {
        this.nextModuleId = nextModuleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
