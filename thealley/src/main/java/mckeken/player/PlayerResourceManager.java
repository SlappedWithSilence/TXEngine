package mckeken.player;

import mckeken.io.LogUtils;

import java.util.HashMap;


/**************************************************************************************************************
 *  This class contains all the functions needed to manage player resources. This may be HP, MP, Stamina, etc *
 **************************************************************************************************************/
public class PlayerResourceManager {

    /* Member variables */
    HashMap<String, Integer[]> resources; // A map that contains a String mapped to an array of two int. The String is the name of the resource, arr[0] is the max quantity of the resource, and arr[1] is the current quantity of the resource


    /* Constructors */
    public PlayerResourceManager() {

    }

    public PlayerResourceManager(HashMap<String, Integer[]> resources) {
        this.resources = resources;
    }

    /* Member Methods */

    // Register a new resource into the manager
    public int registerResource(String resourceName, Integer resourceMax, Integer resourceStartValue) {
        Integer[] vals = {resourceMax, resourceStartValue};

        if (resources.containsKey(resourceName)) {
            LogUtils.error("Resource already registered!");
            return -1;
        }

        resources.put(resourceName, vals);
        return 0;

    }

    // Set the value of an existing resource
    public void setResource(String resourceName, int resourceQuantity) {

        if (!resources.containsKey(resourceName)) {
            LogUtils.error("Resource " + resourceName + " does not exist!");
            return;
        }

        Integer[] vals = resources.get(resourceName);
        vals[1] = resourceQuantity;

        resources.put(resourceName, vals);
    }

}
