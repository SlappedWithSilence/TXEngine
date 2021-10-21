package txengine.systems.combat;

import com.rits.cloning.Cloner;
import txengine.structures.Pair;
import txengine.ui.LogUtils;
import txengine.main.Manager;
import txengine.ui.Out;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;


/*********************************************************************************************************************
 *  This class contains all the functions needed to manage combat entity resources. This may be HP, MP, Stamina, etc *
 *********************************************************************************************************************/
public class CombatResourceManager {

    /* Member variables */
    TreeMap<String, Integer[]> resources; // A map that contains a String mapped to an array of two int. The String is the name of the resource, arr[0] is the max quantity of the resource, and arr[1] is the current quantity of the resource

    /* Constructors */
    public CombatResourceManager(TreeMap<String, Integer[]> treeMap) {
        resources = treeMap;
    }

    public CombatResourceManager(HashMap<String, Integer[]> map) {
        resources = new TreeMap<>(map);
    }

    public CombatResourceManager() {
        if (Manager.playerResourceMap != null) {
            Cloner cloner = new Cloner();
            resources = new TreeMap<>(cloner.deepClone(Manager.playerResourceMap));
        } else {
            resources = new TreeMap<>();
        }

    }

    public CombatResourceManager(CombatResourceManager resourceManager) {
        Cloner cloner = new Cloner();
        resources = cloner.deepClone(resourceManager.resources);
    }

    /* Member Methods */

    // Returns the percentage of the resource's remaining value
    public double resourcePercentage(String resourceName) {
        return ((double) resources.get(resourceName)[1]) / ((double) resources.get(resourceName)[0]); //  Return resource.current/resource.max
    }

    // Test if a resource is available at a given quantity. If not, return false.
    public boolean testResource(String resourceName, Integer resourceQuantity) {

        if (!resources.containsKey(resourceName)) return false;

        return getResourceQuantity(resourceName) >= resourceQuantity;
    }

    // Test if a list of resources are available at their respective given quantities. If any are not available, return false.
    public boolean testResource(List<Pair<String, Integer>> requirements) {

        for (Pair<String, Integer> requirement : requirements) { // Iterate through the list of resource name-quantity pairs
            if (!testResource(requirement.getKey(), requirement.getValue())) return false; // If a resource doesn't have enough quantity remaining, return false
        }

        return true; // If all resources have a sufficient quantity remaining, return true.
    }

    // Register a new resource into the manager
    public void registerResource(String resourceName, Integer resourceMax, Integer resourceStartValue) {
        Integer[] vals = {resourceMax, resourceStartValue};

        resources.put(resourceName, vals);
        return;

    }

    public TreeMap<String, Integer[]> getResources() {
        return resources;
    }

    // Set the value of an existing resource
    public void setResource(String resourceName, int resourceQuantity) {

        if (!resources.containsKey(resourceName)) {
            Out.error("Resource " + resourceName + " does not exist!");
            return;
        }

        Integer[] vals = resources.get(resourceName);
        vals[1] = Math.min(Math.max(0,resourceQuantity),getResourceMax(resourceName));

        resources.put(resourceName, vals);
    }

    // Increments the value of an existing resource up to the resource's max
    public void incrementResource(String resourceName, int increment) {
        if (!resources.containsKey(resourceName)) {
            Out.error("Resource " + resourceName + " does not exist!");
            return;
        }

        // Set change the value of the resource specified by 'increment'. The resource is bounded between 0 and its max value.
        setResource(resourceName, Math.max(0,Math.min(getResourceQuantity(resourceName) + increment, getResourceMax(resourceName))));

    }

    // Decrements the value of an existing resource down to at least zero.
    public void decrementResource(String resourceName, int decrement) {
        if (!resources.containsKey(resourceName)) {
            Out.error("Resource " + resourceName + " does not exist!");
            return;
        }

        resources.get(resourceName)[1] = Math.min(Math.max(0, getResourceQuantity(resourceName) - decrement), getResourceMax(resourceName));
    }

    // Simulates consuming exactly X of a resource. If there is not enough of the resource, none will be consumed and the function will return false.
    public boolean consumeResource(String resourceName, int resourceQuantity) {
        if (!resources.containsKey(resourceName)) {
            Out.error("Resource " + resourceName + " does not exist!");
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
            Out.error("Resource " + resourceName + " does not exist!");
            return -1;
        }

        return resources.get(resourceName)[1];
    }

    public int getResourceMax(String resourceName) {
        if (!resources.containsKey(resourceName)) {
            Out.error("Resource " + resourceName + " does not exist!");
            return -1;
        }
        return resources.get(resourceName)[0];
    }

    public int numberOfResources() {
        return resources.size();
    }

    public int getPrimaryResourceQuantity() {
        return getResourceQuantity(Manager.primaryResource);
    }

    public int getPrimaryResourceMax() {
        return getResourceMax(Manager.primaryResource);
    }
}
