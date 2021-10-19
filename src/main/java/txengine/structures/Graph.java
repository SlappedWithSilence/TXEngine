package txengine.structures;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.*;

public class Graph<T> {

    Map<T, Set<T>> nodeData;

    public Graph() {
        nodeData = new HashMap<>();
    }

    public void addNode(T s) {
        if (nodeData.containsKey(s)) {
            throw new KeyAlreadyExistsException();
        }

        nodeData.put(s, new HashSet<>());
    }

    public void addConnection(T nodeA, T nodeB) {
        if (!nodeData.containsKey(nodeA) || !nodeData.containsKey(nodeB)) {
            throw new NoSuchElementException();
        }

        nodeData.get(nodeA).add(nodeB);
        nodeData.get(nodeB).add(nodeA);
    }

    public void removeConnection(T nodeA, T nodeB) {
        if (!nodeData.containsKey(nodeA) || !nodeData.containsKey(nodeB)) {
            throw new NoSuchElementException();
        }

        nodeData.get(nodeA).remove(nodeB);
        nodeData.get(nodeB).remove(nodeA);
    }

    public Set<T> getConnections(T nodeA) {
        if (!nodeData.containsKey(nodeA)) {
            throw new NoSuchElementException();
        }

        return nodeData.get(nodeA);
    }

    public Set<T> getNodes() {
        return nodeData.keySet();
    }

}
