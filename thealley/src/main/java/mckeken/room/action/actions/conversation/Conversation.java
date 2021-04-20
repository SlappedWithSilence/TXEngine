package mckeken.room.action.actions.conversation;

import mckeken.io.LogUtils;

import java.util.ArrayList;
import java.util.Iterator;

public class Conversation {
    ArrayList<ConversationLayer> layers;
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

    public Conversation(int id, int index, ArrayList<ConversationLayer> layers) {
        this.id = id;
        this.index = index;
        this.layers = layers;
        layerIterator = layers.iterator();
    }

    // Methods

    public void converse() {

      while (layerIterator.hasNext()) {
          ConversationLayer layer = layerIterator.next();
          index = layer.getModules()[index].perform();
          if (index < 0) break;
      }
    }

    // Getters and setters

    private ConversationLayer nextLayer() {
        return layerIterator.next();
    }

    private boolean hasNext() {
        return layerIterator.hasNext();
    }

    public ArrayList<ConversationLayer> getLayers() {
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
