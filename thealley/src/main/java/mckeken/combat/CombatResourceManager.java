package mckeken.combat;

import mckeken.io.LogUtils;

import java.util.HashMap;
import java.util.TreeMap;


/*********************************************************************************************************************
 *  This class contains all the functions needed to manage combat entity resources. This may be HP, MP, Stamina, etc *
 *********************************************************************************************************************/
public class CombatResourceManager {

    /* Member variables */
    //HashMap<String, Integer[]> resources; // A map that contains a String mapped to an array of two int. The String is the name of the resource, arr[0] is the max quantity of the resource, and arr[1] is the current quantity of the resource
    TreeMap<String, Integer[]> resources;

    /* Constructors */
    public CombatResourceManager(TreeMap<String, Integer[]> treeMap) {
        resources = treeMap;
    }

    public CombatResourceManager(HashMap<String, Integer[]> map) {
        resources = new TreeMap<>(map);
    }

    public CombatResourceManager() {
        resources = new TreeMap<>();
    }

    /* Member Methods */

    public TreeMap<String, Integer[]> getResources() {
        return resources;
    }

    // Register a new resource into the manager
    public boolean registerResource(String resourceName, Integer resourceMax, Integer resourceStartValue) {
        Integer[] vals = {resourceMax, resourceStartValue};

        if (resources.containsKey(resourceName)) {
            LogUtils.error("Resource already registered!\n");
            return false;
        }

        resources.put(resourceName, vals);
        return true;

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

    // Increments the value of an existing resource up to the resource's max
    public int incrementResource(String resourceName, int increment) {
        if (!resources.containsKey(resourceName)) {
            LogUtils.error("Resource " + resourceName + " does not exist!");
            return -1;
        }

        Integer[] vals = resources.get(resourceName);

        if (vals[1] + increment > vals[0]) { // if increment + current_resource_value > max_value
            vals[1] = vals[0]; // current_resource_value = max_value
            int amount_healed = vals[0] -vals[1]; //amounted_healed = max_value - current_value
            resources.put(resourceName, vals); // commit changes to resources
            return amount_healed;
        } else {
            vals[1] = vals[1] + increment;
            resources.put(resourceName, vals); // commit changes to resources
            return increment;
        }
    }

    // Decrements the value of an existing resource down to at least zero.
    public int decrementResource(String resourceName, int decrement) {
        if (!resources.containsKey(resourceName)) {
            LogUtils.error("Resource " + resourceName + " does not exist!");
            return -1;
        }

        Integer[] vals = resources.get(resourceName);

        if (vals[0] - decrement < 0) {
            int amount_removed = vals[0];
            vals[1] = 0;
            resources.put(resourceName, vals); // commit changes to resources
            return amount_removed;
        } else {
            vals[1] = vals[1] - decrement;
            resources.put(resourceName, vals); // commit changes to resources
            return decrement;
        }
    }

    // Simulates consuming exactly X of a resource. If there is not enough of the resource, none will be consumed and the function will return false.
    public boolean consumeResource(String resourceName, int resourceQuantity) {
        if (!resources.containsKey(resourceName)) {
            LogUtils.error("Resource " + resourceName + " does not exist!");
            return false;
        }

        Integer[] vals = resources.get(resourceName);

        if (vals[1] < resourceQuantity) return false;

        vals[1] = vals[1] - resourceQuantity;
        resources.put(resourceName, vals); // commit changes to resources

        return true;
    }

    public int getResourceQuantity(String resourceName) {
        if (!resources.containsKey(resourceName)) {
            LogUtils.error("Resource " + resourceName + " does not exist!");
            return -1;
        }

        return resources.get(resourceName)[1];
    }

    public int numberOfResources() {
        return resources.size();
    }

}
