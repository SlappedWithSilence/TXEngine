package txengine.systems.item;

import txengine.ui.LogUtils;
import txengine.ui.Out;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ItemManager {

    private HashMap<Integer, Item> itemHashMap;

    public ItemManager(HashMap<Integer, Item> itemHashMap) {
        this.itemHashMap = itemHashMap;
    }

    /*** Accessor Methods ***/
public Set<Integer> get_all_ids() {
    return itemHashMap.keySet();
}

    public boolean is_valid_id(int id) {
        return itemHashMap.containsKey(id) && itemHashMap.get(id) != null;
    }

    public Item get_instance(int id) {
        if (!is_valid_id(id)) {
            Out.error("No such item with id" + id + " exists!");
            return null;
        }

        return itemHashMap.get(id);
    }

    public HashMap<Integer, Item> getItemHashMap() {
        return itemHashMap;
    }

    public void setItemHashMap(HashMap<Integer, Item> itemHashMap) {
        this.itemHashMap = itemHashMap;
    }
}
