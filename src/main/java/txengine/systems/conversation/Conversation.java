package txengine.systems.conversation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Conversation {
    List<ConversationLayer> layers;
    int id;
    int index;
    Iterator<ConversationLayer> layerIterator;

    // Constructors

    public Conversation() {
        id = 0;
        index = 0;
        layers = new ArrayList<ConversationLayer>();
        layerIterator = layers.iterator();
    }

    public Conversation(int id, List<ConversationLayer> layers) {
        this.id = id;
        this.index = 0;
        this.layers = layers;
        layerIterator = layers.iterator();
    }

    // Methods

    public void converse() {

      while (layerIterator.hasNext()) {
          ConversationLayer layer = layerIterator.next();
          index = layer.getModules()[index].perform();
          if (index < 0) break; // End the conversation if the user has reached a branch that doesn't continue
      }

      index = 0; // Reset the index. This prevents crashes when the user tries to re-enter a conversation
      layerIterator = layers.iterator();
    }

    // Getters and setters

    private ConversationLayer nextLayer() {
        return layerIterator.next();
    }

    private boolean hasNext() {
        return layerIterator.hasNext();
    }

    public List<ConversationLayer> getLayers() {
        return layers;
    }

    public void setLayers(ArrayList<ConversationLayer> layers) {
        this.layers = layers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
